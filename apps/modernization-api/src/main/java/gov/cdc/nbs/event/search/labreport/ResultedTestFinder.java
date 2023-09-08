package gov.cdc.nbs.event.search.labreport;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimplePath;
import gov.cdc.nbs.entity.srte.QLabTest;
import gov.cdc.nbs.entity.srte.QLoincCode;
import gov.cdc.nbs.event.search.labreport.model.ResultedTest;

@Component
public class ResultedTestFinder {
    private static final QLabTest labTestTable = QLabTest.labTest;
    private static final QLoincCode loincTable = QLoincCode.loincCode;
    private static final SimplePath<String> RESULTED_TEST = Expressions.path(String.class, "resultedTest");


    // used in the query to retrieve LOINC ResultedTests - NBS: ObservationProcessor.java #697
    private static List<String> relatedClassCodes = Arrays.asList(
            "ABXBACT",
            "BC",
            "CELLMARK",
            "CHAL",
            "CHALSKIN",
            "CHEM",
            "COAG",
            "CYTO",
            "DRUG",
            "DRUG/TOX",
            "HEM",
            "HEM/BC",
            "MICRO",
            "MISC",
            "PANEL.ABXBACT",
            "PANEL.BC",
            "PANEL.CHEM",
            "PANEL.MICRO",
            "PANEL.OBS",
            "PANEL.SERO",
            "PANEL.TOX",
            "PANEL.UA",
            "SERO",
            "SPEC",
            "TOX",
            "UA",
            "VACCIN");

    private final EntityManager entityManager;
    private final CriteriaBuilderFactory criteriaBuilderFactory;

    public ResultedTestFinder(
            final EntityManager entityManager,
            final CriteriaBuilderFactory criteriaBuilderFactory) {
        this.entityManager = entityManager;
        this.criteriaBuilderFactory = criteriaBuilderFactory;
    }

    public List<ResultedTest> findResultedTest(String searchText, boolean loinc) {
        String searchString = "%" + searchText + "%";
        if (loinc) {
            return new BlazeJPAQuery<String>(entityManager, criteriaBuilderFactory)
                    .distinct()
                    .select(loincTable.componentName.as(RESULTED_TEST))
                    .from(loincTable)
                    .where(loincTable.componentName.likeIgnoreCase(searchString)
                            .and(loincTable.relatedClassCd.in(relatedClassCodes)))
                    .limit(100)
                    .fetch()
                    .stream()
                    .map(ResultedTest::new)
                    .toList();

        } else {
            return new BlazeJPAQuery<String>(entityManager, criteriaBuilderFactory)
                    .distinct()
                    .select(labTestTable.labTestDescTxt.as(RESULTED_TEST))
                    .from(labTestTable)
                    .where(labTestTable.labTestDescTxt.likeIgnoreCase(searchString)
                            .or(labTestTable.id.labTestCd.likeIgnoreCase(searchString)))
                    .limit(100)
                    .fetch()
                    .stream()
                    .map(ResultedTest::new)
                    .toList();
        }
    }

}

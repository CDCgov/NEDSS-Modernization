package gov.cdc.nbs.event.search.labreport;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimplePath;
import gov.cdc.nbs.entity.srte.QLabResult;
import gov.cdc.nbs.entity.srte.QSnomedCode;
import gov.cdc.nbs.event.search.labreport.model.CodedResult;

@Component
public class CodedResultFinder {
    private static final QLabResult labResultTable = QLabResult.labResult;
    private static final QSnomedCode snomedCodeTable = QSnomedCode.snomedCode;
    private static final SimplePath<String> CODED_RESULT = Expressions.path(String.class, "codedResult");


    private final EntityManager entityManager;
    private final CriteriaBuilderFactory criteriaBuilderFactory;

    public CodedResultFinder(
            final EntityManager entityManager,
            final CriteriaBuilderFactory criteriaBuilderFactory) {
        this.entityManager = entityManager;
        this.criteriaBuilderFactory = criteriaBuilderFactory;
    }

    public List<CodedResult> findDistinctCodedResults(String searchText, boolean loinc) {
        String searchString = "%" + searchText + "%";
        if (loinc) {
            return new BlazeJPAQuery<String>(entityManager, criteriaBuilderFactory)
                    .distinct()
                    .select(snomedCodeTable.snomedDescTxt.as(CODED_RESULT))
                    .from(snomedCodeTable)
                    .where(snomedCodeTable.snomedDescTxt.likeIgnoreCase(searchString)
                            .and(snomedCodeTable.id.likeIgnoreCase(searchString)))
                    .limit(100)
                    .fetch()
                    .stream()
                    .map(CodedResult::new)
                    .toList();
        } else {
            return new BlazeJPAQuery<String>(entityManager, criteriaBuilderFactory)
                    .distinct()
                    .select(labResultTable.labResultDescTxt.as(CODED_RESULT))
                    .from(labResultTable)
                    .where(
                            labResultTable.organismNameInd.eq('N')
                                    .and(labResultTable.labResultDescTxt.likeIgnoreCase(searchString)
                                            .or(labResultTable.id.labResultCd.likeIgnoreCase(searchString))))
                    .limit(100)
                    .fetch()
                    .stream()
                    .map(CodedResult::new)
                    .toList();
        }
    }

}

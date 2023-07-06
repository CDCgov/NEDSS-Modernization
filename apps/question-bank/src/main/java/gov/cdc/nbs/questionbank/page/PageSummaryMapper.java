package gov.cdc.nbs.questionbank.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.querydsl.core.Tuple;
import gov.cdc.nbs.authentication.entity.QAuthUser;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
import gov.cdc.nbs.questionbank.page.model.PageSummary;
import gov.cdc.nbs.questionbank.question.model.Condition;

@Component
public class PageSummaryMapper {
    private static final QWaTemplate waTemplate = QWaTemplate.waTemplate;
    private static final QAuthUser authUser = QAuthUser.authUser;
    private static final QConditionCode conditionCode = QConditionCode.conditionCode;
    private static final QCodeValueGeneral codeValueGeneral = QCodeValueGeneral.codeValueGeneral;


    public List<PageSummary> map(List<Tuple> tuples) {
        // convert each entry to a page summary
        return tuples.stream()
                .map(this::map)
                .collect(
                        Collectors.groupingBy(PageSummary::id,
                                Collectors.collectingAndThen(
                                        Collectors.reducing((a, b) -> merge(a, b)),
                                        Optional::get)))
                .values()
                .stream()
                .toList();
    }

    /**
     * The query contains a join on the page_cond_mapping table that causes each condition associated with a page to
     * generate an extra row. The merge function simply combines the condition lists and returns the 'left' entry
     * 
     * @param left
     * @param right
     * @return
     */
    private PageSummary merge(PageSummary left, PageSummary right) {
        left.conditions().addAll(right.conditions());
        return left;
    }

    private PageSummary map(Tuple tuple) {
        ArrayList<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition(tuple.get(conditionCode.id), tuple.get(conditionCode.conditionShortNm)));
        return new PageSummary(
                tuple.get(waTemplate.id),
                new PageSummary.EventType(tuple.get(waTemplate.busObjType)),
                tuple.get(waTemplate.templateNm),
                getStatus(tuple),
                new PageSummary.MessageMappingGuide(
                        tuple.get(waTemplate.nndEntityIdentifier),
                        tuple.get(codeValueGeneral.codeShortDescTxt)),
                conditions,
                tuple.get(waTemplate.lastChgTime),
                tuple.get(authUser.userFirstNm.concat(" ").concat(authUser.userLastNm)));
    }


    /**
     * Sets the page status as defined in legacy code PageManagementActionUtil line #1965-1970
     * 
     * If a page has status "Draft" and no publish version exists, then set to "Initial Draft", if a publish version
     * does exist, then set to "Published with Draft"
     * 
     * @param tuple
     * @return
     */
    private String getStatus(Tuple tuple) {
        String templateType = tuple.get(waTemplate.templateType);
        Integer publishVersion = tuple.get(waTemplate.publishVersionNbr);
        if (publishVersion == null && "Draft".equalsIgnoreCase(templateType)) {
            return "Initial Draft";
        } else if (publishVersion != null && "Draft".equalsIgnoreCase(templateType)) {
            return "Published with Draft";
        } else {
            return templateType;
        }
    }
}

package gov.cdc.nbs.questionbank.page;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
import gov.cdc.nbs.questionbank.page.model.PageInfo;
import gov.cdc.nbs.questionbank.question.model.ConditionSummary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class PageInfoMapper {
    private static final QWaTemplate waTemplate = QWaTemplate.waTemplate;
    private static final QConditionCode conditionCode = QConditionCode.conditionCode;
    private static final QCodeValueGeneral codeValueGeneral = QCodeValueGeneral.codeValueGeneral;

    public PageInfo map(Tuple tuple) {
        return toPageInfo(tuple);
    }

    PageInfo toPageInfo(Tuple tuple) {
        ArrayList<ConditionSummary> conditions = new ArrayList<>();
        conditions.add(new ConditionSummary(tuple.get(conditionCode.id), tuple.get(conditionCode.conditionShortNm)));
        return new PageInfo(
                getEventType(tuple.get(waTemplate.busObjType)),
                tuple.get(waTemplate.templateNm),
                tuple.get(waTemplate.descTxt),
                new PageInfo.MessageMappingGuide(
                        tuple.get(waTemplate.nndEntityIdentifier),
                        tuple.get(codeValueGeneral.codeShortDescTxt)),
                conditions,
                tuple.get(waTemplate.datamartNm)
        );
    }

    PageInfo.EventType getEventType(String type) {
        String display =
                switch (type) {
                    case "INV" -> "Investigation";
                    case "CON" -> "Contact";
                    case "VAC" -> "Vaccination";
                    case "IXS" -> "Interview";
                    case "SUS" -> "Lab Susceptibility";
                    case "LAB" -> "Lab Report";
                    case "ISO" -> "Lab Isolate Tracking";
                    default -> type;
                };
        return new PageInfo.EventType(type, display);
    }
}

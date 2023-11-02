package gov.cdc.nbs.questionbank.page.summary.search;

import com.querydsl.core.types.dsl.StringExpression;
import gov.cdc.nbs.authentication.entity.QAuthUser;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QPageCondMapping;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;

public record PageSummaryTables(
    QWaTemplate page,
    QCodeValueGeneral eventType,
    QPageCondMapping conditionMapping,
    QConditionCode condition,
    QCodeValueGeneral mappingGuide,
    QAuthUser authUser,
    StringExpression lastUpdatedBy
) {

  public PageSummaryTables() {
    this(
        QWaTemplate.waTemplate,
        new QCodeValueGeneral("BUS_OBJ_TYPE"),
        QPageCondMapping.pageCondMapping,
        QConditionCode.conditionCode,
        new QCodeValueGeneral("NBS_MSG_PROFILE"),
        QAuthUser.authUser,
        QAuthUser.authUser.userFirstNm.concat(" ").concat(QAuthUser.authUser.userLastNm)
    );
  }

}

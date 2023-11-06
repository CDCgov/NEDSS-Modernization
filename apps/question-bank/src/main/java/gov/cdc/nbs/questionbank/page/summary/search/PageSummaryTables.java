package gov.cdc.nbs.questionbank.page.summary.search;

import com.querydsl.core.types.dsl.StringExpression;
import gov.cdc.nbs.authentication.entity.QAuthUser;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QPageCondMapping;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;

record PageSummaryTables(
    QWaTemplate page,
    QCodeValueGeneral eventType,
    QPageCondMapping conditionMapping,
    QConditionCode condition,
    QAuthUser authUser,
    StringExpression lastUpdatedBy
) {

  PageSummaryTables() {
    this(
        QWaTemplate.waTemplate,
        new QCodeValueGeneral("BUS_OBJ_TYPE"),
        QPageCondMapping.pageCondMapping,
        QConditionCode.conditionCode,
        QAuthUser.authUser,
        QAuthUser.authUser.userFirstNm.concat(" ").concat(QAuthUser.authUser.userLastNm)
    );
  }

}

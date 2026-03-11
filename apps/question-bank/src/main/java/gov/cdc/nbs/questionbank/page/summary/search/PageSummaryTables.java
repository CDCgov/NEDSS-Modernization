package gov.cdc.nbs.questionbank.page.summary.search;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QPageCondMapping;
import gov.cdc.nbs.questionbank.entity.QUser;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
import gov.cdc.nbs.questionbank.page.PageStatus;

record PageSummaryTables(
    QWaTemplate page,
    QCodeValueGeneral eventType,
    QPageCondMapping conditionMapping,
    QConditionCode condition,
    QUser authUser,
    StringExpression lastUpdatedBy,
    StringExpression status) {

  PageSummaryTables() {
    this(
        QWaTemplate.waTemplate,
        new QCodeValueGeneral("BUS_OBJ_TYPE"),
        QPageCondMapping.pageCondMapping,
        QConditionCode.conditionCode,
        QUser.user,
        QUser.user.userFirstNm.concat(" ").concat(QUser.user.userLastNm),
        new CaseBuilder()
            .when(
                QWaTemplate.waTemplate
                    .templateType
                    .eq("Draft")
                    .and(QWaTemplate.waTemplate.publishVersionNbr.isNotNull()))
            .then(PageStatus.PUBLISHED_WITH_DRAFT.display())
            .when(QWaTemplate.waTemplate.templateType.eq("Draft"))
            .then(PageStatus.INITIAL_DRAFT.display())
            .otherwise(QWaTemplate.waTemplate.templateType));
  }
}

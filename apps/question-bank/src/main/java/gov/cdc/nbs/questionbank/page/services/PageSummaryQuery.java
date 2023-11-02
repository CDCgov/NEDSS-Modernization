package gov.cdc.nbs.questionbank.page.services;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummaryTables;

public class PageSummaryQuery {

  private final PageSummaryTables tables;

  public PageSummaryQuery(final PageSummaryTables tables) {
    this.tables = tables;
  }

  public JPAQuery<Tuple> query(final JPAQueryFactory factory) {
    return factory.select(
            this.tables.page().id,
            this.tables.page().templateType,
            this.tables.page().templateNm,
            this.tables.page().descTxt,
            this.tables.eventType().id.code,
            this.tables.eventType().codeShortDescTxt,
            this.tables.page().lastChgTime,
            this.tables.page().lastChgUserId,
            this.tables.page().publishVersionNbr,
            this.tables.mappingGuide().id.code,
            this.tables.mappingGuide().codeShortDescTxt,
            this.tables.lastUpdatedBy(),
            this.tables.condition().id,
            this.tables.condition().conditionShortNm
        ).from(this.tables.page())
        .join(this.tables.eventType()).on(
            this.tables.eventType().id.codeSetNm.eq("BUS_OBJ_TYPE"),
            this.tables.eventType().id.code.eq(this.tables.page().busObjType)
        )
        .leftJoin(this.tables.authUser())
        .on(this.tables.page().lastChgUserId.eq(this.tables.authUser().nedssEntryId))
        .leftJoin(this.tables.conditionMapping())
        .on(this.tables.page().id.eq(this.tables.conditionMapping().waTemplateUid.id))
        .leftJoin(this.tables.condition())
        .on(this.tables.conditionMapping().conditionCd.eq(this.tables.condition().id))
        .leftJoin(this.tables.mappingGuide()).on(
            this.tables.mappingGuide().id.codeSetNm.eq("NBS_MSG_PROFILE"),
            this.tables.mappingGuide().id.code.eq(this.tables.page().nndEntityIdentifier)
        );
  }
}

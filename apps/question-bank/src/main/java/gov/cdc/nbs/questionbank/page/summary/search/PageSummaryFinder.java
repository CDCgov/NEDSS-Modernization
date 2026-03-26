package gov.cdc.nbs.questionbank.page.summary.search;

import com.google.common.collect.Ordering;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.accumulation.Accumulator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
class PageSummaryFinder {

  private static final String BUSINESS_OBJECT_CODE_SET = "BUS_OBJ_TYPE";
  private final JPAQueryFactory factory;
  private final PageSummaryTables tables;
  private final PageSummaryMapper mapper;
  private final PageSummaryMerger merger;

  PageSummaryFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.tables = new PageSummaryTables();
    this.mapper = new PageSummaryMapper(this.tables);
    this.merger = new PageSummaryMerger();
  }

  List<PageSummary> findAll(final List<Long> identifiers) {
    return factory
        .select(
            this.tables.page().id,
            this.tables.page().templateType,
            this.tables.page().templateNm,
            this.tables.page().descTxt,
            this.tables.eventType().id.code,
            this.tables.eventType().codeShortDescTxt,
            this.tables.page().lastChgTime,
            this.tables.page().lastChgUserId,
            this.tables.page().publishVersionNbr,
            this.tables.lastUpdatedBy(),
            this.tables.condition().id,
            this.tables.condition().conditionShortNm)
        .from(this.tables.page())
        .join(this.tables.eventType())
        .on(
            this.tables.eventType().id.codeSetNm.eq(BUSINESS_OBJECT_CODE_SET),
            this.tables.eventType().id.code.eq(this.tables.page().busObjType))
        .leftJoin(this.tables.authUser())
        .on(this.tables.page().lastChgUserId.eq(this.tables.authUser().nedssEntryId))
        .leftJoin(this.tables.conditionMapping())
        .on(this.tables.page().id.eq(this.tables.conditionMapping().waTemplateUid.id))
        .leftJoin(this.tables.condition())
        .on(this.tables.conditionMapping().conditionCd.eq(this.tables.condition().id))
        .where(this.tables.page().id.in(identifiers))
        .stream()
        .map(mapper::map)
        .collect(Accumulator.collecting(PageSummary::id, this.merger::merge))
        .stream()
        .sorted(Ordering.explicit(identifiers).onResultOf(PageSummary::id))
        .toList();
  }
}

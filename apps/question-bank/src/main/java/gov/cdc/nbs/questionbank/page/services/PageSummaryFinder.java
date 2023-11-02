package gov.cdc.nbs.questionbank.page.services;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.accumulation.Accumulator;
import gov.cdc.nbs.questionbank.page.summary.PageSummaryMapper;
import gov.cdc.nbs.questionbank.page.summary.PageSummaryMerger;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummaryTables;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class PageSummaryFinder {

  private final JPAQueryFactory factory;
  private final PageSummaryTables tables;
  private final PageSummaryQuery query;
  private final PageSummaryMapper mapper;
  private final PageSummaryMerger merger;

  public PageSummaryFinder(final JPAQueryFactory factory) {
    this.factory = factory;
    this.tables = new PageSummaryTables();
    this.query = new PageSummaryQuery(tables);
    this.mapper = new PageSummaryMapper(this.tables);
    this.merger = new PageSummaryMerger();
  }

  public Optional<PageSummary> find(final long identifier) {
    return this.query.query(this.factory)
        .where(this.tables.page().id.eq(identifier))
        .stream()
        .map(this.mapper::map)
        .collect(Accumulator.collecting(PageSummary::id, this.merger::merge))
        .stream()
        .findFirst();
  }

}


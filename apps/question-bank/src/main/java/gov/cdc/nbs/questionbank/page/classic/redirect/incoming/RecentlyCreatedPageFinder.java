package gov.cdc.nbs.questionbank.page.classic.redirect.incoming;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class RecentlyCreatedPageFinder {

  private final JPAQueryFactory factory;
  private static final QWaTemplate pageTable = QWaTemplate.waTemplate;

  public RecentlyCreatedPageFinder(JPAQueryFactory factory) {
    this.factory = factory;
  }

  public Optional<Long> findRecentlyCreatedBy(long user) {
    Instant thirtyMinutesAgo = Instant.now().minus(30, ChronoUnit.MINUTES);
    Long id =
        factory
            .select(pageTable.id)
            .from(pageTable)
            .where(pageTable.addUserId.eq(user).and(pageTable.addTime.gt(thirtyMinutesAgo)))
            .orderBy(pageTable.addTime.desc())
            .fetchFirst();
    return Optional.ofNullable(id);
  }
}

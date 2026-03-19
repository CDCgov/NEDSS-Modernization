package gov.cdc.nbs.questionbank.page.classic.redirect.incoming;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecentlyCreatedPageFinderTest {

  @Mock private JPAQueryFactory queryFactory;

  @InjectMocks private RecentlyCreatedPageFinder finder;

  private static final QWaTemplate pageTable = QWaTemplate.waTemplate;

  @Test
  @SuppressWarnings("unchecked")
  void should_return_id() {
    // given a user that has recently created a page
    JPAQuery<Long> mockQuery = Mockito.mock(JPAQuery.class);
    when(queryFactory.select(pageTable.id)).thenReturn(mockQuery);
    when(mockQuery.from(pageTable)).thenReturn(mockQuery);
    when(mockQuery.where(Mockito.any(BooleanExpression.class))).thenReturn(mockQuery);
    when(mockQuery.orderBy(pageTable.addTime.desc())).thenReturn(mockQuery);
    when(mockQuery.fetchFirst()).thenReturn(19l);

    // when the database is queried for the id
    Optional<Long> id = finder.findRecentlyCreatedBy(1l);

    // Then the id is returned
    assertThat(id).contains(19l);
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_return_null() {
    // given a user that has not recently created a page
    JPAQuery<Long> mockQuery = Mockito.mock(JPAQuery.class);
    when(queryFactory.select(pageTable.id)).thenReturn(mockQuery);
    when(mockQuery.from(pageTable)).thenReturn(mockQuery);
    when(mockQuery.where(Mockito.any(BooleanExpression.class))).thenReturn(mockQuery);
    when(mockQuery.orderBy(pageTable.addTime.desc())).thenReturn(mockQuery);
    when(mockQuery.fetchFirst()).thenReturn(null);

    // when the database is queried for the id
    Optional<Long> id = finder.findRecentlyCreatedBy(1l);

    // Then null is returned
    assertThat(id).isEmpty();
  }
}

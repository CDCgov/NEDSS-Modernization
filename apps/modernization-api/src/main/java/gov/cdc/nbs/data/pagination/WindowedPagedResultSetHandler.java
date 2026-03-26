package gov.cdc.nbs.data.pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

/**
 * A {@link RowCallbackHandler} that can be used with queries made with a {@link java.sql.JDBCType}
 * that include the total number of rows in the result set. This is usually accomplished using
 * window functions (i.e. {@code count () over ()}) to compute the total.
 *
 * @param <T> The type being paged
 */
public class WindowedPagedResultSetHandler<T> implements RowCallbackHandler {
  private HandlerState state;

  public WindowedPagedResultSetHandler(final int totalColumnIndex, final RowMapper<T> mapper) {
    this.state = new EmptyState(totalColumnIndex, mapper);
  }

  public void processRow(final ResultSet resultSet) throws SQLException {
    this.state = this.state.accept(resultSet);
  }

  public Page<T> asPaged(final Pageable pageable) {
    return this.state.asPage(pageable);
  }

  private abstract class HandlerState {

    abstract HandlerState accept(final ResultSet resultSet) throws SQLException;

    Page<T> asPage(final Pageable pageable) {
      return Page.empty(pageable);
    }
  }

  private class EmptyState extends HandlerState {
    private final int totalColumnIndex;
    private final RowMapper<T> mapper;

    EmptyState(final int totalColumnIndex, final RowMapper<T> mapper) {
      this.totalColumnIndex = totalColumnIndex;
      this.mapper = mapper;
    }

    HandlerState accept(final ResultSet resultSet) throws SQLException {
      long total = resultSet.getLong(this.totalColumnIndex);

      return new PageProcessor(this.mapper, total).accept(resultSet);
    }
  }

  private class PageProcessor extends HandlerState {

    private final RowMapper<T> mapper;
    private final long total;
    private final List<T> items;

    PageProcessor(final RowMapper<T> mapper, long total) {
      this.mapper = mapper;
      this.total = total;
      this.items = new ArrayList<>();
    }

    HandlerState accept(final ResultSet resultSet) throws SQLException {
      T item = this.mapper.mapRow(resultSet, resultSet.getRow());
      this.items.add(item);
      return this;
    }

    @Override
    Page<T> asPage(final Pageable pageable) {
      return new PageImpl<>(this.items, pageable, total);
    }
  }
}

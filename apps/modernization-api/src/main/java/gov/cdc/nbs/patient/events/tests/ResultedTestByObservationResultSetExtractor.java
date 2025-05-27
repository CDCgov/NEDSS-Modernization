package gov.cdc.nbs.patient.events.tests;

import com.google.common.collect.ArrayListMultimap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

class ResultedTestByObservationResultSetExtractor implements ResultSetExtractor<Map<Long, Collection<ResultedTest>>> {

  record Column(int observation, ResultedTestRowMapper.Column resultedTest) {
  }


  private final Column columns;
  private final RowMapper<ResultedTest> mapper;

  ResultedTestByObservationResultSetExtractor(final Column columns) {
    this.columns = columns;
    this.mapper = new ResultedTestRowMapper(columns.resultedTest());
  }

  @Override
  public Map<Long, Collection<ResultedTest>> extractData(final ResultSet resultSet)
      throws SQLException, DataAccessException {

    ArrayListMultimap<Long, ResultedTest> resultedTestsByObservation = ArrayListMultimap.create();

    for (int row = 0; resultSet.next(); row++) {
      long observation = resultSet.getLong(columns.observation());

      ResultedTest resultedTest = this.mapper.mapRow(resultSet, row);
      if (resultedTest != null) {
        resultedTestsByObservation.put(observation, resultedTest);
      }

    }

    return resultedTestsByObservation.asMap();
  }
}

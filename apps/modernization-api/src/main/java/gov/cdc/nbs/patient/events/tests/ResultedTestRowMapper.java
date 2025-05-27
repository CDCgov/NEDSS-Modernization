package gov.cdc.nbs.patient.events.tests;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

class ResultedTestRowMapper implements RowMapper<ResultedTest> {

  record Column(
      int name,
      int status,
      int coded,
      int comparator,
      int numeric,
      int scale,
      int high,
      int low,
      int unit,
      int text
  ) {
  }


  private final Column columns;

  public ResultedTestRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public ResultedTest mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
    String name = resultSet.getString(columns.name());
    String coded = resultSet.getString(columns.coded());
    String text = maybeDisplayTextResult(columns, resultSet);
    String numeric = maybeDisplayNumericResult(columns, resultSet);
    String description = Stream.of(coded, text, numeric)
        .filter(Objects::nonNull)
        .collect(joining("\n"));

    return new ResultedTest(
        name,
        description
    );
  }

  private static String maybeDisplayTextResult(final Column columns, final ResultSet resultSet) throws SQLException {
    StringBuilder builder = new StringBuilder();
    String text = resultSet.getString(columns.text());

    if (text != null) {
      builder.append(text);

      String status = maybeDisplayStatus(columns, resultSet);
      if (status != null) {
        builder.append(status);
      }
    }

    return builder.isEmpty() ? null : builder.toString();
  }

  private static String maybeDisplayNumericResult(final Column columns, final ResultSet resultSet) throws SQLException {
    StringBuilder builder = new StringBuilder();

    BigDecimal numeric = resultSet.getBigDecimal(columns.numeric());
    String unit = resultSet.getString(columns.unit());

    if (numeric != null) {
      String comparator = resultSet.getString(columns.comparator());

      int scale = resultSet.getInt(columns.scale());
      builder.append(comparator)
          .append(numeric.setScale(scale, RoundingMode.HALF_EVEN));

      if (unit != null) {
        builder.append(" ").append(unit);
      }

      if (!builder.isEmpty()) {
        String range = maybeDisplayRange(columns, resultSet);

        if (range != null) {
          builder.append("\n")
              .append(range);
        }
      }

    }
    return builder.isEmpty() ? null : builder.toString();
  }

  private static String maybeDisplayRange(final Column columns, final ResultSet resultSet) throws SQLException {
    StringBuilder builder = new StringBuilder();

    String high = resultSet.getString(columns.high());
    String low = resultSet.getString(columns.low());

    if (high != null || low != null) {
      String range = Stream.of(low, high)
          .filter(Objects::nonNull)
          .collect(
              joining(
                  "-",
                  "Reference Range - (",
                  ")"
              )
          );

      builder.append(range);

      String status = maybeDisplayStatus(columns, resultSet);
      if (status != null) {
        builder.append(status);
      }
    }

    return builder.isEmpty() ? null : builder.toString();
  }

  private static String maybeDisplayStatus(final Column columns, final ResultSet resultSet) throws SQLException {
    String status = resultSet.getString(columns.status());
    StringBuilder builder = new StringBuilder();
    if (status != null) {
      builder.append(" - (")
          .append(status)
          .append(")");
    }
    return builder.isEmpty() ? null : builder.toString();
  }
}


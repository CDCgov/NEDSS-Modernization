package gov.cdc.nbs.patient.events.tests;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
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
    String reference = maybeDisplayReferenceRange(columns, resultSet);

    Optional<String> status = maybeDisplayStatus(columns, resultSet)
        .filter(unused -> reference == null);

    String coded = resultSet.getString(columns.coded());

    String text = resultSet.getString(columns.text());

    String numeric = maybeDisplayNumericResult(columns, resultSet);

    String result = null;

    if (coded != null) {
      result = coded;
    }

    if (text != null) {
      result = Objects.requireNonNullElse(result, "").concat("\n").concat(text);
    }

    if (numeric != null) {
      result = Objects.requireNonNull(result, "").concat("\n").concat(numeric);
    }

    result = Objects.requireNonNull(result, "").concat(status.orElse(""));



    return new ResultedTest(
        name,
        result,
        reference
    );
  }

  private static String maybeDisplayNumericResult(final Column columns, final ResultSet resultSet) throws SQLException {

    BigDecimal numeric = resultSet.getBigDecimal(columns.numeric());

    if (numeric != null) {
      StringBuilder builder = new StringBuilder();
      String comparator = resultSet.getString(columns.comparator());

      if (comparator != null) {
        builder.append(comparator);
      }

      int scale = resultSet.getInt(columns.scale());
      builder.append(numeric.setScale(scale, RoundingMode.HALF_EVEN));

      String unit = resultSet.getString(columns.unit());
      if (unit != null) {
        builder.append(" ").append(unit);
      }

      return builder.toString();
    }
    return null;
  }

  private static String maybeDisplayReferenceRange(
      final Column columns,
      final ResultSet resultSet
  )
      throws SQLException {

    String high = resultSet.getString(columns.high());
    String low = resultSet.getString(columns.low());

    if (high != null || low != null) {
      String range = Stream.of(low, high)
          .filter(Objects::nonNull)
          .collect(
              joining(
                  "-",
                  "(",
                  ")"
              )
          );


      return maybeDisplayStatus(columns, resultSet).map(range::concat).orElse(range);
    }

    return null;
  }

  private static Optional<String> maybeDisplayStatus(final Column columns, final ResultSet resultSet)
      throws SQLException {
    String status = resultSet.getString(columns.status());

    return status == null ? Optional.empty() : Optional.of(String.format(" - (%s)", status));
  }
}


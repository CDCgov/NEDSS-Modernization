package gov.cdc.nbs.patient.file.demographics.race;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.data.selectable.SelectableRowMapper;
import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

class PatientRaceDemographicRowMapper implements RowMapper<PatientRaceDemographic> {

  record Column(
      int asOf,
      SelectableRowMapper.Column category,
      SelectableRowMapper.Column detail
  ) {
    Column() {
      this(
          1,
          new SelectableRowMapper.Column(2, 3),
          new SelectableRowMapper.Column(4, 5)
      );
    }
  }


  private final Column columns;
  private final SelectableRowMapper categoryMapper;
  private final SelectableRowMapper detailMapper;

  PatientRaceDemographicRowMapper() {
    this(new Column());
  }

  PatientRaceDemographicRowMapper(final Column columns) {
    this.columns = columns;
    this.categoryMapper = new SelectableRowMapper(columns.category());
    this.detailMapper = new SelectableRowMapper(columns.detail());
  }

  @Override
  public PatientRaceDemographic mapRow(final ResultSet resultSet, int rowNum) throws SQLException {

    LocalDate asOf = LocalDateColumnMapper.map(resultSet, columns.asOf());
    Selectable category = Objects.requireNonNull(categoryMapper.mapRow(resultSet, rowNum));
    Selectable detail = detailMapper.mapRow(resultSet, rowNum);

    List<Selectable> detailed = detail == null || Objects.equals(detail.value(), category.value())
        ? List.of()
        : List.of(detail);

    return new PatientRaceDemographic(
        asOf,
        category,
        detailed
    );
  }
}

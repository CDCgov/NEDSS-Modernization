package gov.cdc.nbs.patient.file.demographics.ethnicity;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.data.selectable.SelectableRowMapper;
import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

class PatientEthnicityDemographicRowMapper implements RowMapper<PatientEthnicityDemographic> {

  record Column(
      int asOf,
      SelectableRowMapper.Column category,
      SelectableRowMapper.Column unknownReason,
      SelectableRowMapper.Column detail
  ) {
    Column() {
      this(
          1,
          new SelectableRowMapper.Column(2, 3),
          new SelectableRowMapper.Column(4, 5),
          new SelectableRowMapper.Column(6, 7)
      );
    }
  }


  private final Column columns;
  private final SelectableRowMapper ethnicityMapper;
  private final SelectableRowMapper unknownReasonMapper;
  private final SelectableRowMapper detailMapper;

  PatientEthnicityDemographicRowMapper() {
    this(new Column());
  }

  PatientEthnicityDemographicRowMapper(final Column columns) {
    this.columns = columns;
    this.ethnicityMapper = new SelectableRowMapper(columns.category());
    this.unknownReasonMapper = new SelectableRowMapper(columns.unknownReason());
    this.detailMapper = new SelectableRowMapper(columns.detail());
  }

  @Override
  public PatientEthnicityDemographic mapRow(final ResultSet resultSet, int rowNum) throws SQLException {

    LocalDate asOf = LocalDateColumnMapper.map(resultSet, columns.asOf());
    Selectable ethnicGroup = ethnicityMapper.mapRow(resultSet, rowNum);
    Selectable unknownReason = unknownReasonMapper.mapRow(resultSet, rowNum);
    Selectable detail = detailMapper.mapRow(resultSet, rowNum);


    List<Selectable> detailed = List.of();
    if (ethnicGroup != null && ethnicGroup.value().equalsIgnoreCase("Hispanic or Latino")) {
      detailed = detail == null || Objects.equals(detail.value(), ethnicGroup.value())
          ? List.of()
          : List.of(detail);
    }

    Selectable unknown;
    if (ethnicGroup != null && ethnicGroup.value().equalsIgnoreCase("UNK")) {
      System.out.println(ethnicGroup.value());
      unknown = unknownReason;
    } else {
      unknown = null;
    }

    return new PatientEthnicityDemographic(
        asOf,
        ethnicGroup,
        unknown,
        detailed
    );
  }
}

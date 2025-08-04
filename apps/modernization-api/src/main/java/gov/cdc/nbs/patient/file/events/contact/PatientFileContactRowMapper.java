package gov.cdc.nbs.patient.file.events.contact;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigation;
import gov.cdc.nbs.patient.events.investigation.association.AssociatedInvestigationRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

class PatientFileContactRowMapper implements RowMapper<PatientFileContacts.PatientFileContact> {

  record Column(
      int patient,
      int identifier,
      int local,
      int referralBasis,
      int processingDecision,
      int createdOn,
      int namedOn,
      NamedContactRowMapper.Columns named,
      int priority,
      int disposition,
      AssociatedInvestigationRowMapper.Column associated
  ) {
  }

  private final Column columns;
  private final RowMapper<NamedContact> namedContactRowMapper;
  private final RowMapper<AssociatedInvestigation> associatedRowMapper;

  PatientFileContactRowMapper(final Column columns) {
    this.columns = columns;
    this.namedContactRowMapper = new NamedContactRowMapper(columns.named);
    this.associatedRowMapper = new AssociatedInvestigationRowMapper(columns.associated);
  }

  @Override
  public PatientFileContacts.PatientFileContact mapRow(
      final ResultSet resultSet,
      final int rowNum
  ) throws SQLException {
    long patient = resultSet.getLong(this.columns.patient());
    long identifier = resultSet.getLong(this.columns.identifier());
    String local = resultSet.getString(this.columns.local());
    String processingDecision = resultSet.getString(this.columns.processingDecision());
    String referralBasis = resultSet.getString(this.columns.referralBasis());
    LocalDateTime createdOn = resultSet.getObject(this.columns.createdOn, LocalDateTime.class);
    LocalDate namedOn = LocalDateColumnMapper.map(resultSet, this.columns.namedOn());
    NamedContact named = namedContactRowMapper.mapRow(resultSet, rowNum);
    String priority = resultSet.getString(this.columns.priority());
    String disposition = resultSet.getString(this.columns.disposition());
    AssociatedInvestigation associated = associatedRowMapper.mapRow(resultSet, rowNum);

    return new PatientFileContacts.PatientFileContact(
        patient,
        identifier,
        local,
        processingDecision,
        referralBasis,
        createdOn,
        namedOn,
        named,
        priority,
        disposition,
        associated
    );
  }
}

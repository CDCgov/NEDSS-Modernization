package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Display_column", catalog = "NBS_ODSE")
public class DisplayColumn {
  @Id
  @Column(name = "display_column_uid", nullable = false)
  private Long id;

  // TODO: Add DataSourceColumn? not specified in design doc
  @NonNull @ManyToOne(fetch = FetchType.LAZY) // TODO: leave as-is or default to EAGER?
  @JoinColumn(name = "column_uid", nullable = false)
  private DataSourceColumn columnUid;

  @NonNull private ReportId reportId;

  @Column(name = "sequence_nbr")
  private Integer sequenceNumber;

  @Embedded private Status status;

  protected DisplayColumn() {}
}

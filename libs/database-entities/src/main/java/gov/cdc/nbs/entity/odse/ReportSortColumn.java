package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Report_Sort_Column", catalog = "NBS_ODSE")
public class ReportSortColumn {
  // TODO: Add @NonNull here?
  @Id
  @Column(name = "report_sort_column_uid", nullable = false)
  private Long id;

  @Column(name = "report_sort_order_code", length = 4)
  private String orderCode;

  @Column(name = "report_sort_sequence_nu")
  private Integer sequenceNumber;

  private ReportId reportUid;

  @Column(name = "column_uid", nullable = false)
  private Long columnUid;

  @Embedded private Status status;
}

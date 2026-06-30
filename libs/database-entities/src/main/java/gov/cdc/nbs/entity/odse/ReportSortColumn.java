package gov.cdc.nbs.entity.odse;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name = "Report_Sort_Column", catalog = "NBS_ODSE")
public class ReportSortColumn {
  @Id
  @Column(name = "report_sort_column_uid", nullable = false)
  private Long id;

  @Column(name = "report_sort_order_code")
  private String reportSortOrderCode;

  @Column(name = "report_sort_sequence_num")
  private Integer reportSortSequenceNum;

  @NonNull @ManyToOne(fetch = FetchType.LAZY)
  // Report has a composite primary key
  @SuppressWarnings("java:S1710")
  @JoinColumns({
    @JoinColumn(name = "report_uid", referencedColumnName = "report_uid", nullable = false),
    @JoinColumn(
        name = "data_source_uid",
        referencedColumnName = "data_source_uid",
        nullable = false)
  })
  private Report report;

  // Don't need the join, so treating as plain column
  @Column(name = "column_uid")
  private Long dataSourceColumnUid;

  @Column(name = "status_cd")
  private Character statusCd;

  @Column(name = "status_time")
  private LocalDateTime statusTime;

  protected ReportSortColumn() {}
}

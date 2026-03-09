package gov.cdc.nbs.entity.odse;

import jakarta.persistence.*;
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
@Table(name = "Report_Filter", catalog = "NBS_ODSE")
public class ReportFilter {
  @Id
  @Column(name = "report_filter_uid", nullable = false)
  private Long id;

  @NonNull @Embedded private ReportId reportId;

  @NonNull @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "filter_uid")
  private FilterCode filterCode;

  @NonNull @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "column_uid")
  private DataSourceColumn dataSourceColumn;

  @Column(name = "status_cd")
  private Character statusCd;

  @Column(name = "max_value_cnt")
  private Integer maxValueCnt;

  @Column(name = "min_value_cnt")
  private Integer minValueCnt;

  protected ReportFilter() {}
}

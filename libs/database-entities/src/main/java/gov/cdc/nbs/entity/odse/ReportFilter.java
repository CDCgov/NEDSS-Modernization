package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
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
@Table(name = "Report_Filter", catalog = "NBS_ODSE")
public class ReportFilter {
  @Id
  @Column(name = "report_filter_uid", nullable = false)
  private Long id;

  @NonNull private ReportId reportId;

  @NonNull @ManyToOne(fetch = FetchType.LAZY) // TODO: leave as-is or default to EAGER?
  @JoinColumn(name = "filter_uid")
  private FilterCode filterCode;

  @NonNull @ManyToOne(fetch = FetchType.LAZY) // TODO: leave as-is or default to EAGER?
  @JoinColumn(name = "column_uid")
  private DataSourceColumn dataSourceColumn;

  @Column(name = "max_value_cnt")
  private Integer maxValueCnt;

  @Column(name = "min_value_cnt")
  private Integer minValueCnt;

  protected ReportFilter() {}
}

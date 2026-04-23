package gov.cdc.nbs.entity.odse;

import jakarta.persistence.*;
import java.util.List;
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

  @NonNull @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "filter_uid")
  private FilterCode filterCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "column_uid")
  private DataSourceColumn dataSourceColumn;

  @OneToMany(mappedBy = "reportFilter", fetch = FetchType.LAZY)
  private List<FilterValue> filterValues;

  @OneToOne(mappedBy = "reportFilter", fetch = FetchType.LAZY)
  private ReportFilterValidation filterValidation;

  @Column(name = "status_cd")
  private Character statusCd;

  @Column(name = "max_value_cnt")
  private Integer maxValueCnt;

  @Column(name = "min_value_cnt")
  private Integer minValueCnt;

  protected ReportFilter() {}
}

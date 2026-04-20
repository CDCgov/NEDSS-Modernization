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
@Table(name = "Report_Filter_Validation", catalog = "NBS_ODSE")
public class ReportFilterValidation {
  @Id
  @Column(name = "report_filter_validation_uid", nullable = false)
  private Long id;

  @NonNull @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "report_filter_uid")
  private ReportFilter reportFilter;

  @Column(name = "report_filter_ind")
  private Character reportFilterInd;

  @Column(name = "status_cd")
  private Character statusCd;

  protected ReportFilterValidation() {}
}

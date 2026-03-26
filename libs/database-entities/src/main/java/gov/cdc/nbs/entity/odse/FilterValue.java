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
@Table(name = "Filter_value", catalog = "NBS_ODSE")
public class FilterValue {
  @Id
  @Column(name = "value_uid", nullable = false)
  private Long id;

  @MapsId("report_filter_uid")
  @NonNull @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "report_filter_uid", nullable = false)
  private ReportFilter reportFilter;

  @NonNull @Column(name = "sequence_nbr")
  private Integer sequenceNumber;

  @NonNull @Column(name = "value_type", length = 20)
  private String valueType;

  @NonNull @Column(name = "column_uid")
  private Long columnUid;

  @NonNull @Column(name = "operator", length = 20)
  private String operator;

  @NonNull @Column(name = "value_txt", length = 2000)
  private String valueTxt;

  protected FilterValue() {}
}

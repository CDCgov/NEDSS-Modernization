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
@Builder
@Getter
@Setter
@Entity
@Table(name = "Display_column", catalog = "NBS_ODSE")
public class DisplayColumn {
  @Id
  @Column(name = "display_column_uid", nullable = false)
  private Long id;

  @NonNull @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "column_uid", nullable = false)
  private DataSourceColumn dataSourceColumn;

  @NonNull @Embedded private ReportId reportId;

  @NonNull @Column(name = "sequence_nbr")
  private Integer sequenceNumber;

  //  TODO: add a converter? Or Separate Status class? // NOSONAR
  @NonNull @Column(name = "status_cd", nullable = false)
  private Character statusCd;

  @Column(name = "status_time")
  private LocalDateTime statusTime;

  protected DisplayColumn() {}
}

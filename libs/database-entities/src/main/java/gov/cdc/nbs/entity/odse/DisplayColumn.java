package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

  @NonNull @ManyToOne(fetch = FetchType.LAZY)

  // use @JoinColumns to indicate multiple
  // join columns are needed in the examples table
  @JoinColumns({
    // define the first join column
    @JoinColumn(name = "report_uid", nullable = false),
    // define the second join column.
    @JoinColumn(name = "data_source_uid", nullable = false)
  })
  private Report report;

  @NonNull @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "data_source_uid", nullable = false, insertable = false, updatable = false)
  private DataSource dataSource;

  @NonNull @Column(name = "sequence_nbr")
  private Integer sequenceNumber;

  //  TODO: add a converter? Or Separate Status class? // NOSONAR
  @NonNull @Column(name = "status_cd", nullable = false)
  private Character statusCd;

  @Column(name = "status_time")
  private LocalDateTime statusTime;

  protected DisplayColumn() {}
}

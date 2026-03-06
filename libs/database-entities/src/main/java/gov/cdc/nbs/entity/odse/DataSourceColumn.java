package gov.cdc.nbs.entity.odse;

import jakarta.persistence.*;
import java.time.LocalDate;
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
@Table(name = "Data_source_column", catalog = "NBS_ODSE")
public class DataSourceColumn {

  @Id
  @Column(name = "column_uid", nullable = false)
  private Long id;

  @Column(name = "column_max_length")
  private Integer columnMaxLength;

  @Column(name = "column_name", length = 50)
  private String columnName;

  @Column(name = "column_title", length = 50)
  private String columnTitle;

  @Column(name = "column_type_code", length = 20)
  private String columnSourceTypeCode;

  @NonNull @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "data_source_uid")
  private DataSource dataSource;

  @Column(name = "desc_txt", length = 300)
  private String descTxt;

  //  TODO: add a converter? // NOSONAR
  @Column(name = "displayable", length = 1)
  private Character displayable;

  @Column(name = "effective_from_time")
  private LocalDate effectiveFromTime;

  @Column(name = "effective_to_time")
  private LocalDate effectiveToTime;

  //  TODO: add a converter? // NOSONAR
  @Column(name = "filterable", length = 1)
  private Character filterable;

  //  TODO: add a converter? Or Separate Status class? // NOSONAR
  @NonNull @Column(name = "status_cd", nullable = false)
  private Character statusCd;

  @Column(name = "status_time")
  private LocalDateTime statusTime;

  protected DataSourceColumn() {}
}

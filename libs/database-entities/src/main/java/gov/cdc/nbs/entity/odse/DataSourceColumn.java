package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.time.EffectiveTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
@Table(name = "Data_source_column", catalog = "NBS_ODSE")
public class DataSourceColumn {

  @Id
  @Column(name = "column_uid", nullable = false)
  private Long id;

  @Column(name = "column_max_len")
  private Integer columnMaxLength;

  @Column(name = "column_name", length = 50)
  private String columnName;

  @Column(name = "column_title", length = 60)
  private String columnTitle;

  @Column(name = "column_type_code", length = 20)
  private String columnSourceTypeCode;

  @NonNull @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "data_source_uid")
  private DataSource dataSource;

  /** Should be 1:1 in practice, but the DB does not enforce this. */
  @OneToMany(mappedBy = "column", fetch = FetchType.LAZY)
  private List<DataSourceCodeset> codesets;

  @Column(name = "desc_txt", length = 300)
  private String descTxt;

  //  TODO: add a converter? // NOSONAR
  @Column(name = "displayable", length = 1)
  private Character displayable;

  @Embedded private EffectiveTime effectiveTime;

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

package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Data_Source_Codeset", catalog = "NBS_ODSE")
public class DataSourceCodeset {

  @Id
  @Column(name = "data_source_codeset_uid", nullable = false)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "column_uid")
  private DataSourceColumn column;

  @Column(name = "code_desc_cd", length = 20)
  private String codeDescCd;

  @Column(name = "codeset_nm", length = 250)
  private String codesetNm;

  //  TODO: add a converter? Or Separate Status class? // NOSONAR
  @Column(name = "status_cd")
  private Character statusCd;

  @Column(name = "status_time")
  private LocalDateTime statusTime;

  protected DataSourceCodeset() {}
}

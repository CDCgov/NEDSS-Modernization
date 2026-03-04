package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Status;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Data_Source_Codeset", catalog = "NBS_ODSE")
public class DataSourceCodeset {

  @Id
  @Column(name = "data_source_codeset_uid", nullable = false)
  private Long id;

  @NonNull @ManyToMany(fetch = FetchType.LAZY) // TODO: leave as-is or default to EAGER?
  @JoinColumn(name = "column_uid", nullable = false)
  private DataSourceColumn columnUid;

  @Column(name = "code_desc_cd", length = 20)
  private String CodeDescCode;

  @Column(name = "codeset_nm", length = 256)
  private String codeSetNumber;

  @Embedded private Status status;

  protected DataSourceCodeset() {}
}

package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(catalog = "NBS_SRTE", name = "Code_value_general")
public class CodeValueGeneral {
  @EmbeddedId
  private CodeValueGeneralId id;

  @Column(name = "code_desc_txt", length = 300)
  private String codeDescTxt;

  @Column(name = "code_short_desc_txt", length = 100)
  private String codeShortDescTxt;

  @Column(name = "code_system_cd", length = 300)
  private String codeSystemCd;

  @Column(name = "code_system_desc_txt", length = 100)
  private String codeSystemDescTxt;

  @Column(name = "effective_from_time")
  private Instant effectiveFromTime;

  @Column(name = "indent_level_nbr")
  private Short indentLevelNbr;

  @Column(name = "is_modifiable_ind")
  private Character isModifiableInd;

  @Column(name = "nbs_uid")
  private Integer nbsUid;

  @Column(name = "status_cd")
  private Character statusCd;

  @Column(name = "status_time")
  private Instant statusTime;

  @Column(name = "concept_type_cd", length = 20)
  private String conceptTypeCd;

  @Column(name = "concept_code", length = 256)
  private String conceptCode;

  @Column(name = "concept_nm", length = 256)
  private String conceptNm;

  @Column(name = "concept_preferred_nm", length = 256)
  private String conceptPreferredNm;

  @Column(name = "concept_status_cd", length = 256)
  private String conceptStatusCd;

  @Column(name = "concept_status_time")
  private Instant conceptStatusTime;

  @Column(name = "add_time")
  private Instant addTime;

  @Column(name = "add_user_id")
  private Long addUserId;

}

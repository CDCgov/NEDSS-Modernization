package gov.cdc.nbs.entity.srte;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Jurisdiction_code")
public class JurisdictionCode {
  @Id
  @Column(name = "code", nullable = false, length = 20)
  private String id;

  @Column(name = "type_cd", nullable = false, length = 20)
  private String typeCd;

  @Column(name = "assigning_authority_cd", length = 199)
  private String assigningAuthorityCd;

  @Column(name = "assigning_authority_desc_txt", length = 100)
  private String assigningAuthorityDescTxt;

  @Column(name = "code_desc_txt")
  private String codeDescTxt;

  @Column(name = "code_short_desc_txt", length = 50)
  private String codeShortDescTxt;

  @Column(name = "indent_level_nbr")
  private Short indentLevelNbr;

  @Column(name = "is_modifiable_ind")
  private Character isModifiableInd;

  @Column(name = "state_domain_cd", length = 20)
  private String stateDomainCd;

  @Column(name = "code_set_nm", length = 256)
  private String codeSetNm;

  @Column(name = "code_seq_num")
  private Short codeSeqNum;

  @Column(name = "nbs_uid")
  private Integer nbsUid;
}

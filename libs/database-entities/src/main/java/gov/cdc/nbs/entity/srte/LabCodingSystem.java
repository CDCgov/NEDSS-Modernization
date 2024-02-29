package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(catalog = "NBS_SRTE", name = "Lab_coding_system")
public class LabCodingSystem implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "laboratory_id", nullable = false, length = 20)
  private String id;

  @Column(name = "laboratory_system_desc_txt", length = 100)
  private String laboratorySystemDescTxt;

  @Column(name = "coding_system_cd", length = 20)
  private String codingSystemCd;

  @Column(name = "code_system_desc_txt", length = 100)
  private String codeSystemDescTxt;

  @Column(name = "electronic_lab_ind")
  private Character electronicLabInd;

  @Column(name = "assigning_authority_cd", length = 199)
  private String assigningAuthorityCd;

  @Column(name = "assigning_authority_desc_txt", length = 100)
  private String assigningAuthorityDescTxt;

  @Column(name = "nbs_uid")
  private Long nbsUid;

}

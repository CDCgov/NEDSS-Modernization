package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(catalog = "NBS_SRTE", name = "Lab_result")
public class LabResult implements Serializable {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EmbeddedId
  private LabResultId id;

  @MapsId("laboratoryId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "laboratory_id", nullable = false)
  private LabCodingSystem laboratory;

  @Column(name = "lab_result_desc_txt", length = 50)
  private String labResultDescTxt;

  @Column(name = "nbs_uid")
  private Long nbsUid;

  @Column(name = "organism_name_ind")
  private Character organismNameInd;

  @Column(name = "pa_derivation_exclude_cd")
  private Character paDerivationExcludeCd;

  @Column(name = "code_set_nm", length = 256)
  private String codeSetNm;


}

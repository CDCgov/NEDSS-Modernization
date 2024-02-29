package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(catalog = "NBS_SRTE", name = "Snomed_code")
public class SnomedCode implements Serializable {
  @Id
  @Column(name = "snomed_cd", nullable = false, length = 20)
  private String id;

  @Column(name = "snomed_desc_txt", length = 100)
  private String snomedDescTxt;

  @Column(name = "source_concept_id", nullable = false, length = 20)
  private String sourceConceptId;

  @Column(name = "source_version_id", nullable = false, length = 20)
  private String sourceVersionId;

  @Column(name = "status_cd")
  private Character statusCd;

  @Column(name = "status_time")
  private Instant statusTime;

  @Column(name = "nbs_uid")
  private Integer nbsUid;

  @Column(name = "pa_derivation_exclude_cd")
  private Character paDerivationExcludeCd;

}

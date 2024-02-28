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
@Table(catalog = "NBS_SRTE", name = "Lab_test")
public class LabTest implements Serializable {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EmbeddedId
  private LabTestId id;

  @MapsId("laboratoryId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "laboratory_id", nullable = false)
  private LabCodingSystem laboratory;

  @Column(name = "lab_test_desc_txt", length = 100)
  private String labTestDescTxt;

  @Column(name = "test_type_cd", nullable = false, length = 20)
  private String testTypeCd;

  @Column(name = "nbs_uid", nullable = false)
  private Long nbsUid;

  @Column(name = "pa_derivation_exclude_cd")
  private Character paDerivationExcludeCd;

}

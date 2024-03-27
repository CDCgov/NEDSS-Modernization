package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
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

package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Entity
@Builder
@Table(catalog = "NBS_SRTE", name = "LOINC_code")
public class LoincCode implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "loinc_cd", nullable = false, length = 20)
  private String id;

  @Column(name = "component_name", length = 200)
  private String componentName;

  @Column(name = "property", length = 10)
  private String property;

  @Column(name = "time_aspect", length = 10)
  private String timeAspect;

  @Column(name = "system_cd", length = 50)
  private String systemCd;

  @Column(name = "scale_type", length = 20)
  private String scaleType;

  @Column(name = "method_type", length = 50)
  private String methodType;

  @Column(name = "nbs_uid")
  private Long nbsUid;

  @Column(name = "related_class_cd", length = 50)
  private String relatedClassCd;

  @Column(name = "pa_derivation_exclude_cd")
  private Character paDerivationExcludeCd;

}

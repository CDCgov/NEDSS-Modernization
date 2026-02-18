package gov.cdc.nbs.questionbank.entity.question;

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
@Table(name = "NBS_ui_component")
public class NbsUiComponent {
  @Id
  @Column(name = "nbs_ui_component_uid", nullable = false)
  private Long id;

  @Column(name = "type_cd", length = 10)
  private String typeCd;

  @Column(name = "type_cd_desc", length = 50)
  private String typeCdDesc;

  @Column(name = "ldf_available_ind", length = 10, columnDefinition = "CHAR")
  private String ldfAvailableInd;

  @Column(name = "display_order")
  private Integer displayOrder;

  @Column(name = "component_behavior", length = 20)
  private String componentBehavior;
}

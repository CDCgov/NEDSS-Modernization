package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
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
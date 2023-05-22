package gov.cdc.nbs.authentication.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "Auth_bus_obj_type", catalog = "NBS_ODSE")
public class AuthBusObjType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_bus_obj_type_uid", nullable = false)
    private Long id;

    @Column(name = "bus_obj_nm", length = 100)
    private String busObjNm;

    @Column(name = "bus_obj_disp_nm", length = 1000)
    private String busObjDispNm;

    @Column(name = "prog_area_ind")
    private Character progAreaInd;

    @Column(name = "jurisdiction_ind")
    private Character jurisdictionInd;

    @Embedded
    private AuthAudit audit;

    @Column(name = "operation_sequence", nullable = false)
    private Integer operationSequence;

}

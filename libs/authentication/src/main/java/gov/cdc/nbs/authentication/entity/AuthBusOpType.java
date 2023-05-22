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
@Table(name = "Auth_bus_op_type", catalog = "NBS_ODSE")
@Builder
public class AuthBusOpType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_bus_op_type_uid", nullable = false)
    private Long id;

    @Column(name = "bus_op_nm", length = 100)
    private String busOpNm;

    @Column(name = "bus_op_disp_nm", length = 1000)
    private String busOpDispNm;

    @Column(name = "operation_sequence", nullable = false)
    private Integer operationSequence;

    @Embedded
    private AuthAudit audit;

}

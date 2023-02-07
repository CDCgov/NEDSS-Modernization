package gov.cdc.nbs.entity.odse;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import gov.cdc.nbs.entity.enums.RecordStatus;
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
@Table(name = "Auth_bus_op_type")
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

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_status_cd", nullable = false, length = 20)
    private RecordStatus recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "operation_sequence", nullable = false)
    private Integer operationSequence;

}
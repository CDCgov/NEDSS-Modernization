package gov.cdc.nbs.authentication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "Auth_bus_op_rt", catalog = "NBS_ODSE")
public class AuthBusOpRt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_bus_op_rt_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "auth_bus_op_type_uid", nullable = false)
    private AuthBusOpType authBusOpTypeUid;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "auth_bus_obj_rt_uid", nullable = false)
    private AuthBusObjRt authBusObjRtUid;

    @Column(name = "bus_op_user_rt")
    private Character busOpUserRt;

    @Column(name = "bus_op_guest_rt")
    private Character busOpGuestRt;

    @Embedded
    private AuthAudit audit;

}

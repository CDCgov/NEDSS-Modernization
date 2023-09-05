package gov.cdc.nbs.authentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
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

    AuthBusOpRt() {

    }

    public AuthBusOpRt(final AuthBusOpType type, final AuthBusObjRt right) {
        this.authBusOpTypeUid = type;
        this.authBusObjRtUid = right;
        this.busOpGuestRt = 'T';    // TODO: what decides this value?
        this.busOpUserRt = 'T';     // TODO: what decides this value?
        this.audit = new AuthAudit(right.audit());
    }

}

package gov.cdc.nbs.authentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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

    @SuppressWarnings(
        //  Bidirectional mappings require knowledge of each other
        "javaarchitecture:S7027"
    )
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "auth_bus_obj_rt_uid", nullable = false)
    private AuthBusObjRt authBusObjRtUid;

    @Column(name = "bus_op_user_rt")
    private Character busOpUserRt;

    @Column(name = "bus_op_guest_rt")
    private Character busOpGuestRt;

    @Embedded
    private AuthAudit audit;

    protected AuthBusOpRt() {

    }

    public AuthBusOpRt(final AuthBusOpType type, final AuthBusObjRt right) {
        this();
        this.authBusOpTypeUid = type;
        this.authBusObjRtUid = right;

        //  This class is currently used only by tests.  It is not clear what the values for the following fields should be nor what decides these values.
        this.busOpGuestRt = 'T';    // what decides this value?
        this.busOpUserRt = 'T';     // what decides this value?

        this.audit = new AuthAudit(right.audit());
    }

    public AuthBusOpType operationType() {
        return authBusOpTypeUid;
    }

    public AuthBusObjRt objectRight() {
        return authBusObjRtUid;
    }
}

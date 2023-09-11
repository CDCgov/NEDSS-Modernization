package gov.cdc.nbs.authentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Auth_bus_obj_rt", catalog = "NBS_ODSE")
public class AuthBusObjRt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_bus_obj_rt_uid", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "auth_perm_set_uid", nullable = false, updatable = false)
    private AuthPermSet authPermSetUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "auth_bus_obj_type_uid", nullable = false)
    private AuthBusObjType authBusObjTypeUid;

    @OneToOne(
        mappedBy = "authBusObjRtUid",
        cascade = {
            CascadeType.PERSIST,
            CascadeType.REMOVE
        },
        orphanRemoval = true
    )
    private AuthBusOpRt operationRight;

    @Embedded
    private AuthAudit audit;

    AuthBusObjRt() {

    }

    public AuthBusObjRt(
        final AuthPermSet set,
        final AuthBusObjType operationType,
        final AuthBusOpType objectType
    ) {
        this();
        this.authPermSetUid = set;
        this.authBusObjTypeUid = operationType;
        this.audit = new AuthAudit(set.audit());
        this.operationRight = new AuthBusOpRt(objectType, this);
    }

    public AuthPermSet permissionSet() {
        return authPermSetUid;
    }

    public AuthBusObjType objectType() {
        return authBusObjTypeUid;
    }

    public AuthBusOpRt operationRight() {
        return operationRight;
    }

    public AuthAudit audit() {
        return audit;
    }

}

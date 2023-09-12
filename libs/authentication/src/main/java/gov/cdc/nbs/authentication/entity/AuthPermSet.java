package gov.cdc.nbs.authentication.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "Auth_perm_set", catalog = "NBS_ODSE")
public class AuthPermSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_perm_set_uid", nullable = false)
    private Long id;

    @Column(name = "perm_set_nm", length = 100)
    private String permSetNm;

    @Column(name = "perm_set_desc", length = 1000)
    private String permSetDesc;

    @Column(name = "sys_defined_perm_set_ind")
    private Character sysDefinedPermSetInd;

    @OneToMany(
        mappedBy = "authPermSetUid",
        fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.REMOVE
        },
        orphanRemoval = true
    )
    private Collection<AuthBusObjRt> rights;

    @Embedded
    private AuthAudit audit;

    public Long id() {
        return id;
    }

    public String name() {
        return permSetNm;
    }

    public AuthPermSet name(final String name) {
        this.permSetNm = name;
        return this;
    }

    public String description() {
        return permSetDesc;
    }

    public AuthPermSet description(final String permSetDesc) {
        this.permSetDesc = permSetDesc;
        return this;
    }

    public Character systemDefined() {
        return sysDefinedPermSetInd;
    }

    public AuthPermSet systemDefined(final Character sysDefinedPermSetInd) {
        this.sysDefinedPermSetInd = sysDefinedPermSetInd;
        return this;
    }

    public AuthAudit audit() {
        return audit;
    }

    public AuthPermSet audit(final AuthAudit audit) {
        this.audit = audit;
        return this;
    }

    private Collection<AuthBusObjRt> ensureRights() {
        if (this.rights == null) {
            this.rights = new ArrayList<>();
        }
        return this.rights;
    }

    public AuthBusObjRt addObjectRight(final AuthBusOpType operation, final AuthBusObjType object) {
        AuthBusObjRt right = new AuthBusObjRt(
            this,
            object,
            operation
        );

        ensureRights().add(right);

        return right;
    }

    public Collection<AuthBusObjRt> objectRights() {
        return this.rights == null ? List.of() : List.copyOf(this.rights);
    }
}

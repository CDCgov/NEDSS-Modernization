package gov.cdc.nbs.authentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
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
@Table(name = "Auth_bus_obj_rt", catalog = "NBS_ODSE")
@SuppressWarnings("javaarchitecture:S7027")
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

  @SuppressWarnings(
      //  Bidirectional mappings require knowledge of each other
      "javaarchitecture:S7027")
  @OneToOne(
      mappedBy = "authBusObjRtUid",
      cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      orphanRemoval = true)
  private AuthBusOpRt operationRight;

  @Embedded private AuthAudit audit;

  protected AuthBusObjRt() {}

  public AuthBusObjRt(
      final AuthPermSet set, final AuthBusObjType operationType, final AuthBusOpType objectType) {
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

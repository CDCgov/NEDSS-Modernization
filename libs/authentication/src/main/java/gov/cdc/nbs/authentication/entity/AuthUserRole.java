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
import jakarta.persistence.Table;

@Entity
@Table(name = "Auth_user_role", catalog = "NBS_ODSE")
public class AuthUserRole {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "auth_user_role_uid", nullable = false)
  private Long id;

  @Column(name = "auth_role_nm", length = 100)
  private String authRoleNm;

  @Column(name = "prog_area_cd", length = 100)
  private String progAreaCd;

  @Column(name = "jurisdiction_cd", length = 100)
  private String jurisdictionCd;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "auth_user_uid", nullable = false)
  private AuthUser authUserUid;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "auth_perm_set_uid", nullable = false)
  private AuthPermSet authPermSetUid;

  @Column(name = "role_guest_ind")
  private Character roleGuestInd;

  @Column(name = "read_only_ind")
  private Character readOnlyInd;

  @Column(name = "disp_seq_nbr")
  private Integer dispSeqNbr;

  @Embedded private AuthAudit audit;

  protected AuthUserRole() {
    this.readOnlyInd = 'T'; // not used always "T"
  }

  public AuthUserRole(final AuthUser user, final AuthPermSet set) {
    this();
    this.authUserUid = user;
    this.authPermSetUid = set;
  }

  public Long id() {
    return id;
  }

  public Character forReadOnly() {
    return readOnlyInd;
  }

  public String name() {
    return authRoleNm;
  }

  public AuthUserRole name(final String name) {
    this.authRoleNm = name;
    return this;
  }

  public String programArea() {
    return progAreaCd;
  }

  public AuthUserRole programArea(final String programArea) {
    this.progAreaCd = programArea;
    return this;
  }

  public String jurisdiction() {
    return jurisdictionCd;
  }

  public AuthUserRole jurisdiction(final String jurisdiction) {
    this.jurisdictionCd = jurisdiction;
    return this;
  }

  public AuthUser user() {
    return authUserUid;
  }

  public AuthPermSet permissionSet() {
    return authPermSetUid;
  }

  public Character guest() {
    return roleGuestInd;
  }

  public AuthUserRole guest(final Character roleGuestInd) {
    this.roleGuestInd = roleGuestInd;
    return this;
  }

  public Integer sequence() {
    return dispSeqNbr;
  }

  public AuthUserRole sequence(final Integer sequence) {
    this.dispSeqNbr = sequence;
    return this;
  }

  public AuthAudit audit() {
    return audit;
  }

  public AuthUserRole audit(final AuthAudit audit) {
    this.audit = audit;
    return this;
  }
}

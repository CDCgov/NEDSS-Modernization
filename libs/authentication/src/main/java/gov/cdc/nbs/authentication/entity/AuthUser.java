package gov.cdc.nbs.authentication.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "Auth_user", catalog = "NBS_ODSE")
@SuppressWarnings("javaarchitecture:S7027")
public class AuthUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "auth_user_uid", nullable = false)
  private Long id;

  @SuppressWarnings(
      //  Bidirectional mappings require knowledge of each other
      "javaarchitecture:S7027"
  )
  @OneToMany(
      mappedBy = "authUserUid",
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.MERGE,
          CascadeType.REMOVE,
          CascadeType.PERSIST
      },
      orphanRemoval = true
  )
  private List<AuthUserRole> authUserRoles;

  @OneToMany(
      mappedBy = "authUserUid",
      fetch = FetchType.LAZY,
      cascade = {
          CascadeType.MERGE,
          CascadeType.REMOVE,
          CascadeType.PERSIST
      },
      orphanRemoval = true
  )
  private List<AuthProgAreaAdmin> adminProgramAreas;

  @Column(name = "user_id", length = 256)
  private String userId;

  @Column(name = "user_type", length = 100)
  private String userType;

  @Column(name = "user_first_nm", length = 100)
  private String userFirstNm;

  @Column(name = "user_last_nm", length = 100)
  private String userLastNm;

  @Column(name = "master_sec_admin_ind")
  private Character masterSecAdminInd;

  @Column(name = "prog_area_admin_ind")
  private Character progAreaAdminInd;

  @Column(name = "nedss_entry_id", nullable = false)
  private Long nedssEntryId;

  @Embedded
  private AuthAudit audit;

  public Long id() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAuthUserRoles(List<AuthUserRole> authUserRoles) {
    this.authUserRoles = authUserRoles;
  }

  public void setAdminProgramAreas(List<AuthProgAreaAdmin> adminProgramAreas) {
    this.adminProgramAreas = adminProgramAreas;
  }

  public String userId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public void setUserFirstNm(String userFirstNm) {
    this.userFirstNm = userFirstNm;
  }

  public void setUserLastNm(String userLastNm) {
    this.userLastNm = userLastNm;
  }

  public void setMasterSecAdminInd(Character masterSecAdminInd) {
    this.masterSecAdminInd = masterSecAdminInd;
  }

  public void setProgAreaAdminInd(Character progAreaAdminInd) {
    this.progAreaAdminInd = progAreaAdminInd;
  }

  public void setNedssEntryId(Long nedssEntryId) {
    this.nedssEntryId = nedssEntryId;
  }

  public AuthAudit audit() {
    return audit;
  }

  public void setAudit(AuthAudit audit) {
    this.audit = audit;
  }
}

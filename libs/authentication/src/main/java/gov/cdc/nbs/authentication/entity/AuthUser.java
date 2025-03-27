package gov.cdc.nbs.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @Column(name = "user_title", length = 100)
    private String userTitle;

    @Column(name = "user_department", length = 100)
    private String userDepartment;

    @Column(name = "user_first_nm", length = 100)
    private String userFirstNm;

    @Column(name = "user_last_nm", length = 100)
    private String userLastNm;

    @Column(name = "user_work_email", length = 100)
    private String userWorkEmail;

    @Column(name = "user_work_phone", length = 100)
    private String userWorkPhone;

    @Column(name = "user_mobile_phone", length = 100)
    private String userMobilePhone;

    @Column(name = "master_sec_admin_ind")
    private Character masterSecAdminInd;

    @Column(name = "prog_area_admin_ind")
    private Character progAreaAdminInd;

    @Column(name = "nedss_entry_id", nullable = false)
    private Long nedssEntryId;

    @Column(name = "external_org_uid")
    private String externalOrgUid;

    @Column(name = "user_password", length = 100)
    private String userPassword;

    @Column(name = "user_comments", length = 100)
    private String userComments;

    @Embedded
    private AuthAudit audit;

    @Column(name = "jurisdiction_derivation_ind")
    private Character jurisdictionDerivationInd;

    @Column(name = "provider_uid")
    private String providerUid;

}

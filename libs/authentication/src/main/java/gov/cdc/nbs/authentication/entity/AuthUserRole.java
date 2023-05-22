package gov.cdc.nbs.authentication.entity;

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

    @Embedded
    private AuthAudit audit;

}

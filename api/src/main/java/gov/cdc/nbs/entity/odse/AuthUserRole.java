package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Auth_user_role")
public class AuthUserRole {
    @Id
    @Column(name = "auth_user_role_uid", nullable = false)
    private Long id;

    @Column(name = "auth_role_nm", length = 100)
    private String authRoleNm;

    @Column(name = "prog_area_cd", length = 100)
    private String progAreaCd;

    @Column(name = "jurisdiction_cd", length = 100)
    private String jurisdictionCd;

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

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

}
package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Tele_locator_hist")
public class TeleLocatorHist {
    @EmbeddedId
    private TeleLocatorHistId id;

    @MapsId("teleLocatorUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tele_locator_uid", nullable = false)
    private TeleLocator teleLocatorUid;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "cntry_cd", length = 20)
    private String cntryCd;

    @Column(name = "email_address", length = 100)
    private String emailAddress;

    @Column(name = "extension_txt", length = 20)
    private String extensionTxt;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "phone_nbr_txt", length = 20)
    private String phoneNbrTxt;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "url_address", length = 100)
    private String urlAddress;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

}
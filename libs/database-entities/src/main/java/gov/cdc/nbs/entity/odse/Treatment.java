package gov.cdc.nbs.entity.odse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@SuppressWarnings("javaarchitecture:S7027")
public class Treatment {
    @Id
    @Column(name = "treatment_uid", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.REMOVE
    }, optional = false)
    @JoinColumn(name = "treatment_uid", nullable = false)
    private Act act;

    @OneToMany(mappedBy = "treatmentUid", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
    })
    private List<TreatmentAdministered> administered;

    @Column(name = "activity_duration_amt", length = 20)
    private String activityDurationAmt;

    @Column(name = "activity_duration_unit_cd", length = 20)
    private String activityDurationUnitCd;

    @Column(name = "activity_from_time")
    private Instant activityFromTime;

    @Column(name = "activity_to_time")
    private Instant activityToTime;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "cd", length = 50)
    private String cd;

    @Column(name = "cd_desc_txt", length = 150)
    private String cdDescTxt;

    @Column(name = "cd_system_cd", length = 20)
    private String cdSystemCd;

    @Column(name = "cd_system_desc_txt", length = 100)
    private String cdSystemDescTxt;

    @Column(name = "cd_version", length = 10)
    private String cdVersion;

    @Column(name = "class_cd", length = 10)
    private String classCd;

    @Column(name = "jurisdiction_cd", length = 20)
    private String jurisdictionCd;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "prog_area_cd", length = 20)
    private String progAreaCd;

    @Column(name = "program_jurisdiction_oid")
    private Long programJurisdictionOid;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "shared_ind")
    private Character sharedInd;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "txt", length = 1000)
    private String txt;

    @Column(name = "version_ctrl_nbr")
    private Short versionCtrlNbr;

    public Treatment(long identifier, String localId) {
        this.id = identifier;
        this.localId = localId;
        this.sharedInd = 'F';
        this.versionCtrlNbr = 1;

        this.act = new Act(identifier, "TRMT");

    }

    private List<TreatmentAdministered> ensureAdministered() {
        if (this.administered == null) {
            this.administered = new ArrayList<>();
        }
        return this.administered;
    }

    public List<TreatmentAdministered> getAdministered() {
        return this.administered == null ? List.of() : List.copyOf(this.administered);
    }

    public TreatmentAdministered administer() {
        TreatmentAdministered given = new TreatmentAdministered(this);
        ensureAdministered().add(given);
        return given;
    }
}

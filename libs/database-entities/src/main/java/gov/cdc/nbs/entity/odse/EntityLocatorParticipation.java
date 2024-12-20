package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.patient.PatientEntityLocatorHistoryListener;
import gov.cdc.nbs.patient.PatientCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.function.Predicate;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Entity_locator_participation")
@SuppressWarnings(
    //  The PatientEntityLocatorHistoryListener is an entity listener specifically for instances of this class
    {"javaarchitecture:S7027", "javaarchitecture:S7091"}
)
@EntityListeners(PatientEntityLocatorHistoryListener.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class_cd", discriminatorType = DiscriminatorType.STRING)
public abstract class EntityLocatorParticipation {

    public static <V extends EntityLocatorParticipation> Predicate<V> active() {
        return input -> Objects.equals(input.getRecordStatusCd(), RecordStatus.ACTIVE.name());
    }

    @EmbeddedId
    private EntityLocatorParticipationId id;

    @MapsId("entityUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_uid", nullable = false)
    private NBSEntity nbsEntity;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "cd", length = 50)
    protected String cd;

    @Column(name = "cd_desc_txt", length = 100)
    private String cdDescTxt;

    @Column(name = "duration_amt", length = 20)
    private String durationAmt;

    @Column(name = "duration_unit_cd", length = 20)
    private String durationUnitCd;

    @Column(name = "from_time")
    private Instant fromTime;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "locator_desc_txt", length = 2000)
    protected String locatorDescTxt;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "to_time")
    private Instant toTime;

    @Column(name = "use_cd", length = 20)
    protected String useCd;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "valid_time_txt", length = 100)
    private String validTimeTxt;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Column(name = "as_of_date")
    protected Instant asOfDate;

    protected EntityLocatorParticipation() {
    }

    protected EntityLocatorParticipation(
            final PatientCommand command,
            final NBSEntity nbs,
            final EntityLocatorParticipationId identifier) {
        this.id = identifier;
        this.nbsEntity = nbs;

        this.addUserId = command.requester();
        this.addTime = command.requestedOn();

        this.lastChgTime = command.requestedOn();
        this.lastChgUserId = command.requester();

        this.recordStatusCd = "ACTIVE";
        this.recordStatusTime = command.requestedOn();

        this.statusCd = 'A';
        this.statusTime = command.requestedOn();

        this.versionCtrlNbr = 1;
    }

    public abstract Locator getLocator();

    public abstract String getClassCd();

    protected void changed(final PatientCommand command) {
        this.versionCtrlNbr = (short) (this.versionCtrlNbr + 1);

        this.lastChgTime = command.requestedOn();
        this.lastChgUserId = command.requester();
    }

    protected void changeStatus(final RecordStatus status, final Instant when) {
        this.recordStatusCd = status.name();
        this.recordStatusTime = when;
    }
}

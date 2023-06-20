package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.entity.enums.converter.SuffixConverter;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.PatientCommand;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.time.Instant;

@Getter
@Entity
@Table(name = "Person_name")
public class PersonName {
    @EmbeddedId
    private PersonNameId id;

    @MapsId("personUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_uid", nullable = false)
    private Person personUid;


    @Column(name = "default_nm_ind")
    private Character defaultNmInd;

    @Column(name = "duration_amt", length = 20)
    private String durationAmt;

    @Column(name = "duration_unit_cd", length = 20)
    private String durationUnitCd;

    @Column(name = "first_nm", length = 50)
    private String firstNm;

    @Column(name = "first_nm_sndx", length = 50)
    private String firstNmSndx;

    @Column(name = "from_time")
    private Instant fromTime;

    @Column(name = "last_nm", length = 50)
    private String lastNm;

    @Column(name = "last_nm_sndx", length = 50)
    private String lastNmSndx;

    @Column(name = "last_nm2", length = 50)
    private String lastNm2;

    @Column(name = "last_nm2_sndx", length = 50)
    private String lastNm2Sndx;

    @Column(name = "middle_nm", length = 50)
    private String middleNm;

    @Column(name = "middle_nm2", length = 50)
    private String middleNm2;

    @Column(name = "nm_degree", length = 20)
    private String nmDegree;

    @Column(name = "nm_prefix", length = 20)
    private String nmPrefix;

    @Convert(converter = SuffixConverter.class)
    @Column(name = "nm_suffix", length = 20)
    private Suffix nmSuffix;

    @Column(name = "nm_use_cd", length = 20)
    private String nmUseCd;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "status_cd", nullable = false)
    private Character statusCd;

    @Column(name = "status_time", nullable = false)
    private Instant statusTime;

    @Column(name = "to_time")
    private Instant toTime;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "as_of_date")
    private Instant asOfDate;

    @Embedded
    private Audit audit;

    PersonName() {

    }

    public PersonName(
        final PersonNameId identifier,
        final Person person,
        final PatientCommand.AddName added
    ) {
        this.asOfDate = added.asOf();

        this.statusCd = 'A';
        this.statusTime = added.requestedOn();

        this.recordStatusCd = "ACTIVE";
        this.recordStatusTime = added.requestedOn();

        this.id = identifier;
        this.personUid = person;

        this.nmPrefix = added.prefix();
        this.firstNm = added.first();
        this.middleNm = added.middle();
        this.middleNm2 = added.secondMiddle();
        this.lastNm = added.last();
        this.lastNm2 = added.secondLast();
        this.nmSuffix = Suffix.resolve(added.suffix());
        this.nmDegree = added.degree();
        this.nmUseCd = added.type();

        this.audit = new Audit(added.requester(), added.requestedOn());
    }

    public void update(final PatientCommand.UpdateNameInfo info) {
        this.asOfDate = info.asOf();
        this.nmPrefix = info.prefix();
        this.firstNm = info.first();
        this.middleNm = info.middle();
        this.middleNm2 = info.secondMiddle();
        this.lastNm = info.last();
        this.lastNm2 = info.secondLast();
        this.nmSuffix = Suffix.resolve(info.suffix());
        this.nmDegree = info.degree();
        this.nmUseCd = info.type();

        this.audit.changed(info.requester(), info.requestedOn());
    }

}

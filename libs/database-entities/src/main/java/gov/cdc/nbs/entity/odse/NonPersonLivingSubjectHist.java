package gov.cdc.nbs.entity.odse;

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
@Table(name = "Non_Person_living_subject_hist")
public class NonPersonLivingSubjectHist {
    @EmbeddedId
    private NonPersonLivingSubjectHistId id;

    @MapsId("nonPersonUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "non_person_uid", nullable = false)
    private NonPersonLivingSubject nonPersonUid;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "birth_sex_cd")
    private Character birthSexCd;

    @Column(name = "birth_order_nbr")
    private Short birthOrderNbr;

    @Column(name = "birth_time")
    private Instant birthTime;

    @Column(name = "breed_cd", length = 20)
    private String breedCd;

    @Column(name = "breed_desc_txt", length = 100)
    private String breedDescTxt;

    @Column(name = "cd", length = 50)
    private String cd;

    @Column(name = "cd_desc_txt", length = 100)
    private String cdDescTxt;

    @Column(name = "deceased_ind_cd")
    private Character deceasedIndCd;

    @Column(name = "deceased_time")
    private Instant deceasedTime;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "multiple_birth_ind")
    private Character multipleBirthInd;

    @Column(name = "nm", length = 100)
    private String nm;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "taxonomic_classification_cd", length = 20)
    private String taxonomicClassificationCd;

    @Column(name = "taxonomic_classification_desc", length = 100)
    private String taxonomicClassificationDesc;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

}
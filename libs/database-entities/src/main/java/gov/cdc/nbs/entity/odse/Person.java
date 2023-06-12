package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.enums.converter.SuffixConverter;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.GenderConverter;
import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientCommand.AddMortalityLocator;
import gov.cdc.nbs.patient.PatientHasAssociatedEventsException;
import gov.cdc.nbs.patient.demographic.PatientEthnicity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Entity
public class Person {
    @Id
    @Column(name = "person_uid", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.REMOVE
    }, optional = false)
    @JoinColumn(name = "person_uid", nullable = false)
    private NBSEntity nbsEntity;

    @OneToMany(mappedBy = "personUid", fetch = FetchType.EAGER, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.REMOVE
    }, orphanRemoval = true)
    private List<PersonName> names;

    @OneToMany(mappedBy = "personUid", fetch = FetchType.LAZY, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.REMOVE
    }, orphanRemoval = true)
    private List<PersonRace> races;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "administrative_gender_cd", length = 20)
    private String administrativeGenderCd;

    @Column(name = "age_calc")
    private Short ageCalc;

    @Column(name = "age_calc_time")
    private Instant ageCalcTime;

    @Column(name = "age_calc_unit_cd")
    private Character ageCalcUnitCd;

    @Column(name = "age_category_cd", length = 20)
    private String ageCategoryCd;

    @Column(name = "age_reported", length = 10)
    private String ageReported;

    @Column(name = "age_reported_time")
    private Instant ageReportedTime;

    @Column(name = "age_reported_unit_cd", length = 20)
    private String ageReportedUnitCd;

    @Convert(converter = GenderConverter.class)
    @Column(name = "birth_gender_cd", columnDefinition = "CHAR")
    private Gender birthGenderCd;

    @Column(name = "birth_order_nbr")
    private Short birthOrderNbr;

    @Column(name = "birth_time")
    private Instant birthTime;

    @Column(name = "birth_time_calc")
    private Instant birthTimeCalc;

    @Column(name = "cd", length = 50)
    private String cd;

    @Column(name = "cd_desc_txt", length = 100)
    private String cdDescTxt;

    @Convert(converter = GenderConverter.class)
    @Column(name = "curr_sex_cd")
    private Gender currSexCd;

    @Enumerated(EnumType.STRING)
    @Column(name = "deceased_ind_cd", length = 20)
    @ColumnTransformer(read = "UPPER(deceased_ind_cd)")
    private Deceased deceasedIndCd;

    @Column(name = "deceased_time")
    private Instant deceasedTime;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "education_level_cd", length = 20)
    private String educationLevelCd;

    @Column(name = "education_level_desc_txt", length = 100)
    private String educationLevelDescTxt;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "marital_status_cd", length = 20)
    private String maritalStatusCd;

    @Column(name = "marital_status_desc_txt", length = 100)
    private String maritalStatusDescTxt;

    @Column(name = "mothers_maiden_nm", length = 50)
    private String mothersMaidenNm;

    @Column(name = "multiple_birth_ind", length = 20)
    private String multipleBirthInd;

    @Column(name = "occupation_cd", length = 20)
    private String occupationCd;

    @Column(name = "preferred_gender_cd", length = 20)
    private String preferredGenderCd;

    @Column(name = "prim_lang_cd", length = 20)
    private String primLangCd;

    @Column(name = "prim_lang_desc_txt", length = 100)
    private String primLangDescTxt;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_status_cd", length = 20)
    private RecordStatus recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "survived_ind_cd")
    private Character survivedIndCd;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "first_nm", length = 50)
    private String firstNm;

    @Column(name = "last_nm", length = 50)
    private String lastNm;

    @Column(name = "middle_nm", length = 50)
    private String middleNm;

    @Column(name = "nm_prefix", length = 20)
    private String nmPrefix;

    @Convert(converter = SuffixConverter.class)
    @Column(name = "nm_suffix", length = 20)
    private Suffix nmSuffix;

    @Column(name = "preferred_nm", length = 50)
    private String preferredNm;

    @Column(name = "hm_street_addr1", length = 100)
    private String hmStreetAddr1;

    @Column(name = "hm_street_addr2", length = 100)
    private String hmStreetAddr2;

    @Column(name = "hm_city_cd", length = 20)
    private String hmCityCd;

    @Column(name = "hm_city_desc_txt", length = 100)
    private String hmCityDescTxt;

    @Column(name = "hm_state_cd", length = 20)
    private String hmStateCd;

    @Column(name = "hm_zip_cd", length = 20)
    private String hmZipCd;

    @Column(name = "hm_cnty_cd", length = 20)
    private String hmCntyCd;

    @Column(name = "hm_cntry_cd", length = 20)
    private String hmCntryCd;

    @Column(name = "hm_phone_nbr", length = 20)
    private String hmPhoneNbr;

    @Column(name = "hm_phone_cntry_cd", length = 20)
    private String hmPhoneCntryCd;

    @Column(name = "hm_email_addr", length = 100)
    private String hmEmailAddr;

    @Column(name = "cell_phone_nbr", length = 20)
    private String cellPhoneNbr;

    @Column(name = "wk_street_addr1", length = 100)
    private String wkStreetAddr1;

    @Column(name = "wk_street_addr2", length = 100)
    private String wkStreetAddr2;

    @Column(name = "wk_city_cd", length = 20)
    private String wkCityCd;

    @Column(name = "wk_city_desc_txt", length = 100)
    private String wkCityDescTxt;

    @Column(name = "wk_state_cd", length = 20)
    private String wkStateCd;

    @Column(name = "wk_zip_cd", length = 20)
    private String wkZipCd;

    @Column(name = "wk_cnty_cd", length = 20)
    private String wkCntyCd;

    @Column(name = "wk_cntry_cd", length = 20)
    private String wkCntryCd;

    @Column(name = "wk_phone_nbr", length = 20)
    private String wkPhoneNbr;

    @Column(name = "wk_phone_cntry_cd", length = 20)
    private String wkPhoneCntryCd;

    @Column(name = "wk_email_addr", length = 100)
    private String wkEmailAddr;

    @Column(name = "SSN", length = 100)
    private String ssn;

    @Column(name = "medicaid_num", length = 100)
    private String medicaidNum;

    @Column(name = "dl_num", length = 100)
    private String dlNum;

    @Column(name = "dl_state_cd", length = 20)
    private String dlStateCd;

    @Column(name = "race_cd", length = 20)
    private String raceCd;

    @Column(name = "race_seq_nbr")
    private Short raceSeqNbr;

    @Column(name = "race_category_cd", length = 20)
    private String raceCategoryCd;

    @Column(name = "ethnicity_group_cd", length = 20)
    private String ethnicityGroupCd;

    @Column(name = "ethnic_group_seq_nbr")
    private Short ethnicGroupSeqNbr;

    @Column(name = "adults_in_house_nbr")
    private Short adultsInHouseNbr;

    @Column(name = "children_in_house_nbr")
    private Short childrenInHouseNbr;

    @Column(name = "birth_city_cd", length = 20)
    private String birthCityCd;

    @Column(name = "birth_city_desc_txt", length = 100)
    private String birthCityDescTxt;

    @Column(name = "birth_cntry_cd", length = 20)
    private String birthCntryCd;

    @Column(name = "birth_state_cd", length = 20)
    private String birthStateCd;

    @Column(name = "race_desc_txt", length = 100)
    private String raceDescTxt;

    @Column(name = "ethnic_group_desc_txt", length = 100)
    private String ethnicGroupDescTxt;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Column(name = "as_of_date_admin")
    private Instant asOfDateAdmin;

    @Column(name = "as_of_date_general")
    private Instant asOfDateGeneral;

    @Column(name = "as_of_date_morbidity")
    private Instant asOfDateMorbidity;

    @Column(name = "as_of_date_sex")
    private Instant asOfDateSex;

    @Column(name = "electronic_ind")
    private Character electronicInd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_parent_uid")
    private Person personParentUid;

    @Column(name = "dedup_match_ind")
    private Character dedupMatchInd;

    @Column(name = "group_nbr")
    private Integer groupNbr;

    @Column(name = "group_time")
    private Instant groupTime;

    @Column(name = "edx_ind", length = 1)
    private String edxInd;

    @Column(name = "speaks_english_cd", length = 20)
    private String speaksEnglishCd;

    @Column(name = "additional_gender_cd", length = 50)
    private String additionalGenderCd;

    @Column(name = "ehars_id", length = 20)
    private String eharsId;

    @Column(name = "sex_unk_reason_cd", length = 20)
    private String sexUnkReasonCd;

    @Embedded
    private PatientEthnicity ethnicity;

    protected Person() {

    }

    public Person(final long identifier, final String localId) {
        this.id = identifier;
        this.localId = localId;
        this.nbsEntity = new NBSEntity(identifier, "PSN");
        this.ethnicity = new PatientEthnicity();

        this.personParentUid = this;
        this.versionCtrlNbr = 1;
        this.cd = "PAT";
        this.electronicInd = 'N';
        this.edxInd = "Y";
        this.statusCd = 'A';
        this.recordStatusCd = RecordStatus.ACTIVE;
    }

    public Person(final PatientCommand.AddPatient patient) {

        this(patient.person(), patient.localId());

        this.nbsEntity = new NBSEntity(patient);

        if (patient.dateOfBirth() != null) {
            this.birthTime = patient.dateOfBirth().atStartOfDay(ZoneOffset.UTC).toInstant();
            this.birthTimeCalc = this.birthTime;
        }

        this.birthGenderCd = patient.birthGender();
        this.currSexCd = patient.currentGender();

        this.deceasedIndCd = patient.deceased();
        this.deceasedTime = patient.deceasedTime();

        this.maritalStatusCd = patient.maritalStatus();

        this.description = patient.comments();
        this.eharsId = patient.stateHIVCase();

        this.asOfDateGeneral = patient.asOf();
        this.asOfDateSex = patient.asOf();
        this.asOfDateAdmin = patient.asOf();
        this.asOfDateMorbidity = patient.asOf();

        this.ethnicity = new PatientEthnicity(patient);

        this.statusTime = patient.requestedOn();

        this.recordStatusTime = patient.requestedOn();

        this.addTime = patient.requestedOn();
        this.addUserId = patient.requester();

        this.lastChgTime = patient.requestedOn();
        this.lastChgUserId = patient.requester();

    }

    public Person revise(final PatientCommand.Revise revise) {

        Person revision = new Person(revise.person(), this.localId);

        revision.statusTime = revise.requestedOn();
        revision.recordStatusCd = RecordStatus.ACTIVE;
        revision.recordStatusTime = revise.requestedOn();

        revision.addTime = revise.requestedOn();
        revision.addUserId = revise.requester();
        revision.personParentUid = this;

        return revision;
    }

    public PersonName add(final PatientCommand.AddName added) {

        Collection<PersonName> existing = ensureNames();

        if (existing.isEmpty()) {
            this.firstNm = added.first();
            this.middleNm = added.middle();
            this.lastNm = added.last();
            this.nmSuffix = added.suffix();
        }

        PersonNameId identifier = new PersonNameId(this.id, (short) (existing.size() + 1));

        PersonName personName = new PersonName(
            identifier,
            this,
            added
        );

        existing.add(personName);

        return personName;
    }

    private Collection<PersonName> ensureNames() {
        if (this.names == null) {
            this.names = new ArrayList<>();
        }
        return this.names;
    }

    public List<PersonName> getNames() {
        return this.names == null ? List.of() : List.copyOf(this.names);
    }

    public EntityId add(final PatientCommand.AddIdentification added) {
        return this.nbsEntity.add(added);
    }

    public PersonRace add(final PatientCommand.AddRace added) {
        Collection<PersonRace> existing = ensureRaces();

        PersonRace personRace = new PersonRace(this, added);

        existing.add(personRace);

        return personRace;

    }

    private Collection<PersonRace> ensureRaces() {
        if (this.races == null) {
            this.races = new ArrayList<>();
        }
        return this.races;
    }

    public List<PersonRace> getRaces() {
        return this.races == null ? List.of() : List.copyOf(this.races);
    }

    public EntityLocatorParticipation add(final PatientCommand.AddAddress address) {
        return this.nbsEntity.add(address);
    }

    public Collection<PostalEntityLocatorParticipation> addresses() {
        return this.nbsEntity.addresses();
    }

    public Collection<TeleEntityLocatorParticipation> phoneNumbers() {
        return this.nbsEntity.phoneNumbers();
    }

    public Collection<TeleEntityLocatorParticipation> emailAddresses() {
        return this.nbsEntity.emailAddress();
    }

    public List<EntityId> identifications() {
        return this.nbsEntity.getEntityIds();
    }

    public EntityLocatorParticipation add(final PatientCommand.AddPhoneNumber phoneNumber) {
        return this.nbsEntity.add(phoneNumber);
    }

    public EntityLocatorParticipation add(final PatientCommand.AddEmailAddress emailAddress) {
        return this.nbsEntity.add(emailAddress);
    }

    public EntityLocatorParticipation add(AddMortalityLocator mortality) {
        return this.nbsEntity.add(mortality);
    }

    public Optional<EntityLocatorParticipation> update(final PatientCommand.UpdateAddress address) {
        return this.nbsEntity.update(address);
    }

    public Optional<EntityLocatorParticipation> update(final PatientCommand.UpdatePhoneNumber phoneNumber) {
        return this.nbsEntity.update(phoneNumber);
    }

    public Optional<EntityLocatorParticipation> update(final PatientCommand.UpdateEmailAddress emailAddress) {
        return this.nbsEntity.update(emailAddress);
    }

    public boolean delete(final PatientCommand.DeleteMortalityLocator mortality) {
        return this.nbsEntity.delete(mortality);
    }

    public boolean delete(final PatientCommand.DeleteAddress address) {
        return this.nbsEntity.delete(address);
    }

    public boolean delete(final PatientCommand.DeletePhoneNumber phoneNumber) {
        return this.nbsEntity.delete(phoneNumber);
    }

    public boolean delete(final PatientCommand.DeleteEmailAddress emailAddress) {
        return this.nbsEntity.delete(emailAddress);
    }

    public void update(PatientCommand.UpdateGeneralInfo info) {
        this.setAsOfDateGeneral(info.asOf());
        this.setMaritalStatusCd(info.maritalStatus());
        this.setMothersMaidenNm(info.mothersMaidenName());
        this.setAdultsInHouseNbr(info.adultsInHouseNumber());
        this.setChildrenInHouseNbr(info.childrenInHouseNumber());
        this.setOccupationCd(info.occupationCode());
        this.setEducationLevelCd(info.educationLevelCode());
        this.setPrimLangCd(info.primaryLanguageCode());
        this.setSpeaksEnglishCd(info.speaksEnglishCode());
        this.setEharsId(info.eharsId());

        changed(info);
    }

    public void update(PatientCommand.UpdateAdministrativeInfo info) {
        this.setDescription(info.description());
        changed(info);
    }

    public void update(PatientCommand.AddName info) {
        this.add(info);
        changed(info);
    }

    public void update(PatientCommand.UpdateNameInfo info) {
        this.setAsOfDateGeneral(info.asOf());

        Collection<PersonName> existing = ensureNames();
        PersonNameId identifier = new PersonNameId(info.person(), info.personNameSeq());

        existing.stream().filter(p -> p.getId() != null && p.getId().equals(identifier)).findFirst().ifPresent(p -> {
            p.setFirstNm(info.first());
            p.setMiddleNm(info.middle());
            p.setLastNm(info.last());
            p.setNmSuffix(info.suffix());
            p.setNmUseCd(info.type());
        });

        changed(info);
    }

    public void update(PatientCommand.DeleteNameInfo info) {
        PersonNameId identifier = new PersonNameId(info.person(), info.personNameSeq());
        List<PersonName> arraylist = new ArrayList<>(this.names);
        arraylist.removeIf(item -> (item.getId().equals(identifier)));
        this.names = arraylist;
        changed(info);
    }

    public void update(PatientCommand.UpdateSexAndBirthInfo info) {
        this.setBirthGenderCd(info.birthGender());
        this.setCurrSexCd(info.currentGender());
        this.setBirthTime(info.dateOfBirth().atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.setAsOfDateSex(info.asOf());
        this.setAgeReported(info.currentAge());
        this.setAgeReportedTime(info.ageReportedTime());
        this.setBirthCityCd(info.birthCity());
        this.setBirthCntryCd(info.birthCntry());
        this.setBirthStateCd(info.birthState());
        this.setBirthOrderNbr(info.birthOrderNbr());
        this.setMultipleBirthInd(info.multipleBirth());
        this.setSexUnkReasonCd(info.sexUnknown());
        this.setAdditionalGenderCd(info.additionalGender());
        this.setPreferredGenderCd(info.transGenderInfo());

        changed(info);
    }

    public void delete(
        final PatientCommand.Delete delete,
        final PatientAssociationCountFinder finder) throws PatientHasAssociatedEventsException {

        long associations = finder.count(this.id);

        if (associations > 0) {
            throw new PatientHasAssociatedEventsException(this.id);
        }

        this.recordStatusCd = RecordStatus.LOG_DEL;
        this.recordStatusTime = delete.requestedOn();

        changed(delete);
    }

    private void changed(final PatientCommand command) {
        this.versionCtrlNbr = (short) (this.versionCtrlNbr + 1);

        this.lastChgUserId = command.requester();
        this.lastChgTime = command.requestedOn();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return this.id == null ? System.identityHashCode(this) : Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            '}';
    }

    public void update(PatientCommand.AddIdentification info) {
        this.add(info);

        changed(info);
    }

    public void update(PatientCommand.UpdateIdentification info) {
        this.nbsEntity.update(info);

        changed(info);
    }

    public void delete(PatientCommand.DeleteIdentification info) {
        this.nbsEntity.delete(info);

        changed(info);
    }

    public void update(final PatientCommand.UpdateEthnicityInfo info) {
        this.ethnicity.update(info);

        changed(info);
    }

    public PersonEthnicGroup add(final PatientCommand.AddDetailedEthnicity add) {
        PersonEthnicGroup ethnicGroup = this.ethnicity.add(this, add);

        changed(add);

        return ethnicGroup;
    }

    public void remove(final PatientCommand.RemoveDetailedEthnicity remove) {
        this.ethnicity.remove(remove);

        changed(remove);
    }

    public void update(PatientCommand.UpdateRaceInfo info) {
        this.setRaceCd(info.raceCd());

        changed(info);
    }

    public boolean delete(PatientCommand.DeleteRaceInfo info) {

        List<PersonRace> arraylist = new ArrayList<>(this.races);
        boolean isDeleted = arraylist.removeIf(
            item -> (item.getPersonUid().getId() == info.person() && item.getRaceCd().equals(info.raceCd())));
        this.races = arraylist;
        changed(info);
        return isDeleted;
    }
}

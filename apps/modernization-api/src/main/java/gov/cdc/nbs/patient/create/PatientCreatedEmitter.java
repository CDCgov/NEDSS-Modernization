package gov.cdc.nbs.patient.create;

import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.patient.event.PatientEventEmitter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Component
class PatientCreatedEmitter {

    private final PatientEventEmitter emitter;

    PatientCreatedEmitter(final PatientEventEmitter emitter) {
        this.emitter = emitter;
    }

    void created(final Person person) {
        PatientEvent.Created created = asCreated(person);

        this.emitter.emit(created);
    }

    private PatientEvent.Created asCreated(final Person patient) {

        LocalDate birthday = resolveBirthday(patient);

        return new PatientEvent.Created(
            patient.getId(),
            patient.getLocalId(),
            birthday,
            resolveGender(patient.getBirthGenderCd()),
            resolveGender(patient.getCurrSexCd()),
            resolveDeceased(patient.getDeceasedIndCd()),
            patient.getDeceasedTime(),
            patient.getGeneralInformation().maritalStatus(),
            patient.getEthnicity().ethnicGroup(),
            patient.getAsOfDateAdmin(),
            patient.getDescription(),
            patient.getGeneralInformation().stateHIVCase(),
            resolveNames(patient.getNames()),
            resolveRaces(patient.getRaces()),
            resolveAddresses(patient.addresses()),
            resolvePhones(patient.phoneNumbers()),
            resolveEmails(patient.emailAddresses()),
            resolveIdentifications(patient.identifications()),
            patient.audit().added().addedBy(),
            patient.audit().added().addedOn()
        );
    }

    private LocalDate resolveBirthday(final Person patient) {
        LocalDateTime value = patient.getBirthTime();

        return value == null
            ? null
            : value.toLocalDate();
    }

    private String resolveGender(final Gender gender) {
        return gender == null ? null : gender.value();
    }

    private String resolveDeceased(final Deceased deceased) {
        return deceased == null ? null : deceased.value();
    }

    private List<PatientEvent.Created.Name> resolveNames(final Collection<PersonName> names) {
        return names.stream()
            .map(this::asName)
            .toList();
    }

    private PatientEvent.Created.Name asName(final PersonName name) {
        String suffix = name.getNmSuffix() == null ? null : name.getNmSuffix().name();

        return new PatientEvent.Created.Name(
            name.getNmUseCd(),
            name.getFirstNm(),
            name.getMiddleNm(),
            name.getLastNm(),
            suffix
        );
    }

    private List<String> resolveRaces(final Collection<PersonRace> races) {
        return races.stream().map(PersonRace::getRaceCategoryCd).toList();
    }

    private List<PatientEvent.Created.Address> resolveAddresses(
        final Collection<PostalEntityLocatorParticipation> addresses
    ) {
        return addresses.stream().map(this::asAddress).toList();
    }

    private PatientEvent.Created.Address asAddress(final PostalEntityLocatorParticipation address) {
        PostalLocator locator = address.getLocator();
        return new PatientEvent.Created.Address(
            address.getId().getLocatorUid(),
            address.getAsOfDate(),
            locator.getStreetAddr1(),
            locator.getStreetAddr2(),
            locator.getCityDescTxt(),
            locator.getStateCd(),
            locator.getZipCd(),
            locator.getCntyCd(),
            locator.getCntryCd(),
            locator.getCensusTract()
        );
    }

    private List<PatientEvent.Created.Phone> resolvePhones(final Collection<TeleEntityLocatorParticipation> phones) {
        return phones.stream().map(this::asPhone).toList();
    }

    private PatientEvent.Created.Phone asPhone(final TeleEntityLocatorParticipation phone) {
        TeleLocator locator = phone.getLocator();

        return new PatientEvent.Created.Phone(
            phone.getId().getLocatorUid(),
            phone.getAsOfDate(),
            phone.getCd(),
            phone.getUseCd(),
            locator.getPhoneNbrTxt(),
            locator.getExtensionTxt()
        );
    }

    private List<PatientEvent.Created.Email> resolveEmails(final Collection<TeleEntityLocatorParticipation> emails) {
        return emails.stream().map(this::asEmail).toList();
    }

    private PatientEvent.Created.Email asEmail(final TeleEntityLocatorParticipation email) {
        TeleLocator locator = email.getLocator();

        return new PatientEvent.Created.Email(
            email.getId().getLocatorUid(),
            email.getAsOfDate(),
            email.getCd(),
            email.getUseCd(),
            locator.getEmailAddress()
        );
    }

    private List<PatientEvent.Created.Identification> resolveIdentifications(final Collection<EntityId> identifications) {
        return identifications.stream().map(this::asIdentification).toList();
    }

    private PatientEvent.Created.Identification asIdentification(final EntityId identification) {
        return new PatientEvent.Created.Identification(
            identification.getId().getEntityIdSeq(),
            identification.getAsOfDate(),
            identification.getTypeCd(),
            identification.getAssigningAuthorityCd(),
            identification.getRootExtensionTxt()
        );
    }
}

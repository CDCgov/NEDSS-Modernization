package gov.cdc.nbs.patientlistener.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.elasticsearch.NestedAddress;
import gov.cdc.nbs.entity.elasticsearch.NestedEmail;
import gov.cdc.nbs.entity.elasticsearch.NestedName;
import gov.cdc.nbs.entity.elasticsearch.NestedPhone;
import gov.cdc.nbs.entity.elasticsearch.NestedRace;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.RequestStatus;
import gov.cdc.nbs.message.patient.event.PatientCreateData;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PatientCreateRequestHandler {
    @Value("${kafkadef.topics.status.patient}")
    private String statusTopic;

    @Autowired
    private UserService userService;

    @Autowired
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    @Autowired
    private KafkaTemplate<String, RequestStatus> producer;

    @Autowired
    PatientCreator creator;

    @Transactional
    public void handlePatientCreate(PatientCreateData data) {
        long user = data.createdBy();
        log.debug("Attempting to validate permissions for user: {}", user);
        if (userService.isAuthorized(user, "FIND-PATIENT", "ADD-PATIENT")) {
            creationAllowed(data.request(), data);
        } else {
            notAuthorized(data.request());
        }
    }

    private void creationAllowed(final String key, final PatientCreateData createRequest) {
        // perform the creation
        log.debug("User permission validated. Creating patient");
        Person newPatient = createPatient(createRequest);
        createElasticsearchPatient(newPatient);

        // post success message to status topic
        sendPatientCreateStatus(true, key, "Successfully created patient", newPatient.getId());
    }

    private void notAuthorized(final String key) {
        log.debug("User lacks permission for patient create");
        sendPatientCreateStatus(false, key, "User not authorized to perform this operation");
    }

    private void sendPatientCreateStatus(boolean successful, String key, String message) {
        sendPatientCreateStatus(successful, key, message, null);
    }

    private void sendPatientCreateStatus(boolean successful, String key, String message, Long entityId) {
        var status = RequestStatus.builder()
                .successful(successful)
                .requestId(key)
                .message(message)
                .entityId(entityId)
                .build();
        producer.send(statusTopic, status);
    }

    /**
     * Creates a Person entity from the PatientInput and persists it to the database
     */
    private Person createPatient(PatientCreateData request) {
        return creator.create(request);
    }

    /**
     * Converts a Person entity into an ElasticsearchPerson Document and persists it to ES
     */
    private void createElasticsearchPatient(Person person) {
        var esPerson = ElasticsearchPerson.builder()
                .id(person.getId().toString())
                .personUid(person.getId())
                .firstNm(person.getFirstNm())
                .lastNm(person.getLastNm())
                .middleNm(person.getMiddleNm())
                .addTime(person.getAddTime())
                .addUserId(person.getAddUserId())
                .birthTime(person.getBirthTime())
                .cd(person.getCd())
                .currSexCd(person.getCurrSexCd())
                .deceasedIndCd(person.getDeceasedIndCd())
                .deceasedTime(person.getDeceasedTime())
                .electronicInd(person.getElectronicInd())
                .ethnicGroupInd(person.getEthnicGroupInd())
                .lastChgTime(person.getLastChgTime())
                .maritalStatusCd(person.getMaritalStatusCd())
                .recordStatusCd(person.getRecordStatusCd())
                .statusCd(person.getStatusCd())
                .statusTime(person.getStatusTime())
                .localId(person.getLocalId())
                .versionCtrlNbr(person.getVersionCtrlNbr())
                .edxInd(person.getEdxInd())
                .dedupMatchInd(person.getDedupMatchInd())
                .address(getAddresses(person))
                .phone(getPhones(person))
                .email(getEmails(person))
                .name(getNames(person))
                .race(getRaces(person))
                .asOfDateGeneral(person.getAsOfDateGeneral())
                .asOfDateAdmin(person.getAsOfDateAdmin())
                .asOfDateSex(person.getAsOfDateSex())
                .description(person.getDescription())
                .build();
        elasticsearchPersonRepository.save(esPerson);
    }

    private List<NestedRace> getRaces(Person person) {
        if (person.getRaces() == null || person.getRaces().isEmpty()) {
            return new ArrayList<>();
        }
        return person.getRaces()
                .stream()
                .map(race -> NestedRace.builder()
                        .raceCd(race.getRaceCategoryCd())
                        .raceDescTxt(race.getRaceDescTxt())
                        .build())
                .toList();
    }

    private List<NestedName> getNames(Person person) {
        if (person.getNames() == null || person.getNames().isEmpty()) {
            return new ArrayList<>();
        }
        return person.getNames()
                .stream()
                .map(n -> NestedName.builder()
                        .firstNm(n.getFirstNm())
                        .firstNmSndx(n.getFirstNmSndx())
                        .middleNm(n.getMiddleNm())
                        .lastNm(n.getLastNm())
                        .lastNmSndx(n.getLastNmSndx())
                        .nmPrefix(n.getNmPrefix())
                        .nmSuffix(n.getNmSuffix() != null ? n.getNmSuffix().toString() : null)
                        .build())
                .toList();
    }

    private List<NestedEmail> getEmails(Person person) {
        if (person == null
                || person.getNbsEntity().getEntityLocatorParticipations() == null
                || person.getNbsEntity().getEntityLocatorParticipations().isEmpty()) {
            return new ArrayList<>();
        }
        return person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(TeleEntityLocatorParticipation.class::isInstance)
                .filter(elp -> Objects.equals(elp.getCd(), "NET"))
                .map(TeleEntityLocatorParticipation.class::cast)
                .map(this::asEmail)
                .toList();
    }

    private NestedEmail asEmail(final TeleEntityLocatorParticipation participation) {
        return NestedEmail.builder()
                .emailAddress(participation.getLocator().getEmailAddress())
                .build();
    }

    private List<NestedPhone> getPhones(Person person) {
        if (person == null
                || person.getNbsEntity().getEntityLocatorParticipations() == null
                || person.getNbsEntity().getEntityLocatorParticipations().isEmpty()) {
            return new ArrayList<>();
        }
        return person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(TeleEntityLocatorParticipation.class::isInstance)
                .filter(elp -> !Objects.equals(elp.getCd(), "NET"))
                .map(TeleEntityLocatorParticipation.class::cast)
                .map(this::asPhone)
                .toList();
    }

    private NestedPhone asPhone(final TeleEntityLocatorParticipation participation) {
        TeleLocator locator = participation.getLocator();
        return NestedPhone.builder()
                .telephoneNbr(locator.getPhoneNbrTxt())
                .extensionTxt(locator.getExtensionTxt())
                .build();
    }

    private List<NestedAddress> getAddresses(Person person) {
        if (person == null
                || person.getNbsEntity().getEntityLocatorParticipations() == null
                || person.getNbsEntity().getEntityLocatorParticipations().isEmpty()) {
            return new ArrayList<>();
        }
        return person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(PostalEntityLocatorParticipation.class::isInstance)
                .map(PostalEntityLocatorParticipation.class::cast)
                .map(this::asAddress)
                .toList();

    }

    private NestedAddress asAddress(final PostalEntityLocatorParticipation participation) {
        PostalLocator locator = participation.getLocator();

        return NestedAddress.builder()
                .streetAddr1(locator.getStreetAddr1())
                .streetAddr2(locator.getStreetAddr2())
                .city(locator.getCityCd())
                .state(locator.getStateCd())
                .zip(locator.getZipCd())
                .cntyCd(locator.getCntyCd())
                .cntryCd(locator.getCntryCd())
                .build();
    }

}

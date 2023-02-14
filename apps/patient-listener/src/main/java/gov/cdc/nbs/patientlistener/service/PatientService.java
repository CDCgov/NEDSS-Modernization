package gov.cdc.nbs.patientlistener.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.cdc.nbs.config.security.NbsAuthority;
import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.elasticsearch.NestedAddress;
import gov.cdc.nbs.entity.elasticsearch.NestedEmail;
import gov.cdc.nbs.entity.elasticsearch.NestedName;
import gov.cdc.nbs.entity.elasticsearch.NestedPhone;
import gov.cdc.nbs.entity.elasticsearch.NestedRace;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.NBSEntity;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonNameId;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.entity.odse.PersonRaceId;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.PatientCreateRequest;
import gov.cdc.nbs.message.PatientInput;
import gov.cdc.nbs.message.PatientInput.Name;
import gov.cdc.nbs.message.PatientInput.PhoneNumber;
import gov.cdc.nbs.message.PatientInput.PhoneType;
import gov.cdc.nbs.message.PatientInput.PostalAddress;
import gov.cdc.nbs.message.RequestStatus;
import gov.cdc.nbs.patientlistener.exception.PatientCreateException;
import gov.cdc.nbs.patientlistener.producer.KafkaProducer;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.PostalLocatorRepository;
import gov.cdc.nbs.repository.TeleLocatorRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PatientService {
    private static final String ACTIVE = "ACTIVE";

    @Value("${nbs.uid.seed}")
    private long seed;

    @Value("${nbs.uid.suffix}")
    private String suffix;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TeleLocatorRepository teleLocatorRepository;

    @Autowired
    private PostalLocatorRepository postalLocatorRepository;

    @Autowired
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    @Autowired
    private KafkaProducer producer;

    public void handlePatientCreate(String message, String key) {
        PatientCreateRequest createRequest;
        try {
            // convert message to Object
            createRequest = objectMapper.readValue(message, PatientCreateRequest.class);
        } catch (JsonProcessingException e) {
            log.warn("Failed to map message to PatientCreateRequest object. Message: '{}'", message);
            sendPatientCreateStatus(false, key, "Failed to parse message");
            return;
        }
        var userId = createRequest.getUserId();
        List<String> userPermissions;
        NbsUserDetails userDetails;
        try {
            log.debug("Attempting to validate permissions for user: {}", userId);
            // validate user has ADD-PATIENT and FIND-PATIENT permissions
            userDetails = userService.loadUserByUsername(userId);
            userPermissions = userDetails.getAuthorities().stream().map(NbsAuthority::getAuthority).toList();
        } catch (Exception e) {
            log.warn("Failed to find user credentials for userId: {}", userId);
            sendPatientCreateStatus(false, key, "Failed to find user in system");
            return;
        }
        if (userPermissions.contains("FIND-PATIENT") && userPermissions.contains("ADD-PATIENT")) {
            // user has permission. perform the creation
            log.debug("User permission validated. Creating patient");
            var newPatient = createPatient(createRequest.getPatientInput(), userDetails.getId());
            createElasticsearchPatient(newPatient);

            // post success message to status topic
            sendPatientCreateStatus(true, key, "Successfully created patient", newPatient.getId());
            return;
        } else {
            // user does not have permission
            log.debug("User lacks permission for patient create");
            sendPatientCreateStatus(false, key, "User not authorized to perform this operation");
            return;
        }

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
        producer.requestPatientCreateStatusEnvelope(status);
    }

    /**
     * Creates a Person entity from the PatientInput and persists it to the database
     */
    @Transactional
    private Person createPatient(PatientInput input, Long userId) {
        var now = Instant.now();
        final long id = personRepository.getMaxId() + 1;
        var person = new Person();
        // generated / required values
        person.setId(id);
        person.setLocalId(generateLocalId(id));
        person.setNbsEntity(new NBSEntity(id, "PSN"));
        person.setVersionCtrlNbr((short) 1);
        person.setAddTime(now);
        person.setRecordStatusCd(RecordStatus.ACTIVE);
        person.setCd("PAT");
        person.setElectronicInd('N');
        person.setEdxInd("Y");
        person.setDedupMatchInd('F');

        person.setSsn(input.getSsn());
        person.setBirthTime(input.getDateOfBirth());
        person.setBirthGenderCd(input.getBirthGender());
        person.setCurrSexCd(input.getCurrentGender());
        person.setDeceasedIndCd(input.getDeceased());
        person.setEthnicGroupInd(input.getEthnicityCode());

        // person_name
        if (input.getNames() != null) {
            for (int i = 0; i < input.getNames().size(); i++) {
                addPersonNameEntry(person, input.getNames().get(i), i + 1);
            }
        }

        // set 'primary' name on person record
        if (person.getNames() != null && !person.getNames().isEmpty()) {
            var name = person.getNames().get(0);
            person.setFirstNm(name.getFirstNm());
            person.setLastNm(name.getLastNm());
            person.setMiddleNm(name.getMiddleNm());
            person.setNmSuffix(name.getNmSuffix());
        }

        // person_race
        if (input.getRaceCodes() != null) {
            input.getRaceCodes().forEach(rc -> addPersonRaceEntry(person, rc, userId));
        }

        // tele_locator
        var teleLocators = addTeleLocatorEntries(
                person,
                input.getPhoneNumbers(),
                input.getEmailAddresses(),
                userId);

        // postal_locator
        var postalLocators = addPostalLocatorEntries(person, input.getAddresses(), userId);

        // Save
        teleLocatorRepository.saveAll(teleLocators);
        postalLocatorRepository.saveAll(postalLocators);
        return personRepository.save(person);
    }

    private String generateLocalId(Long id) {
        final Long nbsId = seed + id;
        return "PSN" + nbsId + suffix;
    }

    /**
     * Converts a Person entity into an ElasticsearchPerson Document and persists it
     * to ES
     */
    private void createElasticsearchPatient(Person person) {
        var esPerson = ElasticsearchPerson.builder()
                .id(person.getId().toString())
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
                .build();
        elasticsearchPersonRepository.save(esPerson);
    }

    private List<NestedRace> getRaces(Person person) {
        if (person.getRaces() == null || person.getRaces().isEmpty()) {
            return new ArrayList<>();
        }
        return person.getRaces()
                .stream()
                .map(race -> {
                    return NestedRace.builder()
                            .raceCd(race.getRaceCategoryCd())
                            .raceDescTxt(race.getRaceDescTxt())
                            .build();
                }).toList();
    }

    private List<NestedName> getNames(Person person) {
        if (person.getNames() == null || person.getNames().isEmpty()) {
            return new ArrayList<>();
        }
        return person.getNames()
                .stream()
                .map(n -> {
                    return NestedName.builder()
                            .firstNm(n.getFirstNm())
                            .firstNmSndx(n.getFirstNmSndx())
                            .middleNm(n.getMiddleNm())
                            .lastNm(n.getLastNm())
                            .lastNmSndx(n.getLastNmSndx())
                            .nmPrefix(n.getNmPrefix())
                            .nmSuffix(n.getNmSuffix() != null ? n.getNmSuffix().toString() : null)
                            .build();
                }).toList();
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
                .filter(elp -> elp.getClassCd().equals("TELE")
                        && ((TeleLocator) elp.getLocator()).getEmailAddress() != null
                        && !((TeleLocator) elp.getLocator()).getEmailAddress().isEmpty()) // email Tele locators only
                .map(elp -> {
                    var teleLocator = (TeleLocator) elp.getLocator();

                    return NestedEmail.builder()
                            .emailAddress(teleLocator.getEmailAddress())
                            .build();
                }).toList();
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
                .filter(elp -> elp.getClassCd().equals("TELE")
                        && ((TeleLocator) elp.getLocator()).getPhoneNbrTxt() != null
                        && !((TeleLocator) elp.getLocator()).getPhoneNbrTxt().isEmpty()) // phone Tele locators only
                .map(elp -> {
                    var teleLocator = (TeleLocator) elp.getLocator();

                    return NestedPhone.builder()
                            .telephoneNbr(teleLocator.getPhoneNbrTxt())
                            .extensionTxt(teleLocator.getExtensionTxt())
                            .build();
                }).toList();
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
                .filter(elp -> elp.getClassCd().equals("PST")) // postal locators only
                .map(elp -> {
                    var postalLocator = (PostalLocator) elp.getLocator();

                    return NestedAddress.builder()
                            .streetAddr1(postalLocator.getStreetAddr1())
                            .streetAddr2(postalLocator.getStreetAddr2())
                            .city(postalLocator.getCityDescTxt())
                            .state(postalLocator.getStateCd())
                            .zip(postalLocator.getZipCd())
                            .cntyCd(postalLocator.getCntyCd())
                            .cntryCd(postalLocator.getCntryCd())
                            .build();
                }).toList();

    }

    private void addPersonNameEntry(Person person, Name name, int sequence) {
        if (person == null || name == null) {
            return;
        }
        var now = Instant.now();
        var personName = new PersonName();
        personName.setId(new PersonNameId(person.getId(), (short) sequence));
        personName.setPersonUid(person);
        personName.setAddReasonCd("Add");
        personName.setAddTime(now);
        personName.setFirstNm(name.getFirstName());
        personName.setMiddleNm(name.getMiddleName());
        personName.setLastNm(name.getLastName());
        personName.setNmSuffix(name.getSuffix());
        personName.setNmUseCd(name.getNameUseCd());
        personName.setRecordStatusCd(ACTIVE);
        personName.setRecordStatusTime(now);
        personName.setStatusCd('A');
        personName.setStatusTime(now);
        personName.setAsOfDate(now);
        personName.setLastChgTime(now);

        if (person.getNames() == null) {
            person.setNames(new ArrayList<>());
        }
        person.getNames().add(personName);
    }

    /*
     * Creates a PersonRace entry and adds it to the Person object
     */
    private void addPersonRaceEntry(Person person, String race, Long userId) {
        if (person == null || race == null) {
            return;
        }
        var now = Instant.now();
        var personRace = new PersonRace();
        personRace.setId(new PersonRaceId(person.getId(), race));
        personRace.setPersonUid(person);
        personRace.setAddTime(now);
        personRace.setAddUserId(userId);
        personRace.setRaceCategoryCd(race);
        personRace.setRecordStatusCd(ACTIVE);
        personRace.setRecordStatusTime(now);
        personRace.setAsOfDate(now);
        personRace.setLastChgTime(now);
        personRace.setLastChgUserId(userId);

        if (person.getRaces() == null) {
            person.setRaces(new ArrayList<>());
        }
        person.getRaces().add(personRace);

    }

    /*
     * Creates an EntityLocatorParticipation and a TeleLocator object for each phone
     * number and email address. TeleLocators are added to the
     * EntityLocatorParticipation, which is then added to the Person NBSEntity
     */
    private List<TeleLocator> addTeleLocatorEntries(
            Person person,
            List<PhoneNumber> phoneNumbers,
            List<String> emailAddresses,
            Long userId) {
        var locatorList = new ArrayList<TeleLocator>();
        if (!phoneNumbers.isEmpty() || !emailAddresses.isEmpty()) {
            // Grab highest Id from DB -- eventually fix db to auto increment
            var teleLocatorId = teleLocatorRepository.getMaxId() + 1;
            var now = Instant.now();
            var elpList = new ArrayList<EntityLocatorParticipation>();
            for (PhoneNumber pn : phoneNumbers) {
                var teleId = teleLocatorId++;

                // entity locator participation ties person to locator entry
                var elp = new EntityLocatorParticipation();
                elp.setId(new EntityLocatorParticipationId(person.getId(), teleId));
                elp.setNbsEntity(person.getNbsEntity());
                elp.setClassCd("TELE");
                setElpTypeFields(elp, pn.getPhoneType());
                elp.setLastChgTime(now);
                elp.setLastChgUserId(userId);
                elp.setRecordStatusCd(ACTIVE);
                elp.setRecordStatusTime(now);
                elp.setStatusCd('A');
                elp.setStatusTime(now);
                elp.setVersionCtrlNbr((short) 1);

                var locator = new TeleLocator();
                locator.setId(teleId);
                locator.setAddTime(now);
                locator.setAddUserId(userId);
                locator.setExtensionTxt(pn.getExtension());
                locator.setPhoneNbrTxt(pn.getNumber());
                locator.setRecordStatusCd(ACTIVE);

                elp.setLocator(locator);
                locatorList.add(locator);
                elpList.add(elp);
            }

            for (String email : emailAddresses) {
                var teleId = teleLocatorId++;
                // entity locator participation ties person to locator entry
                var elp = new EntityLocatorParticipation();
                elp.setId(new EntityLocatorParticipationId(person.getId(), teleId));
                elp.setNbsEntity(person.getNbsEntity());
                elp.setClassCd("TELE");
                elp.setCd("NET");
                elp.setUseCd("H");
                elp.setLastChgTime(now);
                elp.setLastChgUserId(userId);
                elp.setRecordStatusCd(ACTIVE);
                elp.setRecordStatusTime(now);
                elp.setStatusCd('A');
                elp.setVersionCtrlNbr((short) 1);
                elp.setAsOfDate(now);
                elp.setAddTime(now);

                var locator = new TeleLocator();
                locator.setId(teleId);
                locator.setAddTime(now);
                locator.setAddUserId(userId);
                locator.setEmailAddress(email);
                locator.setRecordStatusCd(ACTIVE);
                locator.setLastChgTime(now);
                locator.setLastChgUserId(userId);

                elp.setLocator(locator);
                locatorList.add(locator);
                elpList.add(elp);
            }

            // Add generated ELPs to Person.NBSEntity
            var existingElp = person.getNbsEntity().getEntityLocatorParticipations();
            if (existingElp != null && !existingElp.isEmpty()) {
                elpList.addAll(existingElp);
            }
            person.getNbsEntity().setEntityLocatorParticipations(elpList);
        }
        return locatorList;
    }

    private void setElpTypeFields(EntityLocatorParticipation elp, PhoneType phoneType) {
        switch (phoneType) {
            case CELL:
                elp.setCd("CP");
                elp.setUseCd("MC");
                break;
            case HOME:
                elp.setCd("PH");
                elp.setUseCd("H");
                break;
            case WORK:
                elp.setCd("PH");
                elp.setUseCd("WP");
                break;
            default:
                throw new PatientCreateException("Invalid PhoneType specified: " + phoneType);
        }
    }

    /**
     * Creates an EntityLocatorParticipation and a PostalLocator object for each
     * address. PostalLocators are added to the EntityLocatorParticipation which is
     * then added to the Person.NBSEntity
     */
    private List<PostalLocator> addPostalLocatorEntries(Person person, List<PostalAddress> addresses, Long userId) {
        var postalLocators = new ArrayList<PostalLocator>();
        if (addresses != null && addresses.isEmpty()) {
            return postalLocators;
        }
        // Grab highest Id from DB -- eventually fix db to auto increment
        var postalLocatorId = postalLocatorRepository.getMaxId() + 1;
        var now = Instant.now();
        var elpList = new ArrayList<EntityLocatorParticipation>();
        for (PostalAddress address : addresses) {
            var plId = postalLocatorId++;
            // entity locator participation ties person to locator entry
            var elp = new EntityLocatorParticipation();
            elp.setId(new EntityLocatorParticipationId(person.getId(), plId));
            elp.setNbsEntity(person.getNbsEntity());
            elp.setCd("H");
            elp.setClassCd("PST");
            elp.setLastChgTime(now);
            elp.setLastChgUserId(userId);
            elp.setAddUserId(userId);
            elp.setAddTime(now);
            elp.setRecordStatusCd(ACTIVE);
            elp.setRecordStatusTime(now);
            elp.setStatusCd('A');
            elp.setStatusTime(now);
            elp.setUseCd("H");
            elp.setVersionCtrlNbr((short) 1);

            var locator = new PostalLocator();
            locator.setId(plId);
            locator.setAddTime(now);
            locator.setAddUserId(userId);
            locator.setLastChgTime(now);
            locator.setLastChgUserId(userId);
            locator.setCityDescTxt(address.getCity());
            locator.setCntryCd(address.getCountryCode());
            locator.setCntyCd(address.getCountyCode());
            locator.setStateCd(address.getStateCode());
            locator.setStreetAddr1(address.getStreetAddress1());
            locator.setStreetAddr2(address.getStreetAddress2());
            locator.setZipCd(address.getZip());
            locator.setCensusTract(address.getCensusTract());
            locator.setRecordStatusCd(ACTIVE);
            locator.setRecordStatusTime(now);

            elp.setLocator(locator);
            postalLocators.add(locator);
            elpList.add(elp);
        }
        // Add generated ELPs to Person.NBSEntity
        if (person.getNbsEntity().getEntityLocatorParticipations() == null) {
            person.getNbsEntity().setEntityLocatorParticipations(elpList);
        } else {
            person.getNbsEntity().getEntityLocatorParticipations().addAll(elpList);
        }

        return postalLocators;
    }

}

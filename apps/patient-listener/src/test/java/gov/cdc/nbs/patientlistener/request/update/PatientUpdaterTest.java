package gov.cdc.nbs.patientlistener.request.update;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.patient.event.UpdateAdministrativeData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

class PatientUpdaterTest {

    @Mock
    private PersonRepository personRepository;

    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @InjectMocks
    private PatientUpdater patientUpdater;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_update_sex_and_birth_info() {
        var data = getSexAndBirthData();
        var person = new Person(123L, "localId");
        person.setVersionCtrlNbr((short) 2);
        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        var savedPerson = personCaptor.getValue();

        var now = Instant.now();

        assertEquals(data.asOf().getEpochSecond(), savedPerson.getAsOfDateSex().getEpochSecond());
        assertEquals(data.birthGender(), savedPerson.getBirthGenderCd());
        assertEquals(data.currentGender(), savedPerson.getCurrSexCd());
        assertEquals(data.dateOfBirth(), LocalDate.ofInstant(savedPerson.getBirthTime(), ZoneId.systemDefault()));
        assertEquals(data.currentAge(), savedPerson.getAgeReported());
        assertEquals(data.ageReportedTime().getEpochSecond(), savedPerson.getAgeReportedTime().getEpochSecond());
        assertEquals(data.birthCity(), savedPerson.getBirthCityCd());
        assertEquals(data.birthCntry(), savedPerson.getBirthCntryCd());
        assertEquals(data.birthState(), savedPerson.getBirthStateCd());
        assertEquals(data.birthOrderNbr(), savedPerson.getBirthOrderNbr());
        assertEquals(data.multipleBirth(), savedPerson.getMultipleBirthInd());
        assertEquals(data.sexUnknown(), savedPerson.getSexUnkReasonCd());
        assertEquals(data.additionalGender(), savedPerson.getAdditionalGenderCd());
        assertEquals(data.transGenderInfo(), savedPerson.getPreferredGenderCd());

        assertEquals(Long.valueOf(data.updatedBy()), savedPerson.getLastChgUserId());
        assertEquals(Short.valueOf((short) 3), savedPerson.getVersionCtrlNbr());
        assertEquals(Long.valueOf(data.updatedBy()), savedPerson.getLastChgUserId());
        assertTrue(savedPerson.getLastChgTime().until(now, ChronoUnit.SECONDS) < 5);
    }

    private UpdateSexAndBirthData getSexAndBirthData() {
        return new UpdateSexAndBirthData(
            "RequestId",
            123L,
            321L,
            Instant.now(),
            LocalDate.now(),
            Gender.F,
            Gender.M,
            "additional gender info",
            "trans info",
            "birth city",
            "birth Cntry",
            "birth state",
            (short) 1,
            "multiple birth",
            "sex unknown",
            "current age",
            Instant.now());
    }

    @Test
    void should_update_administrative_info() {
        var data = getAdministrativeData();
        var person = new Person(123L, "localId");
        person.setVersionCtrlNbr((short) 2);
        patientUpdater.update(person, data);

        verify(personRepository).save(personCaptor.capture());
        var savedPerson = personCaptor.getValue();

        var now = Instant.now();

        assertEquals(data.description(), savedPerson.getDescription());

        assertEquals(Long.valueOf(data.updatedBy()), savedPerson.getLastChgUserId());
        assertEquals(Short.valueOf((short) 3), savedPerson.getVersionCtrlNbr());
        assertEquals(Long.valueOf(data.updatedBy()), savedPerson.getLastChgUserId());
        assertTrue(savedPerson.getLastChgTime().until(now, ChronoUnit.SECONDS) < 5);
    }

    private UpdateAdministrativeData getAdministrativeData() {
        return new UpdateAdministrativeData(
            123L,
            "RequestId",
            321L,
            Instant.now(),
            "Administrative Data 1");
    }


}

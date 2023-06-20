package gov.cdc.nbs.patientlistener.request.update;

import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.patient.event.UpdateAdministrativeData;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.patientlistener.request.PatientNotFoundException;
import gov.cdc.nbs.patientlistener.request.PatientRequestStatusProducer;
import gov.cdc.nbs.patientlistener.request.UserNotAuthorizedException;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientUpdateRequestHandlerTest {
    @Mock
    private PersonRepository personRepository;
    @Mock
    private UserService userService;
    @Mock
    private PatientUpdater patientUpdater;
    @Mock
    private PatientRequestStatusProducer statusProducer;
    @Mock
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    @InjectMocks
    private PatientUpdateRequestHandler updateHandler;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenInfoUpdateSuccess() {
        var data = getGeneralInfoData();

        // set valid mock returns
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Person(123L, "localId")));
        when(patientUpdater.update(Mockito.any(), eq(data))).thenAnswer(i -> i.getArguments()[0]);

        // call handle update gen info
        updateHandler.handlePatientGeneralInfoUpdate(data);

        // verify save requests called, success status sent
        verify(patientUpdater, times(1)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(1)).save(Mockito.any());
        verify(statusProducer, times(1)).successful(eq("RequestId"), Mockito.anyString(), eq(123L));
    }

    @Test
    void testGenInfoUpdateUnauthorized() {
        var data = getGeneralInfoData();
        // set unauthorized mock
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        UserNotAuthorizedException ex = null;

        // call handle update gen info
        try {
            updateHandler.handlePatientGeneralInfoUpdate(data);
        } catch (UserNotAuthorizedException e) {
            ex = e;
        }

        // verify exception thrown, save requests are not called
        assertNotNull(ex);
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());

    }

    @Test
    void testGenInfoUpdateNoPatient() {
        var data = getGeneralInfoData();
        // set authorized = true, patient = null
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        PatientNotFoundException ex = null;

        // call handle update gen info
        try {
            updateHandler.handlePatientGeneralInfoUpdate(data);
        } catch (PatientNotFoundException e) {
            ex = e;
        }

        // verify exception thrown, save requests are not called
        assertNotNull(ex);
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
    }

    private UpdateGeneralInfoData getGeneralInfoData() {
        return new UpdateGeneralInfoData(123L,
            "RequestId",
            321L,
            Instant.now(),
            "marital status",
            "mothers maiden name",
            (short) 1,
            (short) 2,
            "occupation code",
            "education level",
            "prim language",
            "speaks english",
            "eharsId");
    }

    @Test
    void testMortalityInfoUpdateSuccess() {
        var data = getMortalityData();

        // set valid mock returns
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Person(123L, "localId")));
        when(patientUpdater.update(Mockito.any(), eq(data))).thenAnswer(i -> i.getArguments()[0]);

        updateHandler.handlePatientMortalityUpdate(data);

        // verify save requests called, success status sent
        verify(patientUpdater, times(1)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(1)).save(Mockito.any());
        verify(statusProducer, times(1)).successful(eq("RequestId"), Mockito.anyString(), eq(123L));
    }

    @Test
    void testMortalityInfoUpdateUnauthorized() {
        var data = getMortalityData();

        // set unauthorized mock
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        UserNotAuthorizedException ex = null;

        try {
            updateHandler.handlePatientMortalityUpdate(data);
        } catch (UserNotAuthorizedException e) {
            ex = e;
        }

        // verify exception thrown, save requests are not called
        assertNotNull(ex);
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
    }

    @Test
    void testMortalityInfoUpdateNoPatient() {
        var data = getMortalityData();

        // set authorized = true, patient = null
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        PatientNotFoundException ex = null;

        try {
            updateHandler.handlePatientMortalityUpdate(data);
        } catch (PatientNotFoundException e) {
            ex = e;
        }

        // verify save requests are not called, failure status sent
        assertNotNull(ex);
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
    }

    private UpdateMortalityData getMortalityData() {
        return new UpdateMortalityData(123L,
            "RequestId",
            321L,
            Instant.now(),
            Deceased.UNK,
            Instant.now(),
            "CityOfDeath",
            "State of Death",
            "County of death",
            "Country of death");
    }

    @Test
    void testSexInfoUpdateSuccess() {
        var data = getSexAndBirthData();

        // set valid mock returns
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Person(123L, "localId")));
        when(patientUpdater.update(Mockito.any(), eq(data))).thenAnswer(i -> i.getArguments()[0]);

        updateHandler.handlePatientSexAndBirthUpdate(data);

        // verify save requests called, success status sent
        verify(patientUpdater, times(1)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(1)).save(Mockito.any());
        verify(statusProducer, times(1)).successful(eq("RequestId"), Mockito.anyString(), eq(123L));
    }

    @Test
    void testSexAndBirthUpdateUnauthorized() {
        var data = getSexAndBirthData();

        // set unauthorized mock
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        UserNotAuthorizedException ex = null;

        try {
            updateHandler.handlePatientSexAndBirthUpdate(data);
        } catch (UserNotAuthorizedException e) {
            ex = e;
        }

        // verify exception thrown, save requests are not called
        assertNotNull(ex);
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
    }

    @Test
    void testSexAndBirthUpdateNoPatient() {
        var data = getSexAndBirthData();

        // set authorized = true, patient = null
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        PatientNotFoundException ex = null;

        try {
            updateHandler.handlePatientSexAndBirthUpdate(data);
        } catch (PatientNotFoundException e) {
            ex = e;
        }

        // verify exception thrown, save requests are not called
        assertNotNull(ex);
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
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
    void testAdministrativeUpdateSuccess() {
        var data = getAdministrativeData();

        // set valid mock returns
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Person(123L, "localId")));
        when(patientUpdater.update(Mockito.any(), eq(data))).thenAnswer(i -> i.getArguments()[0]);

        // call handle update gen info
        updateHandler.handlePatientAdministrativeUpdate(data);

        // verify save requests called, success status sent
        verify(patientUpdater, times(1)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(1)).save(Mockito.any());
        verify(statusProducer, times(1)).successful(eq("RequestId"), Mockito.anyString(), eq(123L));
    }

    private UpdateAdministrativeData getAdministrativeData() {
        return new UpdateAdministrativeData(
            123L,
            "RequestId",
            321L,
            Instant.now(),
            "Administrative Data 1");
    }

    @Test
    void testAdministrativeInfoUpdateUnauthorized() {
        var data = getAdministrativeData();

        // set unauthorized mock
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        UserNotAuthorizedException ex = null;

        try {
            updateHandler.handlePatientAdministrativeUpdate(data);
        } catch (UserNotAuthorizedException e) {
            ex = e;
        }

        // verify exception thrown, save requests are not called
        assertNotNull(ex);
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
    }

}

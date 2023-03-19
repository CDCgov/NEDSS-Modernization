package gov.cdc.nbs.patientlistener.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.patientlistener.kafka.StatusProducer;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.service.UserService;

class PatientUpdateRequestHandlerTest {
    @Mock
    private PersonRepository personRepository;
    @Mock
    private UserService userService;
    @Mock
    private PatientUpdater patientUpdater;
    @Mock
    private StatusProducer statusProducer;
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
        verify(statusProducer, times(1)).send(eq(true), eq("RequestId"), Mockito.anyString(), eq(123L));
    }

    @Test
    void testGenInfoUpdateUnauthorized() {
        var data = getGeneralInfoData();
        // set unauthorized mock
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        // call handle update gen info
        updateHandler.handlePatientGeneralInfoUpdate(data);

        // verify save requests are not called, failure status sent
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
        verify(statusProducer, times(1)).send(eq(false), eq("RequestId"), Mockito.anyString());
    }

    @Test
    void testGenInfoUpdateNoPatient() {
        var data = getGeneralInfoData();
        // set authorized = true, patient = null
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // call handle update gen info
        updateHandler.handlePatientGeneralInfoUpdate(data);

        // verify save requests are not called, failure status sent
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
        verify(statusProducer, times(1)).send(eq(false), eq("RequestId"), Mockito.anyString());
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

        // call handle update mortality info
        updateHandler.handlePatientMortalityUpdate(data);

        // verify save requests called, success status sent
        verify(patientUpdater, times(1)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(1)).save(Mockito.any());
        verify(statusProducer, times(1)).send(eq(true), eq("RequestId"), Mockito.anyString(), eq(123L));
    }

    @Test
    void testMortalityInfoUpdateUnauthorized() {
        var data = getMortalityData();

        // set unauthorized mock
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        // call handle update mortality info
        updateHandler.handlePatientMortalityUpdate(data);

        // verify save requests are not called, failure status sent
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
        verify(statusProducer, times(1)).send(eq(false), eq("RequestId"), Mockito.anyString());
    }

    @Test
    void testMortalityInfoUpdateNoPatient() {
        var data = getMortalityData();

        // set authorized = true, patient = null
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // call handle update mortality info
        updateHandler.handlePatientMortalityUpdate(data);

        // verify save requests are not called, failure status sent
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
        verify(statusProducer, times(1)).send(eq(false), eq("RequestId"), Mockito.anyString());
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

        // call handle update mortality info
        updateHandler.handlePatientSexAndBirthUpdate(data);

        // verify save requests called, success status sent
        verify(patientUpdater, times(1)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(1)).save(Mockito.any());
        verify(statusProducer, times(1)).send(eq(true), eq("RequestId"), Mockito.anyString(), eq(123L));
    }

    @Test
    void testSexAndBirthUpdateUnauthorized() {
        var data = getSexAndBirthData();

        // set unauthorized mock
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        // call handle update mortality info
        updateHandler.handlePatientSexAndBirthUpdate(data);

        // verify save requests are not called, failure status sent
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
        verify(statusProducer, times(1)).send(eq(false), eq("RequestId"), Mockito.anyString());
    }

    @Test
    void testSexAndBirthUpdateNoPatient() {
        var data = getSexAndBirthData();

        // set authorized = true, patient = null
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // call handle update mortality info
        updateHandler.handlePatientSexAndBirthUpdate(data);

        // verify save requests are not called, failure status sent
        verify(patientUpdater, times(0)).update(Mockito.any(), eq(data));
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
        verify(statusProducer, times(1)).send(eq(false), eq("RequestId"), Mockito.anyString());
    }

    private UpdateSexAndBirthData getSexAndBirthData() {
        return new UpdateSexAndBirthData(
                "RequestId",
                123L,
                321L,
                Instant.now(),
                Instant.now(),
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
}

package gov.cdc.nbs.patientlistener.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patientlistener.exception.KafkaException;
import gov.cdc.nbs.patientlistener.exception.PatientNotFoundException;
import gov.cdc.nbs.patientlistener.exception.UserNotAuthorizedException;
import gov.cdc.nbs.patientlistener.kafka.StatusProducer;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.service.UserService;

class PatientDeleteRequestHandlerTest {
    @Mock
    private UserService userService;
    @Mock
    private PatientDeleter patientDeleter;
    @Mock
    private StatusProducer statusProducer;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    @InjectMocks
    private PatientDeleteRequestHandler deleteRequestHandler;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSuccess() {
        // set valid mock returns
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Person(123L, "localId")));
        when(personRepository.findCountOfActiveRevisions(123L)).thenReturn(1L);
        when(patientDeleter.delete(Mockito.any(), Mockito.anyLong())).thenAnswer(i->i.getArguments()[0]);

        // call handle delete
        deleteRequestHandler.handlePatientDelete("key", 123L, 321L);

        // verify success status, elasticsearch insert call
        verify(statusProducer).send(eq(true), eq("key"), Mockito.anyString(), eq(123L));
        verify(elasticsearchPersonRepository, times(1)).save(Mockito.any());
    }

    @Test
    void testTooManyActiveRevisions() {
        // set valid mock returns
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Person(123L, "localId")));
        when(personRepository.findCountOfActiveRevisions(123L)).thenReturn(2L);
        when(patientDeleter.delete(Mockito.any(), Mockito.anyLong())).thenAnswer(i->i.getArguments()[0]);

        KafkaException ex = null;

        // call handle delete
        try {
        deleteRequestHandler.handlePatientDelete("key", 123L, 321L);} catch (KafkaException e) {
            ex = e;
        }

        // verify exception thrown, No calls made to perform deletion
        assertNotNull(ex);
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
        verify(patientDeleter, times(0)).delete(Mockito.any(), Mockito.anyLong());
    }

    @Test
    void testUnauthorized() {
        // set authorize check to return false
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        UserNotAuthorizedException ex = null;
        
        // call handle delete
        try {
        deleteRequestHandler.handlePatientDelete("key", 123L, 321L);} 
        catch(UserNotAuthorizedException e) {
            ex= e;
        }

        // verify exception thrown. No calls made to perform deletion
        assertNotNull(ex);
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
        verify(patientDeleter, times(0)).delete(Mockito.any(), Mockito.anyLong());
    }

    @Test
    void testPatientNotFound() {
        // set authorized check to return true
        when(userService.isAuthorized(eq(321L), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        // set personRepository to return empty for Id
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        PatientNotFoundException ex = null;
        
        // call handle delete
        try {
        deleteRequestHandler.handlePatientDelete("key", 123L, 321L);} 
        catch(PatientNotFoundException e) {
            ex= e;
        }

        // verify exception thrown. No calls made to perform deletion
        assertNotNull(ex);
        verify(elasticsearchPersonRepository, times(0)).save(Mockito.any());
        verify(patientDeleter, times(0)).delete(Mockito.any(), Mockito.anyLong());
    }

}

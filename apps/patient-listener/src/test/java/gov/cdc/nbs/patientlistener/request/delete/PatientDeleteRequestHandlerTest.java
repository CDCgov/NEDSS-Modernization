package gov.cdc.nbs.patientlistener.request.delete;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.patientlistener.request.PatientNotFoundException;
import gov.cdc.nbs.patientlistener.request.PatientRequestException;
import gov.cdc.nbs.patientlistener.request.PatientRequestStatusProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientDeleteRequestHandlerTest {
    @Mock
    private PatientDeletionCheck check;

    @Mock
    private PatientDeleter patientDeleter;
    @Mock
    private PatientRequestStatusProducer statusProducer;
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PatientDeleteRequestHandler handler;

    @Test
    void should_delete_the_patient() {
        // Given the user can delete a patient
        //  And I have a patient
        when(entityManager.find(eq(Person.class), Mockito.anyLong())).thenReturn(new Person(123L, "localId"));
        //  And the user is not associated with any events
        when(patientDeleter.delete(Mockito.any(), Mockito.anyLong())).thenAnswer(i -> i.getArguments()[0]);

        //  When the patient is deleted
        PatientRequest.Delete request = new PatientRequest.Delete("key", 123L, 321L);
        handler.handle(request);

        // verify success status, elasticsearch insert call
        verify(statusProducer).successful(eq("key"), anyString(), eq(123L));
    }

    @Test
    void should_not_delete_patient_when_checks_fail() {
        // Given the user can delete a patient
        //  And I have a patient
        //  And the patient is associated with at least one event
        doThrow(new PatientRequestException("", "")).when(check).check(Mockito.any());

        //  When the patient is deleted
        PatientRequest.Delete request = new PatientRequest.Delete("key", 123L, 321L);

        assertThatThrownBy(() -> handler.handle(request))
            .isInstanceOf(PatientRequestException.class);

        // verify exception thrown, No calls made to perform deletion

        verify(patientDeleter, times(0)).delete(Mockito.any(), Mockito.anyLong());
    }

    @Test
    void should_not_delete_when_patient_is_not_found() {
        // Given the user can delete a patient
        //  And the patient is associated with at least one event
        // set personRepository to return empty for Id

        // call handle delete
        PatientRequest.Delete request = new PatientRequest.Delete("key", 123L, 321L);

        assertThatThrownBy(
            () -> handler.handle(request)
        )
            .isInstanceOf(PatientNotFoundException.class)
        ;

        // verify exception thrown. No calls made to perform deletion
        verify(patientDeleter, times(0)).delete(Mockito.any(), Mockito.anyLong());
    }

}

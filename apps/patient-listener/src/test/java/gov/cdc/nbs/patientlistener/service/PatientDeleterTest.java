package gov.cdc.nbs.patientlistener.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.repository.PersonRepository;

public class PatientDeleterTest {

    @Mock
    private PersonRepository personRepository;

    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @InjectMocks
    private PatientDeleter patientDeleter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_set_correct_fields() {
        Person person = new Person(123L, "local-id");
        patientDeleter.delete(person, 321L);

        verify(personRepository, times(1)).save(personCaptor.capture());
        var savedPerson = personCaptor.getValue();


        var now = Instant.now();
        assertEquals(RecordStatus.LOG_DEL, savedPerson.getRecordStatusCd());
        assertTrue(savedPerson.getRecordStatusTime().until(now, ChronoUnit.SECONDS) < 5);
        assertEquals(2, savedPerson.getVersionCtrlNbr().intValue());
        assertEquals(321, savedPerson.getLastChgUserId().intValue());
        assertTrue(savedPerson.getLastChgTime().until(now, ChronoUnit.SECONDS) < 5);
    }
}

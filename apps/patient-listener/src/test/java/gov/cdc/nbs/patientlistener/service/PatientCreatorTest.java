package gov.cdc.nbs.patientlistener.service;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.patient.event.PatientCreateEvent;
import gov.cdc.nbs.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class PatientCreatorTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PatientCreator creator;

    @Test
    void should_create_person_from_patient_create_request() {
        PatientCreateEvent request = new PatientCreateEvent(
                "request-id-value",
                117L,
                "patient-local-id-value",
                "ssn-value",
                Instant.parse("2000-09-03T15:17:39.00Z"),
                Gender.M,
                Gender.F,
                Deceased.N,
                null,
                "marital-status-value",
                null,
                "ethnicity-value",
                null,
                null,
                null,
                null,
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z"),
                "comments");

        doAnswer(i -> i.getArguments()[0])
                .when(repository)
                .save(any());

        Person actual = creator.create(request);

        assertThat(actual.getId()).isEqualTo(117L);
        assertThat(actual.getLocalId()).isEqualTo("patient-local-id-value");
        assertThat(actual.getSsn()).isEqualTo("ssn-value");
        assertThat(actual.getBirthTime()).isEqualTo("2000-09-03T15:17:39.00Z");
        assertThat(actual.getBirthGenderCd()).isEqualTo(Gender.M);
        assertThat(actual.getCurrSexCd()).isEqualTo(Gender.F);
        assertThat(actual.getDeceasedIndCd()).isEqualTo(Deceased.N);
        assertThat(actual.getMaritalStatusCd()).isEqualTo("marital-status-value");
        assertThat(actual.getEthnicGroupInd()).isEqualTo("ethnicity-value");
        assertThat(actual.getAsOfDateGeneral()).isEqualTo("2020-03-03T10:15:30.00Z");
        assertThat(actual.getAsOfDateAdmin()).isEqualTo("2020-03-03T10:15:30.00Z");
        assertThat(actual.getAsOfDateSex()).isEqualTo("2020-03-03T10:15:30.00Z");
        assertThat(actual.getDescription()).isEqualTo("comments");
    }
}

package gov.cdc.nbs.patientlistener.request.create;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.patient.event.PatientCreateData;
import gov.cdc.nbs.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
        PatientCreateData request = new PatientCreateData(
                "request-id-value",
                117L,
                "patient-local-id-value",
                "ssn-value",
                LocalDate.parse("2000-09-03"),
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
        assertThat(LocalDate.ofInstant(actual.getBirthTime(), ZoneId.systemDefault())).isEqualTo("2000-09-03");
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

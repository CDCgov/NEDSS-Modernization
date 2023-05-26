package gov.cdc.nbs.patientlistener.request.delete;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PatientDeleterTest {

    @Test
    void should_set_correct_fields() {

        Clock clock = Clock.fixed(Instant.parse("2023-04-04T02:03:04Z"), ZoneId.of("UTC"));
        ElasticsearchPersonRepository searchRepository = mock(ElasticsearchPersonRepository.class);

        PatientDeleter deleter = new PatientDeleter(clock, searchRepository);

        Person person = new Person(123L, "local-id");
        deleter.delete(person, 321L);

        assertThat(person.getRecordStatusCd()).isEqualTo(RecordStatus.LOG_DEL);
        assertThat(person.getRecordStatusTime()).isEqualTo("2023-04-04T02:03:04Z");

        assertThat(person.getVersionCtrlNbr()).isEqualTo((short) 2);
        assertThat(person.getLastChgUserId()).isEqualTo(321L);
        assertThat(person.getLastChgTime()).isEqualTo("2023-04-04T02:03:04Z");

    }

    @Test
    void should_update_search_index() {
        Clock clock = Clock.fixed(Instant.parse("2023-04-04T02:03:04Z"), ZoneId.of("UTC"));

        ElasticsearchPersonRepository searchRepository = mock(ElasticsearchPersonRepository.class);

        PatientDeleter deleter = new PatientDeleter(clock, searchRepository);

        Person person = new Person(123L, "local-id");
        deleter.delete(person, 321L);

        ArgumentCaptor<ElasticsearchPerson> captor = ArgumentCaptor.forClass(ElasticsearchPerson.class);

        verify(searchRepository, times(1)).save(captor.capture());

        ElasticsearchPerson actual = captor.getValue();

        assertThat(actual).returns(123L, ElasticsearchPerson::getPersonUid);
    }
}

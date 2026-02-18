package gov.cdc.nbs.questionbank.valueset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.valueset.exception.CreateValuesetException;
import gov.cdc.nbs.questionbank.valueset.model.Valueset;
import gov.cdc.nbs.questionbank.valueset.request.CreateValuesetRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
class ValuesetCreatorTest {

  @Mock private EntityManager entityManager;

  @Mock private JdbcTemplate template;

  @InjectMocks private ValueSetCreator creator;

  @Test
  void should_create_valueset() {
    // Given a valid request
    CreateValuesetRequest request = validRequest();

    // And the code is not already in use
    when(template.queryForObject(Mockito.anyString(), Mockito.eq(Long.class), Mockito.eq("CODE")))
        .thenReturn(0L);

    // And the name is not already in use
    when(template.queryForObject(Mockito.anyString(), Mockito.eq(Long.class), Mockito.eq("name")))
        .thenReturn(0L);

    // When a create valueset request is processed
    Valueset valueset = creator.create(request, 1l);

    // Then the valueset is created
    assertThat(valueset).isNotNull();
    assertThat(valueset.code()).isEqualTo(request.code());
    assertThat(valueset.name()).isEqualTo(request.name());
    assertThat(valueset.description()).isEqualTo(request.description());
    assertThat(valueset.type()).isEqualTo(request.type());
  }

  @Test
  void should_fail_code_pattern() {
    // Given a request with invalid code
    CreateValuesetRequest request =
        new CreateValuesetRequest("PHIN", "bad code", "name", "description");

    // When a create valueset request is processed
    // Then an exception is thrown
    assertThrows(CreateValuesetException.class, () -> creator.create(request, 1l));
  }

  @Test
  void should_fail_name_pattern() {
    // Given a request with invalid code
    CreateValuesetRequest request =
        new CreateValuesetRequest("PHIN", "CODE", "bad!name", "description");

    // When a create valueset request is processed
    // Then an exception is thrown
    assertThrows(CreateValuesetException.class, () -> creator.create(request, 1l));
  }

  @Test
  void should_fail_bad_type() {
    // Given a request with bad type
    CreateValuesetRequest request = new CreateValuesetRequest("BAD", "CODE", "name", "description");

    // When a create valueset request is processed
    // Then an exception is thrown
    assertThrows(CreateValuesetException.class, () -> creator.create(request, 1l));
  }

  @Test
  void should_fail_code_in_use() {
    // Given a valid request
    CreateValuesetRequest request = validRequest();

    // And the code is in use
    when(template.queryForObject(Mockito.anyString(), Mockito.eq(Long.class), Mockito.eq("CODE")))
        .thenReturn(1L);

    // And the name is not already in use
    when(template.queryForObject(Mockito.anyString(), Mockito.eq(Long.class), Mockito.eq("name")))
        .thenReturn(0L);

    // When a create valueset request is processed
    // Then an exception is thrown
    assertThrows(CreateValuesetException.class, () -> creator.create(request, 1l));
  }

  @Test
  void should_fail_name_in_use() {
    // Given a valid request
    CreateValuesetRequest request = validRequest();

    // And the name is not already in use
    when(template.queryForObject(Mockito.anyString(), Mockito.eq(Long.class), Mockito.eq("name")))
        .thenReturn(1L);

    // When a create valueset request is processed
    // Then an exception is thrown
    assertThrows(CreateValuesetException.class, () -> creator.create(request, 1l));
  }

  @Test
  void should_fail_db_exception() {
    // Given a valid request
    CreateValuesetRequest request = validRequest();

    // And the code is not already in use
    when(template.queryForObject(Mockito.anyString(), Mockito.eq(Long.class), Mockito.eq("CODE")))
        .thenReturn(0L);

    // And the name is not already in use
    when(template.queryForObject(Mockito.anyString(), Mockito.eq(Long.class), Mockito.eq("name")))
        .thenReturn(0L);

    // And a failing database persistance
    doThrow(new RuntimeException()).when(entityManager).persist(Mockito.any());

    // When a create valueset request is processed
    // Then an exception is thrown
    assertThrows(CreateValuesetException.class, () -> creator.create(request, 1l));
  }

  private CreateValuesetRequest validRequest() {
    return new CreateValuesetRequest("PHIN", "CODE", "name", "description");
  }
}

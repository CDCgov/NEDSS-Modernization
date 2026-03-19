package gov.cdc.nbs.questionbank.valueset;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import gov.cdc.nbs.questionbank.valueset.command.ValueSetCommand;
import gov.cdc.nbs.questionbank.valueset.exception.ValuesetUpdateException;
import gov.cdc.nbs.questionbank.valueset.model.Valueset;
import gov.cdc.nbs.questionbank.valueset.request.UpdateValueSetRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValueSetUpdaterTest {

  @Mock private EntityManager entityManager;

  @Mock private ValueSetFinder finder;

  @InjectMocks private ValueSetUpdater updater;

  @Test
  void should_update() {
    // given a valid update request
    final UpdateValueSetRequest request = new UpdateValueSetRequest("new_name", "new description");

    // and a valid value set
    Codeset codeset = Mockito.mock(Codeset.class);
    when(entityManager.find(Codeset.class, new CodesetId("code_value_general", "valueset")))
        .thenReturn(codeset);

    // and a working finder
    when(finder.find("valueset")).thenReturn(new Valueset(null, null, null, null, null));

    // when the update command is applied
    updater.update("valueset", request);

    // then the proper fields are updated
    verify(codeset).update(new ValueSetCommand.Update("new_name", "new description"));
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"new name", "new_name!"})
  void should_fail_bad_name(String name) {
    // given an invalid update request
    final UpdateValueSetRequest request = new UpdateValueSetRequest(name, "new description");

    // when the update command is applied
    // then an exception is thrown
    assertThrows(ValuesetUpdateException.class, () -> updater.update("valueset", request));
  }

  @Test
  void should_fail_bad_valueset() {
    // given a valid update request
    final UpdateValueSetRequest request = new UpdateValueSetRequest("new_name", "new description");

    // and an invalid value set
    when(entityManager.find(Codeset.class, new CodesetId("code_value_general", "valueset")))
        .thenReturn(null);

    // when the update command is applied
    // then an exception is thrown
    assertThrows(ValuesetUpdateException.class, () -> updater.update("valueset", request));
  }
}

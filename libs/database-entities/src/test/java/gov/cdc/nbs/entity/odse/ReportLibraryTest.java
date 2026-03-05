package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class ReportLibraryTest {
  @Test
  void should_throw_exception_with_null_values() {
    Throwable exception =
        assertThrows(
            NullPointerException.class,
            () -> new ReportLibrary(null, null, null, null, null, null, null, null));

    assertEquals("libraryName is marked non-null but is null", exception.getMessage());
  }

  @Test
  void should_create_complete_report_library() {
    String libName = "MOCK_CA01_DIAGNOSIS.SAS";
    String descTxt =
        "CA01: Chalk Talk Report: Case. This report includes information on the patients in the same Lot (Epi-linked group) for a specific disease.";
    String runner = "sas";
    Character builtIn = 'Y';
    LocalDateTime addTime = LocalDateTime.parse("2020-03-03T10:15:30");
    Long userId = 9L;
    LocalDateTime lastChgTime = LocalDateTime.parse("2020-02-28T09:15:30");

    ReportLibrary actual =
        new ReportLibrary(libName, descTxt, runner, builtIn, addTime, userId, lastChgTime, userId);

    assertThat(actual)
        .satisfies(lib -> assertEquals(libName, lib.getLibraryName()))
        .satisfies(lib -> assertEquals(descTxt, lib.getDescTxt()))
        .satisfies(lib -> assertEquals(runner, lib.getRunner()))
        .satisfies(lib -> assertEquals(builtIn, lib.getIsBuiltinIndex()))
        .satisfies(lib -> assertEquals(addTime, lib.getAddTime()))
        .satisfies(lib -> assertEquals(userId, lib.getAddUserId()))
        .satisfies(lib -> assertEquals(userId, lib.getLastChgUserId()))
        .satisfies(lib -> assertEquals(lastChgTime, lib.getLastChgTime()));
  }
}

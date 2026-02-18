package gov.cdc.nbs.questionbank.entity;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.questionbank.valueset.command.ValueSetCommand;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class CodesetTest {

  @Test
  void should_create() {
    Instant now = Instant.now();
    var command =
        new ValueSetCommand.Add("PHIN", "valueset_name", "code", "description", 1, now, 2l);

    Codeset codeset = new Codeset(command);
    assertThat(codeset.getId().getClassCd()).isEqualTo("code_value_general");
    assertThat(codeset.getId().getCodeSetNm()).isEqualTo(command.code().toUpperCase());

    assertThat(codeset.getValueSetCode()).isEqualTo(command.code().toUpperCase());
    assertThat(codeset.getValueSetTypeCd()).isEqualTo(command.type());
    assertThat(codeset.getValueSetNm()).isEqualTo(command.name());
    assertThat(codeset.getCodeSetDescTxt()).isEqualTo(command.description());
    assertThat(codeset.getLdfPicklistIndCd()).isEqualTo('Y');
    assertThat(codeset.getAssigningAuthorityCd()).isEqualTo("L");
    assertThat(codeset.getAssigningAuthorityDescTxt()).isEqualTo("Local");
    assertThat(codeset.getIsModifiableInd()).isEqualTo('Y');
    assertThat(codeset.getStatusCd()).isEqualTo("A");
    assertThat(codeset.getStatusToTime()).isEqualTo(now);
    assertThat(codeset.getAddTime()).isEqualTo(now);
    assertThat(codeset.getAddUserId()).isEqualTo(2l);
    assertThat(codeset.getEffectiveFromTime()).isEqualTo(now);
    assertThat(codeset.getEffectiveToTime()).isEqualTo(now);

    assertThat(codeset.getCodeSetGroup().getId()).isEqualTo(1l);
    assertThat(codeset.getCodeSetGroup().getCodeSetShortDescTxt()).isEqualTo(command.name());
    assertThat(codeset.getCodeSetGroup().getCodeSetDescTxt()).isEqualTo(command.description());
    assertThat(codeset.getCodeSetGroup().getCodeSetNm()).isEqualTo(command.code().toUpperCase());
    assertThat(codeset.getCodeSetGroup().getVadsValueSetCode())
        .isEqualTo(command.code().toUpperCase());
    assertThat(codeset.getCodeSetGroup().getLdfPicklistIndCd()).isEqualTo('Y');
    assertThat(codeset.getCodeSetGroup().getPhinStdValInd()).isEqualTo('N');
  }

  @Test
  void should_update() {
    Instant now = Instant.now();
    var command =
        new ValueSetCommand.Add("PHIN", "valueset_name", "code", "description", 1, now, 2l);

    Codeset codeset = new Codeset(command);
    var update = new ValueSetCommand.Update("new_name", "new description");
    codeset.update(update);

    // verify correct fields are updated
    assertThat(codeset.getValueSetNm()).isEqualTo(update.name());
    assertThat(codeset.getCodeSetDescTxt()).isEqualTo(update.description());
    assertThat(codeset.getCodeSetGroup().getCodeSetShortDescTxt()).isEqualTo(update.name());
    assertThat(codeset.getCodeSetGroup().getCodeSetDescTxt()).isEqualTo(update.description());
  }
}

package gov.cdc.nbs.questionbank.entity;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class WaRdbMetadataTest {

  @Test
  void should_create_from_ui_metadata() {
    // Given a WaUiMetadata entry
    WaTemplate page = new WaTemplate();
    page.setId(1l);
    WaUiMetadata metadata = new WaUiMetadata();
    metadata.setId(2l);
    metadata.setWaTemplateUid(page);
    metadata.setQuestionIdentifier("questionIdentifier");

    // And a valid page contenet command
    Instant now = Instant.now();
    PageContentCommand.UpdateDateQuestion command =
        new PageContentCommand.UpdateDateQuestion(
            36l,
            "new label",
            "new tooltip",
            false,
            false,
            false,
            1007,
            "DATE",
            false,
            new ReportingInfo("report Label", "DFT_RDB_TABLE", "RDB_COL", "DMART_COLUMN"),
            true,
            "messageVariable",
            "messageLabel",
            "codeSystemOid",
            "codeSystemName",
            true,
            "hl7DataType",
            "admin comments",
            1l,
            now);

    // when a new WaRdbyMetadata is created
    WaRdbMetadata rdbMetadata = new WaRdbMetadata(metadata, command);

    // Then the fields are set appropriately
    assertThat(rdbMetadata.getRdbColumnNm()).isEqualTo(command.datamartInfo().rdbColumnName());
    assertThat(rdbMetadata.getRdbTableNm()).isEqualTo(command.datamartInfo().defaultRdbTableName());
    assertThat(rdbMetadata.getRptAdminColumnNm()).isEqualTo(command.datamartInfo().reportLabel());
    assertThat(rdbMetadata.getUserDefinedColumnNm())
        .isEqualTo(command.datamartInfo().dataMartColumnName());
    assertThat(rdbMetadata.getWaTemplateUid()).isEqualTo(page);
    assertThat(rdbMetadata.getQuestionIdentifier()).isEqualTo(metadata.getQuestionIdentifier());
    assertThat(rdbMetadata.getWaUiMetadataUid()).isEqualTo(metadata);
    assertThat(rdbMetadata.getAddTime()).isEqualTo(now);
    assertThat(rdbMetadata.getAddUserId()).isEqualTo(1l);
    assertThat(rdbMetadata.getLastChgTime()).isEqualTo(now);
    assertThat(rdbMetadata.getLastChgUserId()).isEqualTo(1l);
    assertThat(rdbMetadata.getRecordStatusCd()).isEqualTo("Active");
    assertThat(rdbMetadata.getRecordStatusTime()).isEqualTo(now);
  }
}

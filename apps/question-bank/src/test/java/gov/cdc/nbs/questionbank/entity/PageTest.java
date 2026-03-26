package gov.cdc.nbs.questionbank.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import gov.cdc.nbs.questionbank.page.PageCommand;
import gov.cdc.nbs.questionbank.page.template.TemplateCreationException;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class PageTest {

  @Test
  void should_change_page_name_when_unique() {
    WaTemplate page =
        new WaTemplate(
            "INV",
            "mapping-guide-value",
            "testing page",
            99999L,
            Instant.parse("2000-07-17T02:22:56Z"));

    page.changeName(
        name -> true,
        new PageCommand.ChangeName("updated-name", 1409L, Instant.parse("2010-11-17T17:05:19Z")));

    assertThat(page.getTemplateNm()).isEqualTo("updated-name");

    assertThat(page.getAddUserId()).isEqualTo(99999L);
    assertThat(page.getAddTime()).isEqualTo("2000-07-17T02:22:56Z");

    assertThat(page.getLastChgUserId()).isEqualTo(1409L);
    assertThat(page.getLastChgTime()).isEqualTo("2010-11-17T17:05:19Z");
  }

  @Test
  void should_change_page_name_when_not_unique() {
    WaTemplate page =
        new WaTemplate(
            "INV",
            "mapping-guide-value",
            "testing page",
            99999L,
            Instant.parse("2000-07-17T02:22:56Z"));

    assertThatThrownBy(
            () ->
                page.changeName(
                    name -> false,
                    new PageCommand.ChangeName(
                        "updated-name", 1409L, Instant.parse("2010-11-17T17:05:19Z"))))
        .hasMessageContaining("Another Page is named updated-name");
  }

  @Test
  void should_not_change_page_name_when_published() {
    WaTemplate page =
        new WaTemplate(
                "INV",
                "mapping-guide-value",
                "testing page",
                99999L,
                Instant.parse("2000-07-17T02:22:56Z"))
            .publish(new PageCommand.Publish(99999L, Instant.parse("2000-07-17T02:22:56Z")));

    assertThatThrownBy(
            () ->
                page.changeName(
                    name -> true,
                    new PageCommand.ChangeName(
                        "updated-name", 1409L, Instant.parse("2010-11-17T17:05:19Z"))))
        .hasMessageContaining("Changes can only be made to a Draft page");
  }

  @Test
  void should_change_page_datamart_when_unique() {
    WaTemplate page =
        new WaTemplate(
            "INV",
            "mapping-guide-value",
            "testing page",
            99999L,
            Instant.parse("2000-07-17T02:22:56Z"));

    page.changeDatamart(
        name -> true,
        new PageCommand.ChangeDatamart(
            "updated-datamart", 1409L, Instant.parse("2010-11-17T17:05:19Z")));

    assertThat(page.getDatamartNm()).isEqualTo("updated-datamart");

    assertThat(page.getAddUserId()).isEqualTo(99999L);
    assertThat(page.getAddTime()).isEqualTo("2000-07-17T02:22:56Z");

    assertThat(page.getLastChgUserId()).isEqualTo(1409L);
    assertThat(page.getLastChgTime()).isEqualTo("2010-11-17T17:05:19Z");
  }

  @Test
  void should_change_page_datamart_when_not_unique() {
    WaTemplate page =
        new WaTemplate(
            "INV",
            "mapping-guide-value",
            "testing page",
            99999L,
            Instant.parse("2000-07-17T02:22:56Z"));

    assertThatThrownBy(
            () ->
                page.changeDatamart(
                    name -> false,
                    new PageCommand.ChangeDatamart(
                        "updated-datamart", 1409L, Instant.parse("2010-11-17T17:05:19Z"))))
        .hasMessageContaining("Page is using the datamart named updated-datamart");
  }

  @Test
  void should_not_change_page_datamart_when_published() {
    WaTemplate page =
        new WaTemplate(
                "INV",
                "mapping-guide-value",
                "testing page",
                99999L,
                Instant.parse("2000-07-17T02:22:56Z"))
            .publish(new PageCommand.Publish(99999L, Instant.parse("2000-07-17T02:22:56Z")));

    assertThatThrownBy(
            () ->
                page.changeDatamart(
                    name -> true,
                    new PageCommand.ChangeDatamart(
                        "updated-datamart", 1409L, Instant.parse("2010-11-17T17:05:19Z"))))
        .hasMessageContaining("Changes can only be made to a Draft page");
  }

  @Test
  void should_update_page_information() {
    WaTemplate page =
        new WaTemplate(
            "INV",
            "mapping-guide-value",
            "testing page",
            99999L,
            Instant.parse("2000-07-17T02:22:56Z"));

    page.update(
        new PageCommand.UpdateInformation(
            "mapping-guide-updated",
            "updated-description",
            1409L,
            Instant.parse("2010-11-17T17:05:19Z")));

    assertThat(page.getNndEntityIdentifier()).isEqualTo("mapping-guide-updated");
    assertThat(page.getDescTxt()).isEqualTo("updated-description");

    assertThat(page.getAddUserId()).isEqualTo(99999L);
    assertThat(page.getAddTime()).isEqualTo("2000-07-17T02:22:56Z");

    assertThat(page.getLastChgUserId()).isEqualTo(1409L);
    assertThat(page.getLastChgTime()).isEqualTo("2010-11-17T17:05:19Z");
  }

  @Test
  void should_not_update_page_information_when_published() {
    WaTemplate page =
        new WaTemplate(
                "INV",
                "mapping-guide-value",
                "testing page",
                99999L,
                Instant.parse("2000-07-17T02:22:56Z"))
            .publish(new PageCommand.Publish(99999L, Instant.parse("2000-07-17T02:22:56Z")));

    assertThatThrownBy(
            () ->
                page.update(
                    new PageCommand.UpdateInformation(
                        "mapping-guide-updated",
                        "updated-description",
                        1409L,
                        Instant.parse("2010-11-17T17:05:19Z"))))
        .hasMessageContaining("Changes can only be made to a Draft page");
  }

  @Test
  void should_relate_condition() {
    WaTemplate page =
        new WaTemplate(
            "INV",
            "mapping-guide-value",
            "testing page",
            99999L,
            Instant.parse("2000-07-17T02:22:56Z"));

    page.relate(
        new PageCommand.RelateCondition(
            "new-condition", 1409L, Instant.parse("2010-11-17T17:05:19Z")));

    assertThat(page.getConditionMappings())
        .satisfiesExactly(
            actual ->
                assertAll(
                    () -> assertThat(actual.getConditionCd()).isEqualTo("new-condition"),
                    () -> assertThat(actual.getAddUserId()).isEqualTo(1409L),
                    () -> assertThat(actual.getAddTime()).isEqualTo("2010-11-17T17:05:19Z"),
                    () -> assertThat(actual.getLastChgUserId()).isEqualTo(1409L),
                    () -> assertThat(actual.getLastChgTime()).isEqualTo("2010-11-17T17:05:19Z")));

    assertThat(page.getAddUserId()).isEqualTo(99999L);
    assertThat(page.getAddTime()).isEqualTo("2000-07-17T02:22:56Z");

    assertThat(page.getLastChgUserId()).isEqualTo(1409L);
    assertThat(page.getLastChgTime()).isEqualTo("2010-11-17T17:05:19Z");
  }

  @Test
  void should_not_relate_condition_when_published() {
    WaTemplate page =
        new WaTemplate(
                "INV",
                "mapping-guide-value",
                "testing page",
                99999L,
                Instant.parse("2000-07-17T02:22:56Z"))
            .publish(new PageCommand.Publish(99999L, Instant.parse("2000-07-17T02:22:56Z")));

    assertThatThrownBy(
            () ->
                page.relate(
                    new PageCommand.RelateCondition(
                        "new-condition", 1409L, Instant.parse("2010-11-17T17:05:19Z"))))
        .hasMessageContaining("Changes can only be made to a Draft page");
  }

  @Test
  void should_dissociate_condition() {
    WaTemplate page =
        new WaTemplate(
            "INV",
            "mapping-guide-value",
            "testing page",
            99999L,
            Instant.parse("2000-07-17T02:22:56Z"));

    page.relate(
            new PageCommand.RelateCondition(
                "condition-one", 1123L, Instant.parse("2014-08-07T00:05:00Z")))
        .relate(
            new PageCommand.RelateCondition(
                "condition-other", 1087L, Instant.parse("2015-10-02T00:00:00Z")))
        .dissociate(
            new PageCommand.DissociateCondition(
                "condition-other", 1087L, Instant.parse("2015-10-02T00:00:00Z")));

    assertThat(page.getConditionMappings())
        .satisfiesExactly(actual -> assertThat(actual.getConditionCd()).isEqualTo("condition-one"));

    assertThat(page.getAddUserId()).isEqualTo(99999L);
    assertThat(page.getAddTime()).isEqualTo("2000-07-17T02:22:56Z");

    assertThat(page.getLastChgUserId()).isEqualTo(1087L);
    assertThat(page.getLastChgTime()).isEqualTo("2015-10-02T00:00:00Z");
  }

  @Test
  void should_not_dissociate_condition_when_published() {
    WaTemplate page =
        new WaTemplate(
                "INV",
                "mapping-guide-value",
                "testing page",
                99999L,
                Instant.parse("2000-07-17T02:22:56Z"))
            .relate(
                new PageCommand.RelateCondition(
                    "condition-one", 1123L, Instant.parse("2014-08-07T00:05:00Z")))
            .publish(new PageCommand.Publish(99999L, Instant.parse("2000-07-17T02:22:56Z")));

    assertThatThrownBy(
            () ->
                page.dissociate(
                    new PageCommand.DissociateCondition(
                        "condition-other", 1087L, Instant.parse("2015-10-02T00:00:00Z"))))
        .hasMessageContaining("Changes can only be made to a Draft page");
  }

  @Test
  void should_not_dissociate_condition_if_ever_published() {
    WaTemplate page =
        new WaTemplate(
                "INV",
                "mapping-guide-value",
                "testing page",
                99999L,
                Instant.parse("2000-07-17T02:22:56Z"))
            .publish(new PageCommand.Publish(99999L, Instant.parse("2000-07-17T02:22:56Z")));

    //  this should be replaced by the command when it is created.
    page.setTemplateType("Draft");
    page.setPublishIndCd('F');

    assertThatThrownBy(
            () ->
                page.dissociate(
                    new PageCommand.DissociateCondition(
                        "new-condition", 1409L, Instant.parse("2010-11-17T17:05:19Z"))))
        .hasMessageContaining(
            "The related conditions cannot be changed if the Page had ever been Published");
  }

  @Test
  void should_not_create_template_when_name_not_unique() {
    WaTemplate page =
        new WaTemplate(
            "INV",
            "mapping-guide-value",
            "testing page",
            99999L,
            Instant.parse("2000-07-17T02:22:56Z"));

    PageCommand.CreateTemplate create =
        new PageCommand.CreateTemplate(
            "template-name", "template-description", 1409L, Instant.parse("2010-11-17T17:05:19Z"));

    assertThatThrownBy(() -> page.createTemplate(name -> false, create))
        .isInstanceOf(TemplateCreationException.class)
        .hasMessageContaining("Another Template is named template-name");
  }

  @Test
  void should_not_create_template_when_name_is_blank() {
    WaTemplate page =
        new WaTemplate(
            "INV",
            "mapping-guide-value",
            "testing page",
            99999L,
            Instant.parse("2000-07-17T02:22:56Z"));

    PageCommand.CreateTemplate create =
        new PageCommand.CreateTemplate(
            "", "template-description", 1409L, Instant.parse("2010-11-17T17:05:19Z"));

    assertThatThrownBy(() -> page.createTemplate(name -> true, create))
        .isInstanceOf(TemplateCreationException.class)
        .hasMessageContaining("A Template name is required");
  }

  @Test
  void should_not_create_template_when_name_is_null() {
    WaTemplate page =
        new WaTemplate(
            "INV",
            "mapping-guide-value",
            "testing page",
            99999L,
            Instant.parse("2000-07-17T02:22:56Z"));

    PageCommand.CreateTemplate create =
        new PageCommand.CreateTemplate(
            null, "template-description", 1409L, Instant.parse("2010-11-17T17:05:19Z"));
    assertThatThrownBy(() -> page.createTemplate(name -> true, create))
        .isInstanceOf(TemplateCreationException.class)
        .hasMessageContaining("A Template name is required");
  }

  @Test
  void should_not_create_template_when_description_is_blank() {
    WaTemplate page =
        new WaTemplate(
            "INV",
            "mapping-guide-value",
            "testing page",
            99999L,
            Instant.parse("2000-07-17T02:22:56Z"));

    PageCommand.CreateTemplate create =
        new PageCommand.CreateTemplate(
            "template-name", "", 1409L, Instant.parse("2010-11-17T17:05:19Z"));
    assertThatThrownBy(() -> page.createTemplate(name -> true, create))
        .isInstanceOf(TemplateCreationException.class)
        .hasMessageContaining("A Template description is required");
  }

  @Test
  void should_not_create_template_when_description_is_null() {
    WaTemplate page =
        new WaTemplate(
            "INV",
            "mapping-guide-value",
            "testing page",
            99999L,
            Instant.parse("2000-07-17T02:22:56Z"));

    PageCommand.CreateTemplate create =
        new PageCommand.CreateTemplate(
            "template-name", null, 1409L, Instant.parse("2010-11-17T17:05:19Z"));

    assertThatThrownBy(() -> page.createTemplate(name -> true, create))
        .isInstanceOf(TemplateCreationException.class)
        .hasMessageContaining("A Template description is required");
  }
}

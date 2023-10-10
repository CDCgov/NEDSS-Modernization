package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.Component;
import gov.cdc.nbs.questionbank.page.component.Content;
import gov.cdc.nbs.questionbank.page.component.Input;
import gov.cdc.nbs.questionbank.page.component.Page;
import gov.cdc.nbs.questionbank.page.component.Section;
import gov.cdc.nbs.questionbank.page.component.Selection;
import gov.cdc.nbs.questionbank.page.component.SubSection;
import gov.cdc.nbs.questionbank.page.component.Tab;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Service
class DetailedPageResponseMapper {

  DetailedPageResponse asResponse(final DetailedPage detailed, final Collection<DetailedPageRule> rules) {
    return new DetailedPageResponse(
        detailed.identifier(),
        detailed.name(),
        detailed.description(),
        mapAll(this::asRule, rules)
    );
  }

  DetailedPageResponse asResponse(
      final DetailedPage detailed,
      final Collection<DetailedPageRule> rules,
      final Component component
  ) {

    Collection<DetailedPageResponse.Tab> tabs = (component instanceof Page page)
        ? mapAll(this::asTab, page.children())
        : List.of();

    return new DetailedPageResponse(
        detailed.identifier(),
        detailed.name(),
        detailed.description(),
        tabs,
        mapAll(this::asRule, rules)
        );
  }

  private static <I, O> Collection<O> mapAll(final Function<I, O> fn, final Collection<I> items) {
    return items.stream().map(fn).toList();
  }

  private DetailedPageResponse.Rule asRule(final DetailedPageRule rule) {
    return new DetailedPageResponse.Rule(
        rule.id(),
        rule.page(),
        rule.logic(),
        rule.values(),
        rule.function(),
        rule.source(),
        rule.target()
    );
  }

  private DetailedPageResponse.Tab asTab(final Tab tab) {
    return new DetailedPageResponse.Tab(
        tab.identifier(),
        tab.definition().name(),
        tab.definition().visible(),
        mapAll(this::asSection, tab.children())
    );
  }

  private DetailedPageResponse.Section asSection(final Section section) {
    return new DetailedPageResponse.Section(
        section.identifier(),
        section.definition().name(),
        section.definition().visible(),
        mapAll(this::asSubSection, section.children())
    );
  }

  private DetailedPageResponse.SubSection asSubSection(final SubSection subsection) {
    return new DetailedPageResponse.SubSection(
        subsection.identifier(),
        subsection.definition().name(),
        subsection.definition().visible(),
        mapAll(this::asQuestion, subsection.children())
    );
  }

  private DetailedPageResponse.Question asQuestion(final Content content) {

    long id = content.identifier();
    String questionType = content.attributes().standard();
    String questionIdentifier = content.attributes().question();
    String name = content.definition().name();
    String subGroup = content.attributes().subGroup();
    String description = content.attributes().description();
    boolean coInfection = content.attributes().coInfection();
    String dataType = content.attributes().dataType();
    String mask = content.attributes().mask();
    boolean allowFutureDates = content instanceof Input input && input.allowFutureDates();
    String labelOnScreen = content.definition().name();
    String questionToolTop = content.attributes().toolTip();
    boolean display = content.definition().visible();
    boolean enabled = content.attributes().enabled();
    boolean required = content.attributes().required();
    String valueSet = content instanceof Selection selection ? selection.valueSet() : null;
    return new DetailedPageResponse.Question(
        id,
        questionType,
        questionIdentifier,
        name,
        subGroup,
        description,
        coInfection,
        dataType,
        mask,
        allowFutureDates,
        labelOnScreen,
        questionToolTop,
        display,
        enabled,
        required,
        valueSet
    );
  }

}

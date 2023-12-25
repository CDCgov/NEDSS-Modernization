package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import gov.cdc.nbs.questionbank.page.component.ContentNode;
import gov.cdc.nbs.questionbank.page.component.InputNode;
import gov.cdc.nbs.questionbank.page.component.PageNode;
import gov.cdc.nbs.questionbank.page.component.SectionNode;
import gov.cdc.nbs.questionbank.page.component.SelectionNode;
import gov.cdc.nbs.questionbank.page.component.SubSectionNode;
import gov.cdc.nbs.questionbank.page.component.TabNode;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Service
class PagesResponseMapper {

  PagesResponse asResponse(final PageDescription detailed, final Collection<PagesRule> rules) {
    return new PagesResponse(
        detailed.identifier(),
        detailed.name(),
        detailed.description(),
        0,
        mapAll(this::asRule, rules)
    );
  }

  PagesResponse asResponse(
      final PageDescription detailed,
      final Collection<PagesRule> rules,
      final ComponentNode component
  ) {

    Collection<PagesResponse.PagesTab> tabs = (component instanceof PageNode page)
        ? mapAll(this::asTab, page.children())
        : List.of();

    return new PagesResponse(
        detailed.identifier(),
        detailed.name(),
        detailed.description(),
        component.identifier(),
        tabs,
        mapAll(this::asRule, rules)
    );
  }

  private static <I, O> Collection<O> mapAll(final Function<I, O> fn, final Collection<I> items) {
    return items.stream().map(fn).toList();
  }

  private PagesResponse.PageRule asRule(final PagesRule rule) {
    return new PagesResponse.PageRule(
        rule.id(),
        rule.page(),
        rule.logic(),
        rule.values(),
        rule.function(),
        rule.source(),
        rule.target()
    );
  }

  private PagesResponse.PagesTab asTab(final TabNode tab) {
    return new PagesResponse.PagesTab(
        tab.identifier(),
        tab.definition().name(),
        tab.definition().order(),
        tab.definition().visible(),
        mapAll(this::asSection, tab.children())
    );
  }

  private PagesResponse.PagesSection asSection(final SectionNode section) {
    return new PagesResponse.PagesSection(
        section.identifier(),
        section.definition().name(),
        section.definition().order(),
        section.definition().visible(),
        mapAll(this::asSubSection, section.children())
    );
  }

  private PagesResponse.PagesSubSection asSubSection(final SubSectionNode subsection) {
    return new PagesResponse.PagesSubSection(
        subsection.identifier(),
        subsection.definition().name(),
        subsection.definition().order(),
        subsection.definition().visible(),
        mapAll(this::asQuestion, subsection.children())
    );
  }

  private PagesResponse.PagesQuestion asQuestion(final ContentNode content) {
    long id = content.identifier();
    boolean isStandard = true;
    String standard = content.attributes().standard();
    String questionIdentifier = content.attributes().question();
    String name = content.definition().name();
    int order = content.definition().order();
    String subGroup = content.attributes().subGroup();
    String description = content.attributes().description();
    boolean coInfection = content.attributes().coInfection();
    String dataType = content.attributes().dataType();
    String mask = content.attributes().mask();
    boolean allowFutureDates = content instanceof InputNode input && input.allowFutureDates();
    String questionToolTop = content.attributes().toolTip();
    boolean display = content.definition().visible();
    boolean enabled = content.attributes().enabled();
    boolean required = content.attributes().required();
    String defaultValue = content.attributes().defaultValue();
    String valueSet = content instanceof SelectionNode selection ? selection.valueSet() : null;
    long displayComponent = content.type().identifier();
    return new PagesResponse.PagesQuestion(
        id,
        isStandard,
        standard,
        questionIdentifier,
        name,
        order,
        subGroup,
        description,
        coInfection,
        dataType,
        mask,
        allowFutureDates,
        questionToolTop,
        display,
        enabled,
        required,
        defaultValue,
        valueSet,
        displayComponent
    );
  }

}

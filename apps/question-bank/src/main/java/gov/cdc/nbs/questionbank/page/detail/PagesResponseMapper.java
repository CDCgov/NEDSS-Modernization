package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.component.ComponentNode;
import gov.cdc.nbs.questionbank.page.component.ContentNode;
import gov.cdc.nbs.questionbank.page.component.InputNode;
import gov.cdc.nbs.questionbank.page.component.PageNode;
import gov.cdc.nbs.questionbank.page.component.SectionNode;
import gov.cdc.nbs.questionbank.page.component.SelectionNode;
import gov.cdc.nbs.questionbank.page.component.SubSectionNode;
import gov.cdc.nbs.questionbank.page.component.TabNode;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
class PagesResponseMapper {

  public static final int ROLLINGNOTE = 1019;

  PagesResponse asResponse(final PageDescription detailed, final Collection<PagesRule> rules) {
    return new PagesResponse(
        detailed.identifier(),
        detailed.name(),
        detailed.status(),
        detailed.description(),
        0,
        mapAll(this::asRule, rules));
  }

  PagesResponse asResponse(
      final PageDescription detailed,
      final Collection<PagesRule> rules,
      final ComponentNode component) {

    Collection<PagesResponse.PagesTab> tabs =
        (component instanceof PageNode page) ? mapAll(this::asTab, page.children()) : List.of();

    return new PagesResponse(
        detailed.identifier(),
        detailed.name(),
        detailed.status(),
        detailed.description(),
        component.identifier(),
        tabs,
        mapAll(this::asRule, rules));
  }

  private static <I, O> Collection<O> mapAll(final Function<I, O> fn, final Collection<I> items) {
    return items.stream().map(fn).toList();
  }

  private PagesResponse.BusinessRule asRule(final PagesRule rule) {
    return new PagesResponse.BusinessRule(
        rule.id(),
        rule.page(),
        rule.logic(),
        rule.values(),
        rule.function(),
        rule.source(),
        rule.target());
  }

  private PagesResponse.PagesTab asTab(final TabNode tab) {
    return new PagesResponse.PagesTab(
        tab.identifier(),
        tab.definition().name(),
        tab.definition().order(),
        tab.definition().visible(),
        mapAll(this::asSection, tab.children()));
  }

  private PagesResponse.PagesSection asSection(final SectionNode section) {
    return new PagesResponse.PagesSection(
        section.identifier(),
        section.definition().name(),
        section.definition().order(),
        section.definition().visible(),
        mapAll(this::asSubSection, section.children()));
  }

  private PagesResponse.PagesSubSection asSubSection(final SubSectionNode subsection) {
    boolean isGroupable = isSubsectionGrouable(subsection);
    return new PagesResponse.PagesSubSection(
        subsection.identifier(),
        subsection.definition().name(),
        subsection.definition().order(),
        subsection.definition().visible(),
        subsection.isGrouped(),
        isGroupable,
        subsection.questionIdentifier(),
        subsection.blockName(),
        mapAll(this::asQuestion, subsection.children()));
  }

  private PagesResponse.PagesQuestion asQuestion(final ContentNode content) {
    long id = content.identifier();
    String adminComments = content.attributes().adminComments();
    boolean isStandard = content.attributes().isStandard();
    boolean isStandardNnd = content.attributes().isStandardNnd();
    String standard = content.attributes().standard(); // PHIN or LOCAL
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
    boolean visible = content.definition().visible();
    boolean enabled = content.attributes().enabled();
    boolean required = content.attributes().required();
    String defaultValue = content.attributes().defaultValue();
    String valueSet = content instanceof SelectionNode selection ? selection.valueSet() : null;
    long displayComponent = content.type().identifier();
    String fieldLength = content.attributes().fieldLength();
    String defaultRdbTableName = content.attributes().defaultRdbTableName();
    String rdbColumnName = content.attributes().rdbColumnName();
    String defaultLabelInReport = content.attributes().defaultLabelInReport();
    String dataMartColumnName = content.attributes().dataMartColumnName();
    boolean isPublished = content.attributes().isPublished();
    int questionGroupSeq = content.attributes().questionGroupSeq();
    String blockName = content.attributes().blockName();
    Integer dataMartRepeatNumber = content.attributes().dataMartRepeatNumber();
    boolean appearsInBatch = content.attributes().appearsInBatch();
    String batchLabel = content.attributes().batchLabel();
    Integer batchWidth = content.attributes().batchWidth();
    String componentBehavior = content.attributes().componentBehavior();
    String componentName = content.attributes().componentName();
    String classCode = content.attributes().classCode();

    return new PagesResponse.PagesQuestion(
        id,
        isStandardNnd,
        isStandard,
        standard,
        questionIdentifier,
        name,
        order,
        questionGroupSeq,
        subGroup,
        description,
        coInfection,
        dataType,
        mask,
        allowFutureDates,
        questionToolTop,
        visible,
        enabled,
        required,
        defaultValue,
        valueSet,
        displayComponent,
        adminComments,
        fieldLength,
        defaultRdbTableName,
        rdbColumnName,
        defaultLabelInReport,
        dataMartColumnName,
        isPublished,
        blockName,
        dataMartRepeatNumber,
        appearsInBatch,
        batchLabel,
        batchWidth,
        componentBehavior,
        componentName,
        classCode);
  }

  boolean isSubsectionGrouable(SubSectionNode subsectionNode) {

    for (ContentNode content : subsectionNode.children()) {
      if (content.attributes().dataLocation() == null
          || !content.attributes().dataLocation().contains("ANSWER_TXT")) return false;
      if (content.attributes().isPublished()) return false;
      if (content.attributes().nbsComponentId() == ROLLINGNOTE
          && subsectionNode.children().size() > 1) return false;
    }
    return true;
  }
}

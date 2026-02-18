package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.questionbank.support.TestDataSettings;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
class PageContentMother {

  private final PageEntityHarness harness;

  private final TestDataSettings settings;

  PageContentMother(final PageEntityHarness harness, final TestDataSettings settings) {
    this.harness = harness;
    this.settings = settings;
  }

  public void withTab(final PageIdentifier page) {
    String name = "%s Tab".formatted(page.name());

    withTab(page, name);
  }

  public void withTab(final PageIdentifier page, final String name) {
    harness
        .with(page)
        .use(
            found -> {
              // a new tab will always go last
              WaUiMetadata last = last(found.getUiMetadata()).orElseThrow();
              int next = last.getOrderNbr() + 1;
              found.addTab(
                  new PageContentCommand.AddTab(
                      name,
                      true,
                      "TAB_" + next, // bring in the test uuid generator!
                      this.settings.createdBy(),
                      Instant.now()));
            });
  }

  public void withRule(final PageIdentifier page) {
    harness
        .with(page)
        .use(
            found -> {
              found.addRule(
                  new PageContentCommand.AddRule(
                      "ruleCd",
                      "errMsgTxt",
                      "recordStatusCd",
                      "javascriptFunction",
                      "javascriptFunctionNm_TEST",
                      this.settings.createdBy(),
                      Instant.now()));
            });
  }

  public void withSectionIn(final PageIdentifier page, final String name, final int tab) {

    harness
        .with(page)
        .use(
            found ->
                nthOfType(found.getUiMetadata(), tab, PageConstants.TAB_COMPONENT)
                    .ifPresent(container -> withSectionIn(found, container, name)));
  }

  public void withSectionIn(final PageIdentifier page, final int tab) {

    harness
        .with(page)
        .use(
            found ->
                nthOfType(found.getUiMetadata(), tab, PageConstants.TAB_COMPONENT)
                    .ifPresent(
                        container ->
                            withSectionIn(
                                found, container, container.getQuestionLabel() + " Section")));
  }

  public void withSectionIn(final PageIdentifier page, final String name, final String tab) {

    harness
        .with(page)
        .use(
            found ->
                found.getUiMetadata().stream()
                    .filter(havingName(tab))
                    .findFirst()
                    .ifPresent(container -> withSectionIn(found, container, name)));
  }

  private void withSectionIn(
      final WaTemplate found, final WaUiMetadata container, final String name) {

    placeWithin(found.getUiMetadata(), container, PageConstants.TAB_COMPONENT)
        .ifPresent(
            order -> found.addSection(name, order, this.settings.createdBy(), Instant.now()));
  }

  public void withSubSectionIn(final PageIdentifier page, final String name, final int section) {

    harness
        .with(page)
        .use(
            found ->
                nthOfType(found.getUiMetadata(), section, PageConstants.SECTION_COMPONENT)
                    .ifPresent(container -> withSubSectionIn(found, container, name)));
  }

  public void withSubSectionIn(final PageIdentifier page, final int section) {

    harness
        .with(page)
        .use(
            found ->
                nthOfType(found.getUiMetadata(), section, PageConstants.SECTION_COMPONENT)
                    .ifPresent(
                        container ->
                            withSubSectionIn(
                                found, container, container.getQuestionLabel() + " Sub-Section")));
  }

  public void withSubSectionIn(final PageIdentifier page, final String name, final String section) {

    harness
        .with(page)
        .use(
            found ->
                found.getUiMetadata().stream()
                    .filter(havingName(section))
                    .findFirst()
                    .ifPresent(container -> withSubSectionIn(found, container, name)));
  }

  private void withSubSectionIn(
      final WaTemplate found, final WaUiMetadata container, final String name) {
    placeWithin(found.getUiMetadata(), container, PageConstants.SECTION_COMPONENT)
        .ifPresent(
            order -> found.addSubSection(name, order, this.settings.createdBy(), Instant.now()));
  }

  public void withStaticElementIn(
      final PageIdentifier page, final String name, final int subsection) {
    harness
        .with(page)
        .use(
            found ->
                nthOfType(found.getUiMetadata(), subsection, PageConstants.SUB_SECTION_COMPONENT)
                    .ifPresent(container -> withStaticElementIn(found, container, name, 1012L)));
  }

  private void withStaticElementIn(
      final WaTemplate found, final WaUiMetadata container, final String name, final long type) {
    placeWithin(found.getUiMetadata(), container, type)
        .ifPresent(
            order ->
                found.addLineSeparator(
                    new PageContentCommand.AddLineSeparator(
                        found, order, this.settings.createdBy(), name, Instant.now())));
  }

  public void withContentIn(
      final PageIdentifier page,
      final String name,
      final int subSection,
      final long type,
      final String identifier,
      final String dataType) {

    harness
        .with(page)
        .use(
            found ->
                nthOfType(found.getUiMetadata(), subSection, PageConstants.SUB_SECTION_COMPONENT)
                    .ifPresent(
                        container ->
                            withContentIn(found, container, name, type, identifier, dataType)));
  }

  public void withContentIn(final PageIdentifier page, final String name, final String subSection) {

    harness
        .with(page)
        .use(
            found ->
                found.getUiMetadata().stream()
                    .filter(havingName(subSection))
                    .findFirst()
                    .ifPresent(
                        container -> withContentIn(found, container, name, 1008L, "DEM107", null)));
  }

  private void withContentIn(
      final WaTemplate found,
      final WaUiMetadata container,
      final String name,
      final long type,
      final String identifier,
      final String dataType) {
    placeWithin(found.getUiMetadata(), container, type)
        .ifPresent(
            order ->
                found.addContent(
                    name,
                    type,
                    order,
                    this.settings.createdBy(),
                    Instant.now(),
                    identifier,
                    dataType));
  }

  public static Optional<Integer> placeWithin(
      final Collection<WaUiMetadata> components, final WaUiMetadata container, final long type) {
    return components.stream()
        .sorted(Comparator.comparing(WaUiMetadata::getOrderNbr))
        .filter(after(container.getOrderNbr()))
        .takeWhile(Predicate.not(havingType(container.getNbsUiComponentUid())))
        .filter(havingType(type))
        .max(Comparator.comparing(WaUiMetadata::getOrderNbr))
        .or(() -> Optional.of(container))
        .map(previous -> previous.getOrderNbr() + 1);
  }

  public static Optional<WaUiMetadata> last(final Collection<WaUiMetadata> components) {
    return components.stream().max(Comparator.comparing(WaUiMetadata::getOrderNbr));
  }

  public static Predicate<WaUiMetadata> havingName(final String name) {
    return component -> Objects.equals(name, component.getQuestionLabel());
  }

  public static Predicate<WaUiMetadata> havingType(final long type) {
    return component -> type == component.getNbsUiComponentUid();
  }

  public static Predicate<WaUiMetadata> after(final int position) {
    return component -> component.getOrderNbr() > position;
  }

  public static Optional<WaUiMetadata> nthOfType(
      final Collection<WaUiMetadata> components, final int n, final long type) {
    return components.stream()
        .filter(havingType(type))
        .sorted(Comparator.comparing(WaUiMetadata::getOrderNbr))
        .skip(n - 1)
        .findFirst();
  }
}

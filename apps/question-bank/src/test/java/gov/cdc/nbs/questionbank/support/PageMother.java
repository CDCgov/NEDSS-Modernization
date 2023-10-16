package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WANNDMetadataRepository;
import gov.cdc.nbs.questionbank.entity.repository.WARDBMetadataRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.component.tree.ComponentTreeResolver;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Component
@Transactional
public class PageMother {
  private static final String ASEPTIC_MENINGITIS_ID = "10010";
  private static final String BRUCELLOSIS_ID = "10020";

  @Autowired
  private WaUiMetadataRepository waUiMetadatumRepository;

  @Autowired
  private WaRuleMetaDataRepository waRuleMetaDataRepository;

  @Autowired
  private WANNDMetadataRepository wanndMetadataRepository;

  @Autowired
  private WARDBMetadataRepository wARDBMetadataRepository;

  @Autowired
  EntityManager entityManager;

  @Autowired
  TestDataSettings settings;

  @Autowired
  TestPageCleaner cleaner;

  @Autowired
  Available<PageIdentifier> available;

  @Autowired
  Active<PageIdentifier> active;

  @Autowired
  ComponentTreeResolver componentTreeResolver;

  public void clean() {
    this.available.all().forEach(cleaner::clean);
    wanndMetadataRepository.deleteAll();
    wARDBMetadataRepository.deleteAll();
    waRuleMetaDataRepository.deleteAll();
  }

  public WaTemplate one() {
    return available.maybeOne()
        .map(this::managed)
        .orElseThrow(() -> new IllegalStateException("No pages exist"));
  }

  private WaTemplate managed(final PageIdentifier identifier) {
    return this.entityManager.find(WaTemplate.class, identifier.id());
  }

  public WaTemplate brucellosis() {
    return available.all()
        .map(this::managed)
        .filter(t -> t.getConditionMappings()
            .stream()
            .anyMatch(c -> c.getConditionCd().equals(BRUCELLOSIS_ID)))
        .findFirst()
        .orElseGet(this::createBrucellosisPage);
  }

  public WaTemplate asepticMeningitis() {
    return available.all()
        .map(this::managed)
        .filter(t -> t.getConditionMappings()
            .stream()
            .anyMatch(c -> c.getConditionCd().equals(ASEPTIC_MENINGITIS_ID)))
        .findFirst()
        .orElseGet(this::createAsepticMeningitisPage);
  }

  private WaTemplate createBrucellosisPage() {
    Instant now = Instant.now();
    WaTemplate page = new WaTemplate();
    page.setTemplateNm("brucellosis page");
    page.setTemplateType("Draft");
    page.setBusObjType("INV");
    page.setNndEntityIdentifier("GEN_Case_Map_v2.0");

    page.setRecordStatusCd("Active");
    page.setRecordStatusTime(now);
    page.setAddTime(now);
    page.setAddUserId(1L);
    page.setLastChgTime(now);
    page.setLastChgUserId(1L);

    page.associateCondition(BRUCELLOSIS_ID, this.settings.createdBy(), this.settings.createdOn());

    WaUiMetadata tab = new WaUiMetadata();
    tab.setWaTemplateUid(page);
    tab.setNbsUiComponentUid(1010L);
    tab.setOrderNbr(1);
    tab.setDisplayInd("T");
    tab.setVersionCtrlNbr(1);

    WaUiMetadata section = new WaUiMetadata();
    section.setWaTemplateUid(page);
    section.setNbsUiComponentUid(1015L);
    section.setOrderNbr(2);
    section.setDisplayInd("T");
    section.setVersionCtrlNbr(1);

    page.setUiMetadata(Arrays.asList(tab, section));
    this.entityManager.persist(page);
    include(page);
    return page;
  }

  private WaTemplate createAsepticMeningitisPage() {
    Instant now = Instant.now().plusSeconds(5);
    WaTemplate page = new WaTemplate();
    page.setTemplateNm("Aseptic Meningitis");
    page.setTemplateType("Draft");
    page.setBusObjType("INV");
    page.setNndEntityIdentifier("GEN_Case_Map_v2.0");

    page.setRecordStatusCd("Active");
    page.setRecordStatusTime(now);
    page.setAddTime(now);
    page.setAddUserId(1L);
    page.setLastChgTime(now);
    page.setLastChgUserId(1L);

    page.associateCondition(ASEPTIC_MENINGITIS_ID, this.settings.createdBy(), this.settings.createdOn());

    this.entityManager.persist(page);

    // add page detail mappings
    WaUiMetadata tab = getwaUiMetaDtum(page, PageConstants.TAB_COMPONENT, 2);
    WaUiMetadata section = getwaUiMetaDtum(page, PageConstants.SECTION_COMPONENT, 3);
    WaUiMetadata subSection = getwaUiMetaDtum(page, PageConstants.SUB_SECTION_COMPONENT, 4);
    WaUiMetadata question = getwaUiMetaDtum(page, PageConstants.SPE_QUESTION_COMPONENT, 5);

    waUiMetadatumRepository.save(tab);
    waUiMetadatumRepository.save(section);
    waUiMetadatumRepository.save(subSection);
    waUiMetadatumRepository.save(question);

    include(page);
    return page;
  }

  public WaTemplate createPageDraft(WaTemplate pageIn) {

    this.entityManager.persist(pageIn);

    Instant now = Instant.now().plusSeconds(15);
    WaTemplate page = new WaTemplate();
    page.setTemplateNm(pageIn.getTemplateNm());
    page.setTemplateType("Draft");
    page.setBusObjType("INV");
    page.setNndEntityIdentifier("GEN_Case_Map_v2.0");

    page.setRecordStatusCd("Active");
    page.setRecordStatusTime(now);
    page.setAddTime(now);
    page.setAddUserId(1L);
    page.setLastChgTime(now);
    page.setLastChgUserId(1L);

    page.associateCondition(ASEPTIC_MENINGITIS_ID, this.settings.createdBy(), this.settings.createdOn());

    this.entityManager.persist(page);

    // add page detail mappings
    WaUiMetadata tab = getwaUiMetaDtum(page, PageConstants.TAB_COMPONENT, 2);
    WaUiMetadata section = getwaUiMetaDtum(page, PageConstants.SECTION_COMPONENT, 3);
    WaUiMetadata subSection = getwaUiMetaDtum(page, PageConstants.SUB_SECTION_COMPONENT, 4);
    WaUiMetadata question = getwaUiMetaDtum(page, PageConstants.SPE_QUESTION_COMPONENT, 5);

    waUiMetadatumRepository.save(tab);
    waUiMetadatumRepository.save(section);
    waUiMetadatumRepository.save(subSection);
    waUiMetadatumRepository.save(question);

    include(page);
    return page;
  }

  private WaUiMetadata getwaUiMetaDtum(WaTemplate aPage, Long nbsUiComponentUid, Integer orderNumber) {
    WaUiMetadata record = new WaUiMetadata();
    record.setWaTemplateUid(aPage);
    record.setNbsUiComponentUid(nbsUiComponentUid);
    record.setOrderNbr(orderNumber);
    record.setVersionCtrlNbr(0);
    return record;
  }

  public void create(
      final String object,
      final String name,
      final String mappingGuide) {

    WaTemplate page = new WaTemplate(
        object,
        mappingGuide,
        name,
        this.settings.createdBy(),
        this.settings.createdOn());

    this.entityManager.persist(page);

    include(page);
  }

  private void include(final WaTemplate page) {
    PageIdentifier created = new PageIdentifier(page.getId(), page.getTemplateNm());

    this.active.active(created);
    this.available.available(created);
  }

  public void withDescription(final PageIdentifier page, final String description) {
    WaTemplate found = this.entityManager.find(WaTemplate.class, page.id());

    found.setDescTxt(description);
  }

  public void withTab(final PageIdentifier page) {
    String name = String.format("%s Tab", page.name());

    withTab(page, name);
  }

  public void withTab(final PageIdentifier page, final String name) {
    WaTemplate found = this.entityManager.find(WaTemplate.class, page.id());

    // a new tab will always go last
    WaUiMetadata last = last(found.getUiMetadata()).orElseThrow();
    int next = last.getOrderNbr() + 1;
    found.add(new PageContentCommand.AddTab(
        found,
        name,
        true,
        "TAB_"+next,  //  bring in the test uuid generator!
        next,
        this.settings.createdBy(),
        Instant.now()
    ));

  }

  public void withSectionIn(
      final PageIdentifier page,
      final String name,
      final int tab) {

    WaTemplate found = this.entityManager.find(WaTemplate.class, page.id());

    nthOfType(found.getUiMetadata(), tab, PageConstants.TAB_COMPONENT)
        .ifPresent(container -> withSectionIn(found, container, name));
  }

  public void withSectionIn(
      final PageIdentifier page,
      final int tab) {

    WaTemplate found = this.entityManager.find(WaTemplate.class, page.id());

    nthOfType(found.getUiMetadata(), tab, PageConstants.TAB_COMPONENT)
        .ifPresent(container -> withSectionIn(found, container, container.getQuestionLabel() + " Section"));
  }

  public void withSectionIn(
      final PageIdentifier page,
      final String name,
      final String tab) {

    WaTemplate found = this.entityManager.find(WaTemplate.class, page.id());

    found.getUiMetadata().stream().filter(havingName(tab))
        .findFirst()
        .ifPresent(container -> withSectionIn(found, container, name));

  }

  private void withSectionIn(
      final WaTemplate found,
      final WaUiMetadata container,
      final String name) {
    placeWithin(found.getUiMetadata(), container, PageConstants.SECTION_COMPONENT)
        .ifPresent(order -> found.addSection(name, order, this.settings.createdBy(), Instant.now()));

  }

  public void withSubSectionIn(
      final PageIdentifier page,
      final String name,
      final int section) {

    WaTemplate found = this.entityManager.find(WaTemplate.class, page.id());

    nthOfType(found.getUiMetadata(), section, PageConstants.SECTION_COMPONENT)
        .ifPresent(container -> withSubSectionIn(found, container, name));
  }

  public void withSubSectionIn(
      final PageIdentifier page,
      final int section) {

    WaTemplate found = this.entityManager.find(WaTemplate.class, page.id());

    nthOfType(found.getUiMetadata(), section, PageConstants.SECTION_COMPONENT)
        .ifPresent(container -> withSubSectionIn(found, container, container.getQuestionLabel() + " Sub-Section"));
  }

  public void withSubSectionIn(
      final PageIdentifier page,
      final String name,
      final String section) {

    WaTemplate found = this.entityManager.find(WaTemplate.class, page.id());

    found.getUiMetadata().stream().filter(havingName(section))
        .findFirst()
        .ifPresent(container -> withSubSectionIn(found, container, name));
  }

  private void withSubSectionIn(
      final WaTemplate found,
      final WaUiMetadata container,
      final String name) {
    placeWithin(found.getUiMetadata(), container, PageConstants.SECTION_COMPONENT)
        .ifPresent(order -> found.addSubSection(name, order, this.settings.createdBy(), Instant.now()));

  }

  public void withContentIn(
      final PageIdentifier page,
      final String name,
      final int subSection) {

    WaTemplate found = this.entityManager.find(WaTemplate.class, page.id());

    nthOfType(found.getUiMetadata(), subSection, PageConstants.SUB_SECTION_COMPONENT)
        .ifPresent(container -> withContentIn(found, container, name));
  }

  public void withContentIn(
      final PageIdentifier page,
      final String name,
      final String subSection) {

    WaTemplate found = this.entityManager.find(WaTemplate.class, page.id());

    found.getUiMetadata().stream().filter(havingName(subSection))
        .findFirst()
        .ifPresent(container -> withContentIn(found, container, name));
  }

  private void withContentIn(
      final WaTemplate found,
      final WaUiMetadata container,
      final String name) {
    placeWithin(found.getUiMetadata(), container, 1011L)
        .ifPresent(order -> found.addContent(name, 1011L, order, this.settings.createdBy(), Instant.now()));

  }

  public static Optional<Integer> placeWithin(
      final Collection<WaUiMetadata> components,
      final WaUiMetadata container,
      final long type) {
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
    return components
        .stream()
        .max(Comparator.comparing(WaUiMetadata::getOrderNbr));
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
      final Collection<WaUiMetadata> components,
      final int n,
      final long type) {
    return components.stream()
        .filter(havingType(type))
        .sorted(Comparator.comparing(WaUiMetadata::getOrderNbr))
        .skip(n - 1)
        .findFirst();
  }
}

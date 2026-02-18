package gov.cdc.nbs.questionbank.page;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.questionbank.support.TestDataSettings;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PageMother {
  private static final String ASEPTIC_MENINGITIS_ID = "10010";
  private static final String BRUCELLOSIS_ID = "10020";

  private final WaUiMetadataRepository waUiMetadatumRepository;

  private final EntityManager entityManager;

  private final TestDataSettings settings;

  private final TestPageCleaner cleaner;

  private final PageEntityHarness harness;

  private final Available<PageIdentifier> available;

  private final Active<PageIdentifier> active;

  PageMother(
      final WaUiMetadataRepository waUiMetadatumRepository,
      final EntityManager entityManager,
      final TestDataSettings settings,
      final TestPageCleaner cleaner,
      final PageEntityHarness harness,
      final Available<PageIdentifier> available,
      final Active<PageIdentifier> active) {
    this.waUiMetadatumRepository = waUiMetadatumRepository;
    this.entityManager = entityManager;
    this.settings = settings;
    this.cleaner = cleaner;
    this.harness = harness;
    this.available = available;
    this.active = active;
  }

  public void clean() {
    this.available.all().forEach(cleaner::clean);
    this.available.reset();
  }

  public WaTemplate one() {
    return available
        .maybeOne()
        .map(this::managed)
        .orElseThrow(() -> new IllegalStateException("No pages exist"));
  }

  public List<WaUiMetadata> pageContent() {
    return new ArrayList<>(one().getUiMetadata());
  }

  private WaTemplate managed(final PageIdentifier identifier) {
    return this.entityManager.find(WaTemplate.class, identifier.id());
  }

  public WaTemplate brucellosis() {
    return available
        .all()
        .map(this::managed)
        .filter(
            t ->
                t.getConditionMappings().stream()
                    .anyMatch(c -> c.getConditionCd().equals(BRUCELLOSIS_ID)))
        .findFirst()
        .orElseGet(this::createBrucellosisPage);
  }

  public void asepticMeningitis() {
    available
        .all()
        .map(this::managed)
        .filter(
            t ->
                t.getConditionMappings().stream()
                    .anyMatch(c -> c.getConditionCd().equals(ASEPTIC_MENINGITIS_ID)))
        .findFirst()
        .orElseGet(this::createAsepticMeningitisPage);
  }

  private WaTemplate createBrucellosisPage() {
    Instant now = Instant.now();
    WaTemplate page = new WaTemplate();
    page.setTemplateNm("Brucellosis page");
    page.setTemplateType("Draft");
    page.setBusObjType("INV");
    page.setNndEntityIdentifier("GEN_Case_Map_v2.0");

    page.setRecordStatusCd("Active");
    page.setRecordStatusTime(now);
    page.setAddTime(now);
    page.setAddUserId(1L);
    page.setLastChgTime(now);
    page.setLastChgUserId(1L);

    page.relate(
        new PageCommand.RelateCondition(
            BRUCELLOSIS_ID, this.settings.createdBy(), this.settings.createdOn()));

    WaUiMetadata pageType = new WaUiMetadata();
    pageType.setWaTemplateUid(page);
    pageType.setNbsUiComponentUid(1002L);
    pageType.setOrderNbr(1);
    pageType.setDisplayInd("T");
    pageType.setVersionCtrlNbr(1);

    WaUiMetadata tab = new WaUiMetadata();
    tab.setWaTemplateUid(page);
    tab.setNbsUiComponentUid(1010L);
    tab.setOrderNbr(2);
    tab.setDisplayInd("T");
    tab.setVersionCtrlNbr(1);
    tab.setQuestionLabel("First tab");

    WaUiMetadata section = new WaUiMetadata();
    section.setWaTemplateUid(page);
    section.setNbsUiComponentUid(1015L);
    section.setOrderNbr(3);
    section.setDisplayInd("T");
    section.setVersionCtrlNbr(1);
    section.setQuestionLabel("First section");

    WaUiMetadata subsection = new WaUiMetadata();
    subsection.setWaTemplateUid(page);
    subsection.setNbsUiComponentUid(1016L);
    subsection.setOrderNbr(4);
    subsection.setDisplayInd("T");
    subsection.setVersionCtrlNbr(1);
    subsection.setQuestionLabel("First subsection");

    WaUiMetadata question = new WaUiMetadata();
    question.setWaTemplateUid(page);
    question.setNbsUiComponentUid(1009L);
    question.setOrderNbr(5);
    question.setDisplayInd("T");
    question.setVersionCtrlNbr(1);
    question.setQuestionLabel("First question");

    WaUiMetadata tab2 = new WaUiMetadata();
    tab2.setWaTemplateUid(page);
    tab2.setNbsUiComponentUid(1010L);
    tab2.setOrderNbr(6);
    tab2.setDisplayInd("T");
    tab2.setVersionCtrlNbr(1);
    tab2.setQuestionLabel("Second tab");

    WaUiMetadata section2 = new WaUiMetadata();
    section2.setWaTemplateUid(page);
    section2.setNbsUiComponentUid(1015L);
    section2.setOrderNbr(7);
    section2.setDisplayInd("T");
    section2.setVersionCtrlNbr(1);
    section2.setQuestionLabel("Second section");

    WaUiMetadata subsection2 = new WaUiMetadata();
    subsection2.setWaTemplateUid(page);
    subsection2.setNbsUiComponentUid(1016L);
    subsection2.setOrderNbr(8);
    subsection2.setDisplayInd("T");
    subsection2.setVersionCtrlNbr(1);
    subsection2.setQuestionLabel("Second subsection");

    WaUiMetadata question2 = new WaUiMetadata();
    question2.setWaTemplateUid(page);
    question2.setNbsUiComponentUid(1009L);
    question2.setOrderNbr(9);
    question2.setDisplayInd("T");
    question2.setVersionCtrlNbr(1);
    question2.setQuestionLabel("Second question");

    page.setUiMetadata(
        Arrays.asList(
            pageType, tab, section, subsection, question, tab2, section2, subsection2, question2));
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

    page.relate(
        new PageCommand.RelateCondition(
            ASEPTIC_MENINGITIS_ID, this.settings.createdBy(), this.settings.createdOn()));

    include(page);

    // add page detail mappings
    WaUiMetadata tab = getwaUiMetaDtum(page, PageConstants.TAB_COMPONENT, 2);
    WaUiMetadata section = getwaUiMetaDtum(page, PageConstants.SECTION_COMPONENT, 3);
    WaUiMetadata subSection = getwaUiMetaDtum(page, PageConstants.SUB_SECTION_COMPONENT, 4);
    WaUiMetadata question = getwaUiMetaDtum(page, PageConstants.SPE_QUESTION_COMPONENT, 5);

    waUiMetadatumRepository.save(tab);
    waUiMetadatumRepository.save(section);
    waUiMetadatumRepository.save(subSection);
    waUiMetadatumRepository.save(question);

    return page;
  }

  public void createPageDraft(WaTemplate pageIn) {

    Instant now = Instant.now().plusSeconds(15);
    WaTemplate page = new WaTemplate();
    page.setTemplateNm(pageIn.getTemplateNm());
    page.setFormCd(pageIn.getFormCd());
    page.setTemplateType("Draft");
    page.setBusObjType("INV");
    page.setNndEntityIdentifier("GEN_Case_Map_v2.0");

    page.setRecordStatusCd("Active");
    page.setRecordStatusTime(now);
    page.setAddTime(now);
    page.setAddUserId(1L);
    page.setLastChgTime(now);
    page.setLastChgUserId(1L);

    page.relate(
        new PageCommand.RelateCondition(
            ASEPTIC_MENINGITIS_ID, this.settings.createdBy(), this.settings.createdOn()));

    include(page);

    // add page detail mappings
    page.addTab(
        new PageContentCommand.AddTab(
            "tab", true, "TAB_", this.settings.createdBy(), Instant.now()));

    WaUiMetadata section = getwaUiMetaDtum(page, PageConstants.SECTION_COMPONENT, 3);
    WaUiMetadata subSection = getwaUiMetaDtum(page, PageConstants.SUB_SECTION_COMPONENT, 4);
    WaUiMetadata question = getwaUiMetaDtum(page, PageConstants.SPE_QUESTION_COMPONENT, 5);

    waUiMetadatumRepository.save(section);
    waUiMetadatumRepository.save(subSection);
    waUiMetadatumRepository.save(question);
  }

  public WaTemplate createPagePublishedWithDraft(WaTemplate pageIn) {

    Instant now = Instant.now().plusSeconds(15);
    WaTemplate page = new WaTemplate();
    page.setTemplateNm(pageIn.getTemplateNm());
    page.setFormCd(pageIn.getFormCd());
    page.setTemplateType("Published With Draft");
    page.setBusObjType("INV");
    page.setNndEntityIdentifier("GEN_Case_Map_v2.0");

    page.setRecordStatusCd("Active");
    page.setRecordStatusTime(now);
    page.setAddTime(now);
    page.setAddUserId(1L);
    page.setLastChgTime(now);
    page.setLastChgUserId(1L);

    include(page);

    return page;
  }

  private WaUiMetadata getwaUiMetaDtum(
      WaTemplate aPage, Long nbsUiComponentUid, Integer orderNumber) {
    WaUiMetadata metadata = new WaUiMetadata();
    metadata.setWaTemplateUid(aPage);
    metadata.setNbsUiComponentUid(nbsUiComponentUid);
    metadata.setOrderNbr(orderNumber);
    metadata.setVersionCtrlNbr(0);
    return metadata;
  }

  public void create(final String eventType, final String name, final String mappingGuide) {

    WaTemplate page =
        new WaTemplate(
            eventType, mappingGuide, name, this.settings.createdBy(), this.settings.createdOn());

    include(page);
  }

  private void include(final WaTemplate page) {
    this.entityManager.persist(page);
    this.entityManager.flush();
    PageIdentifier created = new PageIdentifier(page.getId(), page.getTemplateNm());

    this.active.active(created);
    this.available.available(created);
  }

  public void withName(final PageIdentifier page, final String value) {
    withName(page, value, this.settings.createdBy(), this.settings.createdOn());
  }

  public void withName(
      final PageIdentifier page, final String value, final long user, final Instant when) {
    harness
        .with(page)
        .use(
            found -> found.changeName(name -> true, new PageCommand.ChangeName(value, user, when)));
  }

  public void withDatamart(final PageIdentifier page, final String value) {
    harness.with(page).use(found -> found.setDatamartNm(value));
  }

  public void withDescription(final PageIdentifier page, final String description) {
    harness.with(page).use(found -> found.setDescTxt(description));
  }

  public void draft(final PageIdentifier page) {
    harness
        .with(page)
        .use(
            //  this should be replaced by the command when it is created.  It should result in the
            // creation of a new Page
            //  with that becoming the Active page
            found -> {
              found.setTemplateType("Draft");
              found.setPublishIndCd('F');
            });
  }

  public void template(final PageIdentifier page) {
    harness
        .with(page)
        .use(
            found -> {
              //  this should be replaced by the command when it is created.  It should result in
              // the creation of a new Page
              //  with that becoming the Active page
              found.setTemplateType("Template");
              found.setPublishIndCd('F');
            });
  }

  public void legacy(final PageIdentifier page) {
    harness
        .with(page)
        .use(
            found -> {
              //  this should be replaced by the command when it is created.  It should result in
              // the creation of a new Page
              //  with that becoming the Active page
              found.setTemplateType("LEGACY");
              found.setPublishIndCd('F');
            });
  }

  public void published(final PageIdentifier page) {
    harness
        .with(page)
        .use(
            found ->
                found.publish(
                    new PageCommand.Publish(this.settings.createdBy(), this.settings.createdOn())));
  }

  public void withCondition(final PageIdentifier page, final String condition) {
    harness
        .with(page)
        .use(
            found ->
                found.relate(
                    new PageCommand.RelateCondition(
                        condition, this.settings.createdBy(), this.settings.createdOn())));
  }
}

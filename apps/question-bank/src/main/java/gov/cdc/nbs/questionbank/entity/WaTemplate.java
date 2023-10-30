package gov.cdc.nbs.questionbank.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import gov.cdc.nbs.questionbank.page.PageCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "WA_template", catalog = "NBS_ODSE")
public class WaTemplate {
  private static final String DRAFT = "Draft";
  private static final long TAB = 1010l;
  private static final long SECTION = 1015l;
  private static final long SUB_SECTION = 1016l;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "wa_template_uid", nullable = false)
  private Long id;

  @Column(name = "template_type", nullable = false, length = 50)
  private String templateType;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "xml_payload", columnDefinition = "TEXT")
  private String xmlPayload;

  @Column(name = "publish_version_nbr")
  private Integer publishVersionNbr;

  @Column(name = "form_cd", length = 50)
  private String formCd;

  @Column(name = "condition_cd", length = 20)
  private String conditionCd;

  @Column(name = "bus_obj_type", nullable = false, length = 50)
  private String busObjType;

  @Column(name = "datamart_nm", length = 21)
  private String datamartNm;

  @Column(name = "record_status_cd", nullable = false, length = 20)
  private String recordStatusCd;

  @Column(name = "record_status_time", nullable = false)
  private Instant recordStatusTime;

  @Column(name = "last_chg_time", nullable = false)
  private Instant lastChgTime;

  @Column(name = "last_chg_user_id", nullable = false)
  private Long lastChgUserId;

  @Column(name = "local_id", length = 50)
  private String localId;

  @Column(name = "desc_txt", length = 2000)
  private String descTxt;

  @Column(name = "template_nm", length = 50)
  private String templateNm;

  @Column(name = "publish_ind_cd")
  private Character publishIndCd;

  @Column(name = "add_time", nullable = false)
  private Instant addTime;

  @Column(name = "add_user_id", nullable = false)
  private Long addUserId;

  @Column(name = "nnd_entity_identifier", length = 200)
  private String nndEntityIdentifier;

  @Column(name = "parent_template_uid")
  private Long parentTemplateUid;

  @Column(name = "source_nm", length = 250)
  private String sourceNm;

  @Column(name = "template_version_nbr")
  private Integer templateVersionNbr;

  @Column(name = "version_note", length = 2000)
  private String versionNote;

  @OneToMany(fetch = FetchType.LAZY,
      mappedBy = "waTemplateUid",
      cascade = {
          CascadeType.MERGE,
          CascadeType.REMOVE,
          CascadeType.PERSIST
      })
  private Set<PageCondMapping> conditionMappings;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "waTemplateUid", cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE,
      CascadeType.REMOVE
  }, orphanRemoval = true)
  @OrderBy("orderNbr")
  private List<WaUiMetadata> uiMetadata;

  public WaTemplate() {
    this.templateType = DRAFT;
    this.recordStatusCd = "ACTIVE";
    this.conditionMappings = new HashSet<>();
    this.uiMetadata = new ArrayList<>();
  }

  public WaTemplate(
      final String object,
      final String mappingGuide,
      final String name,
      final long createdBy,
      final Instant createdOn) {
    this();
    this.busObjType = object;
    this.nndEntityIdentifier = mappingGuide;
    this.templateNm = name;

    this.addUserId = createdBy;
    this.addTime = createdOn;

    this.lastChgUserId = createdBy;
    this.lastChgTime = createdOn;

    this.recordStatusTime = createdOn;

    this.uiMetadata = initializeComponents();

  }

  private List<WaUiMetadata> initializeComponents() {
    WaUiMetadata root = new WaUiMetadata(
        this,
        1002,
        this.templateNm,
        1,
        this.addUserId,
        this.addTime);

    root.setAddUserId(this.addUserId);
    root.setAddTime(this.addTime);

    root.setLastChgUserId(this.lastChgUserId);
    root.setLastChgTime(this.lastChgTime);

    root.setRecordStatusTime(this.recordStatusTime);

    ArrayList<WaUiMetadata> components = new ArrayList<>();

    components.add(root);

    return components;
  }

  public WaUiMetadata updateTab(PageContentCommand.UpdateTab command) {
    // Can only modify Draft pages
    verifyDraftType();

    WaUiMetadata section = uiMetadata.stream()
        .filter(ui -> ui.getId() == command.tab() && ui.getNbsUiComponentUid() == 1010)
        .findFirst()
        .orElseThrow(() -> new PageContentModificationException("Failed to find tab to update"));

    section.update(command);
    changed(command);
    return section;
  }

  public void deleteTab(PageContentCommand.DeleteTab command) {
    // Can only modify Draft pages
    verifyDraftType();

    // Find the tab to delete
    WaUiMetadata tab = uiMetadata.stream()
        .filter(e -> e.getId() == command.tabId() && e.getNbsUiComponentUid() == TAB)
        .findFirst()
        .orElseThrow(
            () -> new PageContentModificationException("Failed to find tab with id: " + command.tabId()));

    // If element after section is null or another Tab then we can delete the tab
    if (!isElementAtOrderNullOrOneOf(Arrays.asList(TAB), tab.getOrderNbr() + 1)) {
      throw new PageContentModificationException("Unable to delete a tab with content");
    }

    // Remove section and adjust orderNbrs
    uiMetadata.remove(tab);
    adjustingComponentsFrom(tab.getOrderNbr());
    changed(command);
  }

  public WaUiMetadata addTab(PageContentCommand.AddTab command) {
    // Can only modify Draft pages
    verifyDraftType();

    // Tabs are always inserted at the end, so find the max or 1
    Integer orderNumber = uiMetadata.stream()
        .mapToInt(WaUiMetadata::getOrderNbr)
        .max()
        .orElse(1) + 1;

    // create tab
    WaUiMetadata tab = new WaUiMetadata(this, command, orderNumber);

    this.uiMetadata.add(tab);
    changed(command);
    return tab;
  }

  public void addTab(WaUiMetadata tab) {
    including(tab);
  }

  public void addSection(
      final String name,
      final int at,
      final long addedBy,
      final Instant addedOn) {
    WaUiMetadata component = new WaUiMetadata(
        this,
        PageConstants.SECTION_COMPONENT,
        name,
        at,
        addedBy,
        addedOn);

    including(component);
  }

  public void addSection(WaUiMetadata section) {
    including(section);
  }

  public WaUiMetadata addSection(PageContentCommand.AddSection command) {
    // Can only modify Draft pages
    verifyDraftType();

    // Find the container to insert section into
    WaUiMetadata tab = uiMetadata.stream()
        .filter(ui -> ui.getId() == command.tab())
        .findFirst()
        .orElseThrow(() -> new PageContentModificationException("Failed to find tab to insert section into"));

    // create section
    WaUiMetadata section = new WaUiMetadata(this, command, tab.getOrderNbr() + 1);

    // make a spot for the section in the orderNbr list
    incrementAllFrom(tab.getOrderNbr() + 1);

    // Insert entity
    this.uiMetadata.add(section);

    // Sort by orderNbr
    this.uiMetadata.sort(Comparator.comparing(WaUiMetadata::getOrderNbr));

    changed(command);
    return section;
  }

  private void incrementAllFrom(Integer start) {
    this.uiMetadata.forEach(ui -> {
      if (ui.getOrderNbr() >= start) {
        ui.setOrderNbr(ui.getOrderNbr() + 1);
      }
    });
  }

  public WaUiMetadata updateSection(PageContentCommand.UpdateSection command) {
    // Can only modify Draft pages
    verifyDraftType();

    WaUiMetadata section = uiMetadata.stream()
        .filter(ui -> ui.getId() == command.sectionId() && ui.getNbsUiComponentUid() == SECTION)
        .findFirst()
        .orElseThrow(() -> new PageContentModificationException("Failed to find section to update"));

    section.update(command);
    changed(command);
    return section;
  }

  public void addSubSection(WaUiMetadata subsection) {
    including(subsection);
  }

  public WaUiMetadata addSubSection(PageContentCommand.AddSubsection command) {
    // Can only modify Draft pages
    verifyDraftType();

    // Find the container to insert subsection into
    WaUiMetadata section = uiMetadata.stream()
        .filter(ui -> ui.getId() == command.section() && ui.getNbsUiComponentUid() == SECTION)
        .findFirst()
        .orElseThrow(() -> new PageContentModificationException("Failed to find tab to insert section into"));

    // create subsection
    WaUiMetadata subsection = new WaUiMetadata(this, command, section.getOrderNbr() + 1);

    // make a spot for the subsection in the orderNbr list
    incrementAllFrom(section.getOrderNbr() + 1);

    // Insert entity
    this.uiMetadata.add(subsection);

    // Sort by orderNbr
    this.uiMetadata.sort(Comparator.comparing(WaUiMetadata::getOrderNbr));

    changed(command);
    return subsection;
  }

  public WaUiMetadata updateSubSection(PageContentCommand.UpdateSubsection command) {
    // Can only modify Draft pages
    verifyDraftType();

    WaUiMetadata subsection = uiMetadata.stream()
        .filter(ui -> ui.getId() == command.subsection() && ui.getNbsUiComponentUid() == SUB_SECTION)
        .findFirst()
        .orElseThrow(() -> new PageContentModificationException("Failed to find subsection to update"));

    subsection.update(command);
    changed(command);
    return subsection;
  }

  public void addSubSection(
      final String name,
      final int at,
      final long addedBy,
      final Instant addedOn) {
    WaUiMetadata component = new WaUiMetadata(
        this,
        PageConstants.SUB_SECTION_COMPONENT,
        name,
        at,
        addedBy,
        addedOn);

    including(component);
  }

  public void addContent(
      final String name,
      final long type,
      final int at,
      final long addedBy,
      final Instant addedOn) {
    WaUiMetadata component = new WaUiMetadata(
        this,
        type,
        name,
        at,
        addedBy,
        addedOn);

    including(component);
  }

  public void deleteSection(PageContentCommand.DeleteSection command) {
    // Can only modify Draft pages
    verifyDraftType();

    // Find the section to delete
    WaUiMetadata section = uiMetadata.stream()
        .filter(e -> e.getId() == command.setionId() && e.getNbsUiComponentUid() == SECTION)
        .findFirst()
        .orElseThrow(
            () -> new PageContentModificationException(
                "Failed to find section with id: " + command.setionId()));

    // If element after section is null, another section, or Tab then we can delete the section
    if (!isElementAtOrderNullOrOneOf(Arrays.asList(SECTION, TAB), section.getOrderNbr() + 1)) {
      throw new PageContentModificationException("Unable to delete a section with content");
    }

    // Remove section and adjust orderNbrs
    uiMetadata.remove(section);
    adjustingComponentsFrom(section.getOrderNbr());
    changed(command);
  }

  public void deleteSubsection(PageContentCommand.DeleteSubsection command) {
    // Can only modify Draft pages
    verifyDraftType();

    // Find the subsection to delete
    WaUiMetadata section = uiMetadata.stream()
        .filter(e -> e.getId() == command.subsectionId() && e.getNbsUiComponentUid() == SUB_SECTION)
        .findFirst()
        .orElseThrow(
            () -> new PageContentModificationException(
                "Failed to find subsection with id: " + command.subsectionId()));

    // If element after section is null, another subsection, section, or Tab then we can delete the subsection
    if (!isElementAtOrderNullOrOneOf(Arrays.asList(SUB_SECTION, SECTION, TAB), section.getOrderNbr() + 1)) {
      throw new PageContentModificationException("Unable to delete a subsection with content");
    }

    // Remove subsection and adjust order numbers
    uiMetadata.remove(section);
    adjustingComponentsFrom(section.getOrderNbr());
    changed(command);
  }

  public WaUiMetadata addQuestion(PageContentCommand.AddQuestion command) {
    // Can only modify Draft pages
    verifyDraftType();

    // ensure page doesn't already contain question
    Optional<WaUiMetadata> existing = uiMetadata.stream()
        .filter(e -> e.getQuestionIdentifier().equals(command.question().getQuestionIdentifier()))
        .findFirst();

    if (existing.isPresent()) {
      throw new PageContentModificationException("Unable to add a question to a page multiple times");
    }

    // Find the SubSection to add question to 
    WaUiMetadata subsection = uiMetadata.stream()
        .filter(e -> e.getId() == command.subsection() && e.getNbsUiComponentUid() == SUB_SECTION)
        .findFirst()
        .orElseThrow(
            () -> new PageContentModificationException(
                "Failed to find subsection with id: " + command.subsection()));

    // Questions are inserted at the END of a subsection, so find the next container (or null if subsection is at end)
    Optional<WaUiMetadata> nextContainer = findNextElementOfComponent(
        subsection.getOrderNbr() + 1,
        Arrays.asList(SUB_SECTION, SECTION, TAB));
    Integer orderNumber;
    if (nextContainer.isEmpty()) {
      orderNumber = uiMetadata.stream()
          .mapToInt(WaUiMetadata::getOrderNbr)
          .max()
          .orElseThrow(() -> new PageContentModificationException("Invalid state")) + 1;
    } else {
      orderNumber = nextContainer.get().getOrderNbr();
    }

    // Make room for new question
    incrementAllFrom(orderNumber);

    // Add question
    WaUiMetadata questionEntry = new WaUiMetadata(this, command, orderNumber);

    this.uiMetadata.add(questionEntry);

    this.uiMetadata.sort(Comparator.comparing(WaUiMetadata::getOrderNbr));

    return questionEntry;
  }

  private Optional<WaUiMetadata> findNextElementOfComponent(Integer start, List<Long> componentTypes) {
    return uiMetadata.stream()
        .filter(ui -> ui.getOrderNbr() >= start)
        .filter(ui -> componentTypes.contains(ui.getNbsUiComponentUid()))
        .findFirst();
  }

  private boolean isElementAtOrderNullOrOneOf(List<Long> validComponents, int orderNumber) {
    WaUiMetadata next = uiMetadata.stream()
        .filter(e -> e.getOrderNbr() == orderNumber)
        .findFirst()
        .orElse(null);
    return next == null || validComponents.contains(next.getNbsUiComponentUid());
  }

  private void including(final WaUiMetadata component) {
    this.uiMetadata.add(component);
    adjustingComponentsFrom(component.getOrderNbr());
  }

  private void adjustingComponentsFrom(final int position) {
    AtomicInteger current = new AtomicInteger(position);

    this.uiMetadata.stream()
        .filter(c -> c.getOrderNbr() >= position)
        .sorted(
            Comparator.comparing(WaUiMetadata::getOrderNbr)
                .thenComparing(WaUiMetadata::getLastChgTime, Comparator.reverseOrder()))

        .forEach(c -> c.setOrderNbr(current.getAndIncrement()));
  }

  public PageCondMapping associateCondition(
      final String condition,
      final long associatedBy,
      final Instant associatedOn) {
    PageCondMapping mapping = new PageCondMapping(
        this,
        condition,
        associatedBy,
        associatedOn);

    this.conditionMappings.add(mapping);
    return mapping;
  }

  public void changed(final PageCommand command) {
    setLastChgTime(command.requestedOn());
    setLastChgUserId(command.requester());
  }

  private void changed(final PageContentCommand command) {
    setLastChgTime(command.requestedOn());
    setLastChgUserId(command.userId());
  }

  public void update(final PageCommand.UpdateDetails command) {

    setTemplateNm(command.name());
    setNndEntityIdentifier(command.messageMappingGuide());
    setDescTxt(command.description());

    // If the page is just an initial draft allow update of conditions and Data mart name
    boolean isInitialDraft = getTemplateType().equals(DRAFT) && getPublishVersionNbr() == null;
    if (isInitialDraft) {
      setDatamartNm(command.dataMartName());

      // Remove any conditions not in the conditions list
      getConditionMappings().removeIf(cm -> !command.conditionIds().contains(cm.getConditionCd()));
    }

    // add any conditions not currently mapped
    var existingConditions = getConditionMappings().stream().map(cm -> cm.getConditionCd()).toList();
    var conditionsToAdd = command.conditionIds().stream().filter(c -> !existingConditions.contains(c)).toList();
    conditionsToAdd.forEach(
        conditionCode -> getConditionMappings().add(new PageCondMapping(command, this, conditionCode)));

    changed(command);
  }

  public void updateType(String type) {
    this.templateType = type;
  }

  private void verifyDraftType() {
    if (!DRAFT.equals(templateType)) {
      throw new PageContentModificationException("Unable to modify non Draft page");
    }
  }
}

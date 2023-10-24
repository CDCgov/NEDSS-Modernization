package gov.cdc.nbs.questionbank.entity;

import gov.cdc.nbs.questionbank.page.PageCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import lombok.Getter;
import lombok.Setter;

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
import javax.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@Entity
@Table(name = "WA_template", catalog = "NBS_ODSE")
public class WaTemplate {
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
  })
  private List<WaUiMetadata> uiMetadata;

  public WaTemplate() {
    this.templateType = "Draft";
    this.recordStatusCd = "ACTIVE";
    this.conditionMappings = new HashSet<>();
    this.uiMetadata = new ArrayList<>();
  }

  public WaTemplate(
      final String object,
      final String mappingGuide,
      final String name,
      final long createdBy,
      final Instant createdOn
  ) {
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
        this.addTime
    );

    root.setAddUserId(this.addUserId);
    root.setAddTime(this.addTime);

    root.setLastChgUserId(this.lastChgUserId);
    root.setLastChgTime(this.lastChgTime);

    root.setRecordStatusTime(this.recordStatusTime);

    ArrayList<WaUiMetadata> components = new ArrayList<>();

    components.add(root);

    return components;
  }

  public void add(final PageContentCommand.AddTab add) {
    WaUiMetadata component = new WaUiMetadata(add);
    including(component);
  }

  public void addSection(
      final String name,
      final int at,
      final long addedBy,
      final Instant addedOn
  ) {
    WaUiMetadata component = new WaUiMetadata(
        this,
        PageConstants.SECTION_COMPONENT,
        name,
        at,
        addedBy,
        addedOn
    );

    including(component);
  }

  public void addSubSection(
      final String name,
      final int at,
      final long addedBy,
      final Instant addedOn
  ) {
    WaUiMetadata component = new WaUiMetadata(
        this,
        PageConstants.SUB_SECTION_COMPONENT,
        name,
        at,
        addedBy,
        addedOn
    );

    including(component);
  }

  public void addContent(
      final String name,
      final long type,
      final int at,
      final long addedBy,
      final Instant addedOn
  ) {
    WaUiMetadata component = new WaUiMetadata(
        this,
        type,
        name,
        at,
        addedBy,
        addedOn
    );

    including(component);
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
                .thenComparing(WaUiMetadata::getLastChgTime, Comparator.reverseOrder())
        )

        .forEach(c -> c.setOrderNbr(current.getAndIncrement()));
  }

  public PageCondMapping associateCondition(
      final String condition,
      final long associatedBy,
      final Instant associatedOn
  ) {
    PageCondMapping mapping = new PageCondMapping(
        this,
        condition,
        associatedBy,
        associatedOn
    );

    this.conditionMappings.add(mapping);
    return mapping;
  }

  public void changed(final PageCommand command) {
    setLastChgTime(command.requestedOn());
    setLastChgUserId(command.requester());
  }

  public void update(final PageCommand.UpdateDetails command) {

    setTemplateNm(command.name());
    setNndEntityIdentifier(command.messageMappingGuide());
    setDescTxt(command.description());

    // If the page is just an initial draft allow update of conditions and Data mart name
    boolean isInitialDraft = getTemplateType().equals("Draft") && getPublishVersionNbr() == null;
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
}

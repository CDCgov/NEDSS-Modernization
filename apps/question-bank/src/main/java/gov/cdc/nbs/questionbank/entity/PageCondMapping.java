package gov.cdc.nbs.questionbank.entity;

import gov.cdc.nbs.questionbank.page.PageCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Page_cond_mapping", catalog = "NBS_ODSE")
public class PageCondMapping {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "page_cond_mapping_uid", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wa_template_uid", nullable = false)
  private WaTemplate waTemplateUid;

  @Column(name = "condition_cd", nullable = false, length = 20)
  private String conditionCd;

  @Column(name = "add_time", nullable = false)
  private Instant addTime;

  @Column(name = "add_user_id", nullable = false)
  private Long addUserId;

  @Column(name = "last_chg_time", nullable = false)
  private Instant lastChgTime;

  @Column(name = "last_chg_user_id", nullable = false)
  private Long lastChgUserId;

  PageCondMapping(
      final WaTemplate page,
      final String condition,
      final long associatedBy,
      final Instant associatedOn
  ) {
    this.waTemplateUid =page;
    this.conditionCd = condition;

    this.addUserId = associatedBy;
    this.addTime = associatedOn;

    this.lastChgUserId = associatedBy;
    this.lastChgTime = associatedOn;
  }

  public PageCondMapping(PageCommand command, WaTemplate template, String conditionCode) {
    this.waTemplateUid = template;
    this.conditionCd = conditionCode;

    this.addTime = command.requestedOn();
    this.addUserId = command.requester();
    this.lastChgTime = command.requestedOn();
    this.lastChgUserId = command.requester();
  }

}

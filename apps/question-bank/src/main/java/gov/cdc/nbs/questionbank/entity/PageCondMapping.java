package gov.cdc.nbs.questionbank.entity;

import gov.cdc.nbs.questionbank.page.PageCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  public PageCondMapping(final WaTemplate page, final PageCommand.RelateCondition command) {
    this.waTemplateUid = page;
    this.conditionCd = command.condition();

    this.addUserId = command.requester();
    this.addTime = command.requestedOn();

    this.lastChgUserId = command.requester();
    this.lastChgTime = command.requestedOn();
  }
}

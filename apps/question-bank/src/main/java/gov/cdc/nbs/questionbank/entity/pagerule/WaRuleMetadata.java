package gov.cdc.nbs.questionbank.entity.pagerule;

import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "WA_rule_metadata", catalog = "NBS_ODSE")
public class WaRuleMetadata {
  private static final String ACTIVE = "ACTIVE";

  @Id
  @Column(name = "wa_rule_metadata_uid", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "wa_template_uid", length = 19, nullable = false)
  private Long waTemplateUid;

  @Column(name = "rule_cd", length = 50)
  private String ruleCd;

  @Column(name = "rule_expression", length = 4000)
  private String ruleExpression;

  @Column(name = "err_msg_txt", length = 4000)
  private String errormsgText;

  @Column(name = "source_question_identifier", length = 4000)
  private String sourceQuestionIdentifier;

  @Column(name = "target_question_identifier", length = 4000)
  private String targetQuestionIdentifier;

  @Column(name = "record_status_cd", length = 20)
  private String recordStatusCd;

  @Column(name = "record_status_time")
  private Instant recordStatusTime;

  @Column(name = "add_time")
  private Instant addTime;

  @Column(name = "add_user_id", length = 19)
  private Long addUserId;

  @Column(name = "last_chg_time")
  private Instant lastChgTime;

  @Column(name = "last_chg_user_id", length = 19)
  private Long lastChgUserId;

  @Column(name = "rule_desc_txt", length = 4000)
  private String ruleDescText;

  @Column(name = "javascript_function")
  private String jsFunction;

  @Column(name = "javascript_function_nm", length = 100)
  private String jsFunctionName;

  @Column(name = "user_rule_id", length = 50)
  private String userRuleId;

  @Column(name = "logic", length = 20)
  private String logic;

  @Column(name = "source_values", length = 4000)
  private String sourceValues;

  @Column(name = "target_type", length = 50)
  private String targetType;

  public WaRuleMetadata(PageContentCommand.AddRuleCommand command) {
    this.waTemplateUid = command.page();
    this.targetType = command.targetType();
    this.ruleCd = command.ruleFunction();
    this.ruleDescText = command.description();
    this.logic = command.comparator();
    this.sourceValues = command.sourceValues();
    this.sourceQuestionIdentifier = command.sourceIdentifier();
    this.targetQuestionIdentifier = command.targetIdentifiers();
    this.errormsgText = command.errorMessage();
    this.jsFunction = command.javascript();
    this.jsFunctionName = command.javascriptName() + "()";
    this.waTemplateUid = command.page();
    this.ruleExpression = command.expression();
    this.userRuleId = "Rule" + command.ruleId();
    added(command);
  }

  public void update(PageContentCommand.UpdateRuleCommand command) {
    this.targetType = command.targetType();
    this.ruleDescText = command.description();
    this.logic = command.comparator();
    this.sourceValues = command.sourceValues();
    this.sourceQuestionIdentifier = command.sourceIdentifier();
    this.targetQuestionIdentifier = command.targetIdentifiers();
    this.errormsgText = command.errorMessage();
    this.jsFunction = command.javascript();
    this.jsFunctionName = command.javascriptName();
    this.ruleExpression = command.expression();
    updated(command);
  }

  public WaRuleMetadata(long pageId, PageContentCommand.AddRule command) {
    this.waTemplateUid = pageId;
    this.ruleCd = command.ruleCd();
    this.errormsgText = command.errMsgTxt();
    this.jsFunction = command.javascriptFunction();
    this.jsFunctionName = command.javascriptFunctionNm();
    this.recordStatusCd = command.recordStatusCd();
    added(command);
  }

  private void added(PageContentCommand command) {
    this.recordStatusCd = ACTIVE;
    this.addTime = command.requestedOn();
    this.addUserId = command.userId();
    this.lastChgTime = command.requestedOn();
    this.lastChgUserId = command.userId();
    this.recordStatusTime = command.requestedOn();
  }

  private void updated(PageContentCommand command) {
    this.lastChgTime = command.requestedOn();
    this.lastChgUserId = command.userId();
  }

  public static WaRuleMetadata clone(WaRuleMetadata original) {
    return new WaRuleMetadata(
        null,
        null,
        original.getRuleCd(),
        original.getRuleExpression(),
        original.getErrormsgText(),
        original.getSourceQuestionIdentifier(),
        original.getTargetQuestionIdentifier(),
        original.getRecordStatusCd(),
        original.getRecordStatusTime(),
        original.getAddTime(),
        original.getAddUserId(),
        original.getLastChgTime(),
        original.getLastChgUserId(),
        original.getRuleDescText(),
        original.getJsFunction(),
        original.getJsFunctionName(),
        original.getUserRuleId(),
        original.getLogic(),
        original.getSourceValues(),
        original.getTargetType());
  }
}

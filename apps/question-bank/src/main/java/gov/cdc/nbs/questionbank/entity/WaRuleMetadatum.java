package gov.cdc.nbs.questionbank.entity;

import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WA_rule_metadata", catalog = "NBS_ODSE")
public class WaRuleMetadatum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wa_rule_metadata_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wa_template_uid", nullable = false)
    private WaTemplate waTemplateUid;

    @Column(name = "rule_cd", nullable = false, length = 50)
    private String ruleCd;

    @Column(name = "rule_expression", length = 4000)
    private String ruleExpression;

    @Column(name = "err_msg_txt", nullable = false, length = 4000)
    private String errMsgTxt;

    @Column(name = "source_question_identifier", length = 4000)
    private String sourceQuestionIdentifier;

    @Column(name = "target_question_identifier", length = 4000)
    private String targetQuestionIdentifier;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "rule_desc_txt", length = 4000)
    private String ruleDescTxt;

    @Lob
    @Column(name = "javascript_function", nullable = false, columnDefinition = "TEXT")
    private String javascriptFunction;

    @Column(name = "javascript_function_nm", nullable = false, length = 100)
    private String javascriptFunctionNm;

    @Column(name = "user_rule_id", length = 50)
    private String userRuleId;

    @Column(name = "logic", length = 20)
    private String logic;

    @Column(name = "source_values", length = 4000)
    private String sourceValues;

    @Column(name = "target_type", length = 50)
    private String targetType;

    WaRuleMetadatum (WaTemplate page ,PageContentCommand.AddRule command){
        this.waTemplateUid = page;
        this.ruleCd= command.ruleCd();
        this.errMsgTxt= command.errMsgTxt();
        this.javascriptFunction= command.javascriptFunction();
        this.javascriptFunctionNm= command.javascriptFunctionNm();
        this.recordStatusCd= command.recordStatusCd();
        added(command);
    }
    private void added(PageContentCommand command) {
        this.addTime = command.requestedOn();
        this.addUserId = command.userId();
        this.lastChgTime = command.requestedOn();
        this.lastChgUserId = command.userId();
        this.recordStatusTime = command.requestedOn();

    }

}

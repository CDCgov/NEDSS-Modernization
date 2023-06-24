package gov.cdc.nbs.questionbank.entity.pagerule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WA_rule_metadata", catalog = "NBS_ODSE")
public class WaRuleMetadata {
    @Id
    @Column(name = "wa_rule_metadata_uid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wa_template_uid", length = 19)
    private Long waTemplateUid;

    @Column(name = "rule_cd", length = 50)
    private String ruleCd;

    @Column(name = "rule_expression", length =4000)
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

    @Column(name = "rule_desc_txt", length =4000)
    private String ruleDescText;

    @Column(name = "javascript_function")
    private String jsFunction;

    @Column(name= "javascript_function_nm", length = 100)
    private String jsFunctionName;

    @Column(name = "user_rule_id", length = 50)
    private String userRuleId;

    @Column(name = "logic", length = 20)
    private String logic;

    @Column(name = "source_values", length = 4000)
    private String sourceValues;

    @Column(name = "target_type", length = 50)
    private String targetType;

}

package gov.cdc.nbs.questionbank.entity.pagerule;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.command.RuleCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WA_rule_metadata", catalog = "NBS_ODSE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class WaRuleMetadata {
    @Id
    @Column(name = "wa_rule_metadata_uid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

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

    protected WaRuleMetadata(RuleCommand command){
        RuleCommand.RuleData ruleData = command.ruleData();
        List<String> targetValuesList= ruleData.targetValue();
        String sourceTargetQuestionIdentifiers= String.join(",",targetValuesList);
        setRuleDescText(ruleData.ruleDescription());
        setTargetType(ruleData.targetType());
        setRuleCd(ruleData.ruleFunction());
        setLogic(ruleData.comparator());
        setTargetQuestionIdentifier(sourceTargetQuestionIdentifiers);
        setSourceQuestionIdentifier(ruleData.sourceIdentifier());
        setSourceValues(ruleData.sourceValue());

    }

}

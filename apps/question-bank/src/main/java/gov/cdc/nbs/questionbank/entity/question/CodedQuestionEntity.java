package gov.cdc.nbs.questionbank.entity.question;

import static gov.cdc.nbs.questionbank.question.util.QuestionUtil.requireNonNull;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(CodedQuestionEntity.CODED_QUESTION_TYPE)
public class CodedQuestionEntity extends WaQuestion {
    static final String CODED_QUESTION_TYPE = "CODED";

    @Column(name = "code_set_group_id")
    private Long codeSetGroupId;

    @Column(name = "default_value", length = 300)
    private String defaultValue;

    @Override
    public String getDataType() {
        return CodedQuestionEntity.CODED_QUESTION_TYPE;
    }

    public CodedQuestionEntity(QuestionCommand.AddCodedQuestion command) {
        super(command);

        this.codeSetGroupId = requireNonNull(command.valueSet(), "ValueSet must not be null");
        this.defaultValue = command.defaultValue();
        setOtherValueIndCd('F');

        // Audit
        created(command);

        // Reporting
        setReportingData(command.reportingData());

        // Messaging
        setMessagingData(command.messagingData());
    }

}

package gov.cdc.nbs.questionbank.entity.question;

import static gov.cdc.nbs.questionbank.util.PageBuilderUtil.requireNonNull;

import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue(CodedQuestionEntity.CODED_QUESTION_TYPE)
public class CodedQuestionEntity extends WaQuestion {
  static final String CODED_QUESTION_TYPE = "CODED";

  @Column(name = "default_value", length = 300)
  private String defaultValue;

  @Override
  public String getDataType() {
    return CodedQuestionEntity.CODED_QUESTION_TYPE;
  }

  public CodedQuestionEntity(QuestionCommand.AddCodedQuestion command) {
    super(command);

    this.codeSetGroupId = requireNonNull(command.valueSet(), "ValueSet");
    this.defaultValue = command.defaultValue();
    this.otherValueIndCd = 'F';

    // Audit
    created(command);
    // Do not set reporting or messaging data for readonly user data
    if (command.questionData().displayControl() != 1026) {
      // Reporting
      setReportingData(command);

      // Messaging
      setMessagingData(command.messagingData());
    }
  }

  @Override
  public void update(QuestionCommand.Update command) {
    // Update general fields
    update(command.questionData());

    // Coded fields
    this.codeSetGroupId = requireNonNull(command.valueSet(), "ValueSet");
    this.defaultValue = command.defaultValue();

    // Reporting
    setReportingData(command);

    // Messaging
    setMessagingData(command.messagingData());

    // Audit
    changed(command);
  }
}

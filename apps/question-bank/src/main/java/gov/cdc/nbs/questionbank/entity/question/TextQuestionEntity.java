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
@DiscriminatorValue(TextQuestionEntity.TEXT_QUESION_TYPE)
public class TextQuestionEntity extends WaQuestion {
  static final String TEXT_QUESION_TYPE = "TEXT";

  @Column(name = "mask", length = 50)
  private String mask;

  @Column(name = "field_size", length = 10)
  private String fieldSize;

  @Column(name = "default_value", length = 300)
  private String defaultValue;

  @Override
  public String getDataType() {
    return TextQuestionEntity.TEXT_QUESION_TYPE;
  }

  public TextQuestionEntity(QuestionCommand.AddTextQuestion command) {
    super(command);

    this.defaultValue = command.defaultValue();
    this.mask = requireNonNull(command.mask(), "Mask").toString();
    if (command.fieldLength() != null) {
      this.fieldSize = requireNonNull(command.fieldLength(), "Field size").toString();
    }

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
    update(command.questionData());

    // Text specific fields
    this.defaultValue = command.defaultValue();
    this.mask = requireNonNull(command.mask(), "Mask");
    this.fieldSize = requireNonNull(command.fieldLength(), "Field size");

    // Reporting
    setReportingData(command);

    // Messaging
    setMessagingData(command.messagingData());

    // Audit
    changed(command);
  }
}

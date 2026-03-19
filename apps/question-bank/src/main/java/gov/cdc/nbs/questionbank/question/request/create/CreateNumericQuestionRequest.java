package gov.cdc.nbs.questionbank.question.request.create;

import gov.cdc.nbs.questionbank.question.model.DisplayOption;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNumericQuestionRequest extends CreateQuestionRequest {
  private NumericMask mask;
  private Integer fieldLength;
  private Long defaultValue;
  private Long minValue;
  private Long maxValue;
  private String relatedUnitsLiteral;
  private Long relatedUnitsValueSet;

  public enum NumericDisplayControl {
    USER_ENTERED(1008, "User entered text, number, or date"),
    READONLY_USER_ENTERED(1026, "Readonly User entered text, number, or date"),
    READONLY_USER_NO_SAVE(1029, "Readonly User text, number, or date no save");

    private final long value;
    private final String name;

    NumericDisplayControl(long value, String name) {
      this.value = value;
      this.name = name;
    }

    public static List<DisplayOption> getDisplayOptions() {
      return Arrays.stream(values()).map(NumericDisplayControl::toDisplayOption).toList();
    }

    private DisplayOption toDisplayOption() {
      return new DisplayOption(value, name);
    }
  }
}

package gov.cdc.nbs.questionbank.question.request.create;

import gov.cdc.nbs.questionbank.question.model.DisplayOption;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCodedQuestionRequest extends CreateQuestionRequest {
  private Long valueSet;
  private String defaultValue;

  public enum CodedDisplayControl {
    SINGLE_SELECT(1007, "Single-Select (Drop down)"),
    MULTI_SELECT(1013, "Multi-Select (List Box)"),
    READONLY_SINGLE_SELECT_SAVE(1024, "Readonly single-select save"),
    READONLY_MULTI_SELECT_SAVE(1025, "Readonly multi-select save"),
    READONLY_SINGLE_SELECT_NO_SAVE(1027, "Readonly single-select no save"),
    READONLY_MULTI_SELECT_NO_SAVE(1028, "Readonly multi-select no save"),
    CODE_LOOKUP(1031, "Code Lookup");

    private final long value;
    private final String name;

    CodedDisplayControl(long value, String name) {
      this.value = value;
      this.name = name;
    }

    public static List<DisplayOption> getDisplayOptions() {
      return Arrays.stream(values()).map(CodedDisplayControl::toDisplayOption).toList();
    }

    private DisplayOption toDisplayOption() {
      return new DisplayOption(value, name);
    }
  }
}

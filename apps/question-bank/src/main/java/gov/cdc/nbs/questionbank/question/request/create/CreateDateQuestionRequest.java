package gov.cdc.nbs.questionbank.question.request.create;

import gov.cdc.nbs.questionbank.question.model.DisplayOption;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDateQuestionRequest extends CreateQuestionRequest {
  private DateMask mask;
  private boolean allowFutureDates;

  public enum DateDisplayControl {
    USER_ENTERED(1008, "User entered text, number, or date"),
    READONLY_USER_ENTERED(1026, "Readonly User entered text, number, or date"),
    READONLY_USER_NO_SAVE(1029, "Readonly User text, number, or date no save");
    private final long value;
    private final String name;

    DateDisplayControl(long value, String name) {
      this.value = value;
      this.name = name;
    }

    public static List<DisplayOption> getDisplayOptions() {
      return Arrays.stream(values()).map(DateDisplayControl::toDisplayOption).toList();
    }

    private DisplayOption toDisplayOption() {
      return new DisplayOption(value, name);
    }
  }
}

package gov.cdc.nbs.questionbank.question.request.create;

import gov.cdc.nbs.questionbank.question.model.DisplayOption;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class CreateTextQuestionRequest extends CreateQuestionRequest {
  private TextMask mask;
  private Integer fieldLength;
  private String defaultValue;


  public enum TextMask {
    TXT,
    CENSUS_TRACT,
    TXT_EMAIL,
    TXT_ID10,
    TXT_ID12,
    TXT_ID15,
    TXT_PHONE,
    TXT_SSN,
    TXT_IDTB,
    TXT_ZIP
  }


  public enum TextDisplayControl {
    USER_ENTERED(1008, "User entered text, number, or date"),
    MULTI_LINE_USER_ENTERED(1009, "Multi-line user-entered text"),
    MULTI_LINE_NOTES(1019, "Multi-line Notes with User/Date Stamp"),
    READONLY_USER_ENTERED(1026, "Readonly User entered text, number, or date"),
    READONLY_USER_NO_SAVE(1029, "Readonly User text, number, or date no save");

    private final long value;
    private final String name;

    TextDisplayControl(long value, String name) {
      this.value = value;
      this.name = name;
    }

    public static List<DisplayOption> getDisplayOptions() {
      return Arrays.stream(values()).map(TextDisplayControl::toDisplayOption).toList();
    }

    private DisplayOption toDisplayOption() {
      return new DisplayOption(value, name);
    }

  }
}

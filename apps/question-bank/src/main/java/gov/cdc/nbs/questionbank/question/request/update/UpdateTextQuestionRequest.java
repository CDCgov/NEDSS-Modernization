package gov.cdc.nbs.questionbank.question.request.update;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTextQuestionRequest extends UpdateQuestionRequest {
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
}

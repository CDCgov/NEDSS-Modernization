package gov.cdc.nbs.questionbank.valueset.response;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class CreateValueSetResponse {
  private ValueSetCreateShort body;
  private String message;
  private HttpStatus status;

  public record ValueSetCreateShort(
      CodesetId id, Instant addTime, Long addUserId, String valueSetNm, Long codeSetGroupId) {
    public static ValueSetCreateShort fromResult(Codeset codeset) {
      return new ValueSetCreateShort(
          codeset.getId(),
          codeset.getAddTime(),
          codeset.getAddUserId(),
          codeset.getValueSetNm(),
          codeset.getCodeSetGroup().getId());
    }
  }
}

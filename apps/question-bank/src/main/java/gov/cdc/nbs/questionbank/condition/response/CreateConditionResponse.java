package gov.cdc.nbs.questionbank.condition.response;

import org.springframework.http.HttpStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;

@Data
@NoArgsConstructor
public class CreateConditionResponse {
    private CreateConditionBody body;
    private HttpStatus status;


public record CreateConditionBody (
    String id
) {}

}

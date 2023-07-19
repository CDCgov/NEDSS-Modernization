package gov.cdc.nbs.questionbank.condition.response;

import org.springframework.http.HttpStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateConditionResponse {
    private String id;
    private HttpStatus status;
}

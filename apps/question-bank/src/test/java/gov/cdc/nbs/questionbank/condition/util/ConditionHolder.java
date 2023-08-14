package gov.cdc.nbs.questionbank.condition.util;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ConditionHolder {
    CreateConditionResponse createConditionResponse;
    ConditionCode conditionCode;
}

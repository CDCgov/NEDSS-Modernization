package gov.cdc.nbs.questionbank.condition.util;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ConditionHolder {
    ConditionCode conditionCode;
}

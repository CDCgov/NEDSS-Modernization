package gov.cdc.nbs.questionbank.condition.util;

import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ConditionHolder {
  Condition createConditionResponse;
  Page<Condition> readConditionResponse;
  List<Condition> allConditionsResponse;
  ConditionCode conditionCode;
}

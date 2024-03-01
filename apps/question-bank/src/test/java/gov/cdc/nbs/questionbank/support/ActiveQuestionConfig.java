package gov.cdc.nbs.questionbank.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gov.cdc.nbs.testing.support.Active;

@Configuration
public class ActiveQuestionConfig {

  @Bean
  public Active<QuestionIdentifier> questionIdentifier() {
    return new Active<QuestionIdentifier>();
  }
}

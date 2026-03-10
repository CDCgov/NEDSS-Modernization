package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.testing.support.Active;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveQuestionConfig {

  @Bean
  public Active<QuestionIdentifier> questionIdentifier() {
    return new Active<QuestionIdentifier>();
  }
}

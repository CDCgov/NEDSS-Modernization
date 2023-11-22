package gov.cdc.nbs.questionbank.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.persistence.EntityManager;

@Configuration
class QueryDSLConfig {
  @Bean
  @Primary
  JPAQueryFactory jpaQueryFactory(final EntityManager entityManager) {
    return new JPAQueryFactory(entityManager);
  }


}

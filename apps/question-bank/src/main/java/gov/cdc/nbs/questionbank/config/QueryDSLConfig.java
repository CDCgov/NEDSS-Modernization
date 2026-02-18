package gov.cdc.nbs.questionbank.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class QueryDSLConfig {
  @Bean
  @Primary
  JPAQueryFactory jpaQueryFactory(final EntityManager entityManager) {
    return new JPAQueryFactory(entityManager);
  }
}

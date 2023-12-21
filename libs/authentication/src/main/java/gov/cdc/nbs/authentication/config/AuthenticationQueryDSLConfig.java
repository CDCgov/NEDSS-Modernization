package gov.cdc.nbs.authentication.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
class AuthenticationQueryDSLConfig {

  @Bean("authentication")
  JPAQueryFactory authenticationJpaQueryFactory(final EntityManager entityManager) {
    return new JPAQueryFactory(entityManager);
  }

}

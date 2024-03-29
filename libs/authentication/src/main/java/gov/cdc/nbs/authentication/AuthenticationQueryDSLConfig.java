package gov.cdc.nbs.authentication;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManager;

@Configuration
class AuthenticationQueryDSLConfig {

  @Bean("authentication")
  JPAQueryFactory authenticationJpaQueryFactory(final EntityManager entityManager) {
    return new JPAQueryFactory(entityManager);
  }

}

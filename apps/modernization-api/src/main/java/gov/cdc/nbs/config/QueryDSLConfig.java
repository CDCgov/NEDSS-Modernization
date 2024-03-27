package gov.cdc.nbs.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import jakarta.persistence.EntityManager;

@Configuration
class QueryDSLConfig {

    @Bean
    @Primary
    JPAQueryFactory jpaQueryFactory(final EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

}

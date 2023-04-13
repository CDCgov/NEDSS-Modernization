package gov.cdc.nbs.questionbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;



//Configuration file to initialize Flyway
@Configuration
public class FlywayConfig {

    @Autowired
    private DataSource dataSource;

    @Bean(initMethod = "migrate")
    public Flyway flyway(){
        Flyway flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations("filesystem:./sql")
            .load();
        return flyway;
    }


}
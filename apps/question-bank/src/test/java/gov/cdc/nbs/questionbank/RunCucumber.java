package gov.cdc.nbs.questionbank;

import gov.cdc.nbs.questionbank.container.EmbeddedNbsDatabase;
import gov.cdc.nbs.testing.interaction.http.EnableAuthenticatedInteractions;
import gov.cdc.nbs.testing.support.EnableSupport;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,html:build/reports/tests/test/cucumber-report.html")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "gov.cdc.nbs.questionbank,gov.cdc.nbs.testing.authorization")
@CucumberContextConfiguration
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:19092", "port=19092"})
@Testcontainers
@EmbeddedNbsDatabase
@SpringBootTest
@Transactional
@ActiveProfiles({"default", "test"})
@AutoConfigureMockMvc
@EnableSupport
@EnableAuthenticatedInteractions
class RunCucumber {

}

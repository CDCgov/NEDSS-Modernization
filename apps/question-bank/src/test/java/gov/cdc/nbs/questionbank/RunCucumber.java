package gov.cdc.nbs.questionbank;

import gov.cdc.nbs.testing.classic.interaction.EnableClassicMockRestServer;
import gov.cdc.nbs.testing.database.EmbeddedNbsDatabase;
import gov.cdc.nbs.testing.interaction.http.EnableAuthenticatedInteractions;
import gov.cdc.nbs.testing.support.EnableSupport;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,html:build/reports/tests/test/cucumber-report.html")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "gov.cdc.nbs.questionbank,gov.cdc.nbs.testing.authorization")
@CucumberContextConfiguration
@EmbeddedNbsDatabase
@SpringBootTest
@Transactional
@ActiveProfiles({"default", "test", "local"})
@AutoConfigureMockMvc
@EnableSupport
@EnableAuthenticatedInteractions
@EnableClassicMockRestServer
class RunCucumber {

}

package gov.cdc.nbs.option;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

import gov.cdc.nbs.testing.database.EmbeddedNbsDatabase;
import gov.cdc.nbs.testing.support.EnableSupport;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
    key = PLUGIN_PROPERTY_NAME,
    value = "html:build/reports/tests/test/cucumber-report.html")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "gov.cdc.nbs.option")
@CucumberContextConfiguration
@SpringBootTest(
    classes = OptionTestContext.class,
    properties = {"spring.mvc.pathmatch.matching-strategy=ant_path_matcher"})
@ActiveProfiles({"test", "local"})
@EmbeddedNbsDatabase
@EnableSupport
@AutoConfigureMockMvc
public class RunCucumber {}

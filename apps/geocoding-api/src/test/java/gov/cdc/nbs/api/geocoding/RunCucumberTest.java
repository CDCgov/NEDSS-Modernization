package gov.cdc.nbs.api.geocoding;

import io.cucumber.spring.CucumberContextConfiguration;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("gov/cdc/nbs")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "html:build/reports/tests/test/cucumber-report.html")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "gov.cdc.nbs")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features")
@CucumberContextConfiguration
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Rollback(false)
@Testcontainers
class RunCucumberTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void context_loads() {
        assertNotNull(context);
    }
}

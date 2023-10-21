package gov.cdc.nbs.concept;

import gov.cdc.nbs.testing.database.EmbeddedNbsDatabase;
import gov.cdc.nbs.testing.support.EnableSupport;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@CucumberOptions(
    glue = "gov.cdc.nbs.concept",
    features = "src/test/resources/features",
    plugin = {"pretty", "html:build/reports/tests/test/cucumber-report.html"}
)
@CucumberContextConfiguration
@SpringBootTest(
    classes = ConceptTestContext.class,
    properties = {"spring.mvc.pathmatch.matching-strategy=ant_path_matcher"}
)
@EmbeddedNbsDatabase
@EnableSupport
@AutoConfigureMockMvc
public class RunCucumber {


}

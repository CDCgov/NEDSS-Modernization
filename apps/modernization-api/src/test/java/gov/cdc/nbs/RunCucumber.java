package gov.cdc.nbs;

import gov.cdc.nbs.containers.NbsElasticsearchContainer;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierGeneratorTestConfiguration;
import gov.cdc.nbs.testing.classic.interaction.EnableClassicMockRestServer;
import gov.cdc.nbs.testing.database.EmbeddedNbsDatabase;
import io.cucumber.junit.platform.engine.Constants;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
    key = Constants.PLUGIN_PROPERTY_NAME,
    value = "html:build/reports/tests/test/cucumber-report.html")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "gov.cdc.nbs")
@ConfigurationParameter(
    key = Constants.FEATURES_PROPERTY_NAME,
    value = "src/test/resources/features")
@CucumberContextConfiguration
@SpringBootTest(classes = Application.class)
@Import(PatientLocalIdentifierGeneratorTestConfiguration.class)
@ActiveProfiles({"default", "test", "local"})
@AutoConfigureMockMvc
@EmbeddedNbsDatabase
@EnableClassicMockRestServer
public class RunCucumber {

  @Container public static final NbsElasticsearchContainer ELASTICSEARCH_CONTAINER;

  static {
    // instantiate docker once for all tests and test the instance itself in
    // ElasticSearchTest
    ELASTICSEARCH_CONTAINER = new NbsElasticsearchContainer();
    try {
      ELASTICSEARCH_CONTAINER.startWithPlugins();
    } catch (Exception e) {
      throw new RuntimeException("Failed to start NbsElasticsearchContainer with plugins");
    }
  }

  @DynamicPropertySource
  public static void overrideProps(DynamicPropertyRegistry registry) {
    registry.add(
        "nbs.elasticsearch.url", () -> "http://" + ELASTICSEARCH_CONTAINER.getHttpHostAddress());
  }
}

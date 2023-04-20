package gov.cdc.nbs;

import gov.cdc.nbs.containers.NbsElasticsearchContainer;
import gov.cdc.nbs.containers.database.EmbeddedNbsDatabase;
import gov.cdc.nbs.patient.PatientController;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierGeneratorTestConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Test;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.transaction.Transactional;

import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("gov/cdc/nbs")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "html:build/reports/tests/test/cucumber-report.html")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "gov.cdc.nbs")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features")
@CucumberContextConfiguration
@SpringBootTest
@Import(PatientLocalIdentifierGeneratorTestConfiguration.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@Rollback(false)
@Testcontainers
@EmbeddedNbsDatabase
@EmbeddedKafka(
    partitions = 1,
    topics = {"patient"}
)
public class RunCucumberTest {
    @Autowired
    private PatientController patientController;

    @Test
    public void contextLoads() {
        assertNotNull(patientController);
    }

    @Container
    public static final NbsElasticsearchContainer ELASTICSEARCH_CONTAINER;

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
        registry.add("nbs.elasticsearch.url", () -> "http://" + ELASTICSEARCH_CONTAINER.getHttpHostAddress());
    }

}

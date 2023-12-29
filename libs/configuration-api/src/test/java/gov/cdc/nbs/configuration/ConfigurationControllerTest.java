package gov.cdc.nbs.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.configuration.Configuration.Features;
import gov.cdc.nbs.configuration.Configuration.Features.Address;
import gov.cdc.nbs.configuration.Configuration.Features.PageBuilder;
import gov.cdc.nbs.configuration.Configuration.Features.PageBuilder.Page;
import gov.cdc.nbs.configuration.Configuration.Features.PageBuilder.Page.Library;
import gov.cdc.nbs.configuration.Configuration.Features.PageBuilder.Page.Management;
import gov.cdc.nbs.configuration.Configuration.Features.PageBuilder.Page.Management.Create;
import gov.cdc.nbs.configuration.Configuration.Features.PageBuilder.Page.Management.Edit;

@ExtendWith(MockitoExtension.class)
class ConfigurationControllerTest {

  ObjectMapper mapper = new ObjectMapper();

  Configuration configuration =
      new Configuration(
          new Features(
              new Address(true, true),
              new PageBuilder(true,
                  new Page(
                      new Library(true),
                      new Management(
                          true,
                          new Create(true),
                          new Edit(true))))));


  ConfigurationController controller = new ConfigurationController(configuration);

  @Test
  void should_return_proper_configuration() throws JsonProcessingException {
    Configuration config = controller.getConfiguration();

    String expected =
        """
            {
              "features" : {
                "address" : {
                  "autocomplete" : true,
                  "verification" : true
                },
                "pageBuilder" : {
                  "enabled" : true,
                  "page" : {
                    "library" : {
                      "enabled" : true
                    },
                    "management" : {
                      "enabled" : true,
                      "create" : {
                        "enabled" : true
                      },
                      "edit" : {
                        "enabled" : true
                      }
                    }
                  }
                }
              }
            }""";
    String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
    assertThat(json).isEqualTo(expected);
  }
}

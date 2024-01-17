package gov.cdc.nbs.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.configuration.Features.Address;
import gov.cdc.nbs.configuration.Features.PageBuilder;
import gov.cdc.nbs.configuration.Features.PageBuilder.Page;
import gov.cdc.nbs.configuration.Features.PageBuilder.Page.Library;
import gov.cdc.nbs.configuration.Features.PageBuilder.Page.Management;
import gov.cdc.nbs.configuration.Features.PageBuilder.Page.Management.Create;
import gov.cdc.nbs.configuration.Features.PageBuilder.Page.Management.Edit;

@ExtendWith(MockitoExtension.class)
class ConfigurationControllerTest {

  ObjectMapper mapper = new ObjectMapper();

  @Spy
  private Features features = new Features(
      new Address(true, true),
      new PageBuilder(true,
          new Page(
              new Library(true),
              new Management(
                  new Create(true),
                  new Edit(true)))));

  @Mock
  private NbsConfigurationFinder finder;

  @InjectMocks
  private ConfigurationController controller;


  @Test
  void should_return_proper_configuration() throws JsonProcessingException {
    Map<String, String> configs = new HashMap<>();
    configs.put("key1", "value1");
    when(finder.find()).thenReturn(configs);

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
                      "create" : {
                        "enabled" : true
                      },
                      "edit" : {
                        "enabled" : true
                      }
                    }
                  }
                }
              },
              "configuration" : {
                "key1" : "value1"
              }
            }""";
    String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
    assertThat(json).isEqualTo(expected);
  }
}

package gov.cdc.nbs.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collections;
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
import gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder;
import gov.cdc.nbs.configuration.nbs.Properties;

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
  private NbsPropertiesFinder finder;

  @InjectMocks
  private ConfigurationController controller;


  @Test
  void should_return_proper_configuration() throws JsonProcessingException {
    Properties properties = new Properties(Arrays.asList("STD"), Arrays.asList("HIV"),
        Collections.singletonMap("CODE_BASE", "Release 6.0.15-Beta"));
    when(finder.find()).thenReturn(properties);

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
              "properties" : {
                "stdProgramAreas" : [ "STD" ],
                "hivProgramAreas" : [ "HIV" ],
                "entries" : {
                  "CODE_BASE" : "Release 6.0.15-Beta"
                }
              }
            }""";
    String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
    assertThat(json).isEqualTo(expected);
  }
}

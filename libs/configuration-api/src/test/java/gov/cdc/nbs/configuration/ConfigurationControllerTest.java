package gov.cdc.nbs.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.configuration.Configuration.Ui;
import gov.cdc.nbs.configuration.Configuration.Ui.Features;
import gov.cdc.nbs.configuration.Configuration.Ui.Features.Address;
import gov.cdc.nbs.configuration.Configuration.Ui.Features.PageBuilder;

@ExtendWith(MockitoExtension.class)
class ConfigurationControllerTest {

    @Spy
    Configuration configuration =
            new Configuration(new Ui(
                    new Features(
                            new Address(true, true),
                            new PageBuilder(true))));

    @InjectMocks
    ConfigurationController controller;

    @Test
    void should_return_proper_configuration() {
        Configuration config = controller.getConfiguration();

        assertEquals(config, configuration);
    }
}

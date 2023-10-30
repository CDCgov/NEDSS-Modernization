package gov.cdc.nbs.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.configuration.Configuration.Features;
import gov.cdc.nbs.configuration.Configuration.Features.Address;
import gov.cdc.nbs.configuration.Configuration.Features.PageBuilder;

@ExtendWith(MockitoExtension.class)
class ConfigurationControllerTest {

    @Spy
    Configuration configuration =
            new Configuration(
                    new Features(
                            new Address(true, true),
                            new PageBuilder(true)));

    @InjectMocks
    ConfigurationController controller;

    @Test
    void should_return_proper_configuration() {
        Configuration config = controller.getConfiguration();

        assertEquals(config, configuration);
        assertEquals(config.features().address().autocomplete(), configuration.features().address().autocomplete());
        assertEquals(config.features().address().verification(), configuration.features().address().verification());
        assertEquals(config.features().pageBuilder().enabled(), configuration.features().pageBuilder().enabled());
    }
}

package gov.cdc.nbs.configuration.features.system.management;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class SystemManagementFeatureConfiguration {

    @Bean
    @Scope("prototype")
    SystemManagement systemManagementFeature(
            @Value("${nbs.ui.features.system-management.enabled:false}") final boolean enabled) {
        return new SystemManagement(enabled);
    }
}

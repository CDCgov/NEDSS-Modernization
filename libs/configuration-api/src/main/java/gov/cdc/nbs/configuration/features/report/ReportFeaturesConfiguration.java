package gov.cdc.nbs.configuration.features.report;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class ReportFeaturesConfiguration {

    @Bean
    @Scope("prototype")
    Report reportFeatures(
            final Report.Execution execution) {
        return new Report(execution);
    }

    @Bean
    @Scope("prototype")
    Report.Execution reportExecutionFeature(
            @Value("${nbs.ui.features.report.execution.enabled:false}") final boolean enabled) {
        return new Report.Execution(enabled);
    }
}

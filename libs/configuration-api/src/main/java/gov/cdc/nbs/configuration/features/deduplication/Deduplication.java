package gov.cdc.nbs.configuration.features.deduplication;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nbs.ui.features.deduplication")
public record Deduplication(boolean enabled) {

}

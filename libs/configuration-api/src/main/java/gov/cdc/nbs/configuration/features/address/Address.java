package gov.cdc.nbs.configuration.features.address;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nbs.ui.features.address")
public record Address(boolean autocomplete, boolean verification) {}

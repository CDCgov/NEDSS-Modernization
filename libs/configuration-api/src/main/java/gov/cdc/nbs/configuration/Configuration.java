package gov.cdc.nbs.configuration;

import gov.cdc.nbs.configuration.features.Features;
import gov.cdc.nbs.configuration.nbs.Properties;
import gov.cdc.nbs.configuration.settings.Settings;

public record Configuration(Features features, Settings settings, Properties properties) {}

package gov.cdc.nbs.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("nbs.ui")
public record Configuration(Features features) {

    public record Features(Address address, PageBuilder pageBuilder) {

        public record Address(boolean autocomplete, boolean verification) {
        }

        public record PageBuilder(boolean enabled) {
        }
    }

}

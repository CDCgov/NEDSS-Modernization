package gov.cdc.nbs.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("nbs")
public record Configuration(Ui ui) {

    public record Ui(Features features) {

        public record Features(Address address, PageBuilder pageBuilder) {

            public record Address(boolean autocomplete, boolean verification) {
            }

            public record PageBuilder(boolean enabled) {
            }
        }
    }
}

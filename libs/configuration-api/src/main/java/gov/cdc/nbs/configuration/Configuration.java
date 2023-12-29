package gov.cdc.nbs.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("nbs.ui")
public record Configuration(Features features) {

  public record Features(Address address, PageBuilder pageBuilder) {

    public record Address(boolean autocomplete, boolean verification) {
    }

    public record PageBuilder(boolean enabled, Page page) {

      public record Page(Library library, Management management) {
        public record Library(boolean enabled) {
        }
        public record Management(boolean enabled, Create create, Edit edit) {

          public record Create(boolean enabled) {
          }
          public record Edit(boolean enabled) {
          }
        }
      }
    }
  }

}

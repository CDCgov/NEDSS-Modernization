package gov.cdc.nbs.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("nbs.ui.features")
public record Features(Address address, PageBuilder pageBuilder, Welcome welcome) {

  public record Address(boolean autocomplete, boolean verification) {
  }

  public record Welcome(boolean enabled) {
  }

  public record PageBuilder(boolean enabled, Page page) {

    public record Page(Library library, Management management) {

      public record Library(boolean enabled) {
      }
      public record Management(Create create, Edit edit) {

        public record Create(boolean enabled) {
        }
        public record Edit(boolean enabled) {
        }
      }
    }
  }
}

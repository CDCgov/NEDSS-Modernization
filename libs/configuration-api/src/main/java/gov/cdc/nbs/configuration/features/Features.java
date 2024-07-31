package gov.cdc.nbs.configuration.features;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("nbs.ui.features")
public record Features(Search search, Address address, PageBuilder pageBuilder) {

  public record Address(boolean autocomplete, boolean verification) {
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

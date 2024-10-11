package gov.cdc.nbs.configuration.features.page_builder;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nbs.ui.features.pagebuilder")
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

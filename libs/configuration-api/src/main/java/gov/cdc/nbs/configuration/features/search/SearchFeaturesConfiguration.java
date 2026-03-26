package gov.cdc.nbs.configuration.features.search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class SearchFeaturesConfiguration {

  @Bean
  @Scope("prototype")
  Search searchFeatures(
      final Search.View view,
      final Search.Events events,
      final Search.Investigations investigations,
      final Search.LaboratoryReports laboratoryReports) {
    return new Search(view, events, investigations, laboratoryReports);
  }

  @Bean
  @Scope("prototype")
  Search.View.Table searchViewTableFeature(
      @Value("${nbs.ui.features.search.view.table.enabled:true}") final boolean enabled) {
    return new Search.View.Table(enabled);
  }

  @Bean
  @Scope("prototype")
  Search.View searchViewFeature(
      @Value("${nbs.ui.features.search.view.enabled:true}") final boolean enabled,
      final Search.View.Table table) {
    return new Search.View(enabled, table);
  }

  @Bean
  @Scope("prototype")
  Search.Events searchEventsFeature(
      @Value("${nbs.ui.features.search.events.enabled:true}") final boolean enabled) {
    return new Search.Events(enabled);
  }

  @Bean
  @Scope("prototype")
  Search.Investigations searchInvestigationsFeature(
      @Value("${nbs.ui.features.search.investigations.enabled:false}") final boolean enabled) {
    return new Search.Investigations(enabled);
  }

  @Bean
  @Scope("prototype")
  Search.LaboratoryReports searchLaboratoryReportsFeature(
      @Value("${nbs.ui.features.search.laboratory-reports.enabled:false}") final boolean enabled) {
    return new Search.LaboratoryReports(enabled);
  }
}

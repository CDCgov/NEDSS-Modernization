package gov.cdc.nbs.questionbank.page.template;

import gov.cdc.nbs.questionbank.RequestContext;
import gov.cdc.nbs.questionbank.page.PageCommand;
import gov.cdc.nbs.questionbank.page.PageService;
import gov.cdc.nbs.questionbank.page.TemplateNameVerifier;
import org.springframework.stereotype.Component;

@Component
class PageTemplateCreator {

  private final PageService service;
  private final TemplateNameVerifier verifier;
  private final ClassicPageTemplateCreator classicCreator;

  PageTemplateCreator(
      final PageService service,
      final TemplateNameVerifier verifier,
      final ClassicPageTemplateCreator classicCreator) {
    this.service = service;
    this.verifier = verifier;
    this.classicCreator = classicCreator;
  }

  void create(final RequestContext context, final long page, final CreateTemplateRequest request) {

    PageCommand.CreateTemplate command =
        new PageCommand.CreateTemplate(
            request.name(), request.description(), context.requestedBy(), context.requestedAt());

    this.service.using(page, found -> found.createTemplate(verifier, command));

    classicCreator.create(page, request);
  }
}

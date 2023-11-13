package gov.cdc.nbs.questionbank.page.template;

record CreateTemplateRequest(String name, String description) {

  CreateTemplateRequest() {
    this(null, null);
  }

  CreateTemplateRequest withName(final String name) {
    return new CreateTemplateRequest(
        name,
        description()
    );
  }

  CreateTemplateRequest withDescription(final String description) {
    return new CreateTemplateRequest(
        name(),
        description
    );
  }
}

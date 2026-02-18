package gov.cdc.nbs.questionbank.page.template;

import io.swagger.v3.oas.annotations.media.Schema;

record CreateTemplateRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String description) {

  CreateTemplateRequest() {
    this(null, null);
  }

  CreateTemplateRequest withName(final String name) {
    return new CreateTemplateRequest(name, description());
  }

  CreateTemplateRequest withDescription(final String description) {
    return new CreateTemplateRequest(name(), description);
  }
}

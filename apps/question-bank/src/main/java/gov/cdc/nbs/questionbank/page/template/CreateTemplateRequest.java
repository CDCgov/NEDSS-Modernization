package gov.cdc.nbs.questionbank.page.template;

import io.swagger.annotations.ApiModelProperty;

record CreateTemplateRequest(
    @ApiModelProperty(required = true)
    String name,
    @ApiModelProperty(required = true)
    String description
) {

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

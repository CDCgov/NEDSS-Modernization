package gov.cdc.nbs.option.language.primary;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PrimaryLanguageOptionRequester {

  private final MockMvc mvc;

  PrimaryLanguageOptionRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions request() throws Exception {
    return mvc.perform(get("/nbs/api/options/languages/primary"));
  }
}

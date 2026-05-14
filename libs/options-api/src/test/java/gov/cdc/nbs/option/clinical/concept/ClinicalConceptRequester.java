package gov.cdc.nbs.option.clinical.concept;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class ClinicalConceptRequester {

  private final MockMvc mvc;

  ClinicalConceptRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions request(final String valueSet) throws Exception {
    return mvc.perform(get("/nbs/api/options/clinical/concepts/{valueSet}", valueSet))
        .andDo(print());
  }
}

package gov.cdc.nbs.option.race.detailed.autocomplete;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
class DetailedRaceAutocompleteRequester {

  private final MockMvc mvc;

  DetailedRaceAutocompleteRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions complete(final String category, final String criteria) throws Exception {
    return mvc.perform(
        get("/nbs/api/options/races/{category}/search", category)
            .param("criteria", criteria)
    );
  }

  ResultActions complete(final String category, final String criteria, final int limit) throws Exception {
    return mvc.perform(
        get("/nbs/api/options/races/{category}/search", category)
            .param("criteria", criteria)
            .param("limit", String.valueOf(limit))
    );
  }
}

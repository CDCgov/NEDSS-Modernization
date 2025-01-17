package gov.cdc.nbs.option.race.detailed;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
class DetailedRaceRequester {

  private final MockMvc mvc;

  DetailedRaceRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions request(final String category) throws Exception {
    return mvc.perform(
        get("/nbs/api/options/races/{category}", category)
    );
  }

}

package gov.cdc.nbs.option;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
public class AutocompleteRequester {

  private final MockMvc mvc;

  AutocompleteRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  public ResultActions complete(final String name, final String criteria) throws Exception {
    return mvc.perform(
        get("/nbs/api/options/{name}/search", name)
            .param("criteria", criteria))
        .andDo(print());
  }

  public ResultActions complete(final String name, final String criteria, final int limit) throws Exception {
    return mvc.perform(
        get("/nbs/api/options/{name}/search", name)
            .param("criteria", criteria)
            .param("limit", String.valueOf(limit)))
        .andDo(print());
  }

  public ResultActions complete(final String name, final String criteria, String root, final int limit)
      throws Exception {
    return mvc.perform(
        get("/nbs/api/options/{name}/{root}/search", name, root)
            .param("criteria", criteria)
            .param("limit", String.valueOf(limit)))
        .andDo(print());
  }

  public ResultActions complete(final String name, final String criteria, String root)
      throws Exception {
    return mvc.perform(
        get("/nbs/api/options/{name}/{root}/search", name, root)
            .param("criteria", criteria))
        .andDo(print());
  }
}

package gov.cdc.nbs.patient.file;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientHeaderRequester {

  private final MockMvc mvc;

  PatientHeaderRequester(final MockMvc mvc) {
    this.mvc = mvc;
  }

  ResultActions request(long patientId) throws Exception {
    return mvc.perform(
        get("nbs/api/patient/" + String.valueOf(patientId) + "/file")).andDo(print());
  }
}

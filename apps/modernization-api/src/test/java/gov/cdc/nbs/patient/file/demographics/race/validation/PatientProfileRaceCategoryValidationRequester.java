package gov.cdc.nbs.patient.file.demographics.race.validation;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientProfileRaceCategoryValidationRequester {
  private final Authenticated authenticated;
  private final MockMvc mvc;

  PatientProfileRaceCategoryValidationRequester(
      final Authenticated authenticated,
      final MockMvc mvc
  ) {
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions validate(final PatientIdentifier patient, final String category) {
    try {
      return mvc.perform(
              this.authenticated.withUser(
                  post(
                      "/nbs/api/patients/{patient}/demographics/races/{category}/validate",
                      patient.id(),
                      category
                  )
              )
          )
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to execute Patient Profile Race Category Validation request", exception);
    }

  }
}

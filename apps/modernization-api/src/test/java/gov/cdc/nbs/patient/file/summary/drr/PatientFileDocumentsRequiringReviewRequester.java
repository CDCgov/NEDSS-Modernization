package gov.cdc.nbs.patient.file.summary.drr;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientFileDocumentsRequiringReviewRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;

  PatientFileDocumentsRequiringReviewRequester(
      final MockMvc mvc,
      final Authenticated authenticated
  ) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions request(final PatientIdentifier patient) {
    try {
      return mvc.perform(
          this.authenticated.withUser(
              get("/nbs/api/patients/{patient}/documents-requiring-review", patient.id())
          )
      ).andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when viewing the Documents Requiring Review for the Patient file.",
          exception
      );
    }
  }
}

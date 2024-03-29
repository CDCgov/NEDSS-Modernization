package gov.cdc.nbs.patient.profile.race.validate.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("nbs/api/patients/{patient}/demographics/race")
class PatientRaceCategoryValidationController {

  private final ExistingRaceCategoryFinder finder;

  PatientRaceCategoryValidationController(final ExistingRaceCategoryFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "validateCategory",
      summary = "Validates that a patient can accept a race demographic for the given category.",
      tags = "PatientRace",
      responses = {
          @ApiResponse(responseCode = "200", description = "Allowable race category for the patient"),
          @ApiResponse(
              responseCode = "400",
              description = "The race category is already present on the patient",
              content = {
                  @Content(
                      mediaType = MediaType.APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = ExistingRaceCategory.class)
                  )
              }
          )
      }
  )
  @PostMapping("categories/{category}/validate")
  ResponseEntity<ExistingRaceCategory> validate(
      @PathVariable final long patient,
      @PathVariable final String category
  ) {
    return this.finder.find(patient, category)
        .map(this::invalid)
        .orElseGet(this::valid);
  }

  private ResponseEntity<ExistingRaceCategory> valid() {
    return ResponseEntity.ok().build();
  }

  private ResponseEntity<ExistingRaceCategory> invalid(final ExistingRaceCategory existing) {
    return ResponseEntity
        .badRequest()
        .body(existing);
  }

}

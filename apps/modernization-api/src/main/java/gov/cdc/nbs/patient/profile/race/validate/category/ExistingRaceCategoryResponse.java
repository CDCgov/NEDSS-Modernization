package gov.cdc.nbs.patient.profile.race.validate.category;

sealed interface ExistingRaceCategoryResponse {
  record ExistingRaceCategoryValid() implements ExistingRaceCategoryResponse{}

  record ExistingRaceCategoryInvalid(String identifier, String description) implements ExistingRaceCategoryResponse{}
}

package gov.cdc.nbs.patient.file.demographics.race.validation;

sealed interface ExistingRaceCategoryResponse {
  record ExistingRaceCategoryValid() implements ExistingRaceCategoryResponse{}

  record ExistingRaceCategoryInvalid(String identifier, String description) implements ExistingRaceCategoryResponse{}
}

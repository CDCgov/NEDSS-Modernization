package gov.cdc.nbs.deduplication.model;

import java.util.List;

public record PatientData(Bundle bundle, String external_person_id) {

  public record Bundle(String resourceType, List<Entry> entry) {

    public record Entry(Resource resource) {

      public record Resource(
          String resourceType,
          List<Name> name,
          List<Address> address,
          List<Identifier> identifier,
          String birthDate) {

        public record Name(
            String family,
            List<String> given) {
        }

        public record Address(
            List<String> line,
            String city,
            String state,
            String postalCode,
            String country) {
        }

        public record Identifier(
            String system,
            String value,
            IdentifierType type) {

          public record IdentifierType(
              List<Coding> coding,
              String text) {

            public record Coding(
                String system,
                String code,
                String display) {
            }

          }
        }
      }
    }
  }
}

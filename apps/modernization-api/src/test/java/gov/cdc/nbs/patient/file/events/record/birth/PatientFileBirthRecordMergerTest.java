package gov.cdc.nbs.patient.file.events.record.birth;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PatientFileBirthRecordMergerTest {

  @Test
  void should_return_the_current_mother_when_there_is_nothing_to_merge_with() {


    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(null, null);

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, null);

    assertThat(actual).isSameAs(current);

  }

  @Test
  void should_return_the_next_mother_when_when_there_is_nothing_to_merge_with() {

    PatientFileBirthRecord.MotherInformation current = null;
    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(null, null);

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual).isSameAs(next);

  }

  // name
  @Test
  void should_return_the_current_name_when_there_is_nothing_to_merge_with() {

    PatientFileBirthRecord.MotherInformation.Name name =
        new PatientFileBirthRecord.MotherInformation.Name(null, null, null, null);

    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(name, null);
    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(null, null);

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.name()).isSameAs(name);

  }

  @Test
  void should_return_the_next_name_when_when_there_is_nothing_to_merge_with() {

    PatientFileBirthRecord.MotherInformation.Name name =
        new PatientFileBirthRecord.MotherInformation.Name(null, null, null, null);

    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(null, null);
    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(name, null);

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.name()).isSameAs(name);

  }

  @Test
  void should_merge_name_using_the_first_name_from_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            "first-name-value",
            null,
            null,
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            "next-first-name-value",
            null,
            null,
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.name())
        .returns("first-name-value", PatientFileBirthRecord.MotherInformation.Name::first);
  }

  @Test
  void should_merge_name_using_the_first_name_from_next_when_not_present_in_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            null,
            null,
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            "next-first-name-value",
            null,
            null,
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.name())
        .returns("next-first-name-value", PatientFileBirthRecord.MotherInformation.Name::first);
  }

  @Test
  void should_merge_name_using_the_middle_name_from_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            "middle-name-value",
            null,
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            "next-middle-name-value",
            null,
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.name())
        .returns("middle-name-value", PatientFileBirthRecord.MotherInformation.Name::middle);
  }

  @Test
  void should_merge_name_using_the_middle_name_from_next_when_not_present_in_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            null,
            null,
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            "next-middle-name-value",
            null,
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.name())
        .returns("next-middle-name-value", PatientFileBirthRecord.MotherInformation.Name::middle);
  }

  @Test
  void should_merge_name_using_the_last_name_from_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            null,
            "last-name-value",
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            null,
            "next-last-name-value",
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.name())
        .returns("last-name-value", PatientFileBirthRecord.MotherInformation.Name::last);
  }

  @Test
  void should_merge_name_using_the_last_name_from_next_when_not_present_in_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            null,
            null,
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            null,
            "next-last-name-value",
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.name())
        .returns("next-last-name-value", PatientFileBirthRecord.MotherInformation.Name::last);
  }

  @Test
  void should_merge_name_using_the_suffix_from_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            null,
            null,
            "suffix-name-value"
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            null,
            null,
            "next-suffix-name-value"
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.name())
        .returns("suffix-name-value", PatientFileBirthRecord.MotherInformation.Name::suffix);
  }

  @Test
  void should_merge_name_using_the_suffix_from_next_when_not_present_in_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            null,
            null,
            null
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        new PatientFileBirthRecord.MotherInformation.Name(
            null,
            null,
            null,
            "next-suffix-name-value"
        ),
        null
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.name())
        .returns("next-suffix-name-value", PatientFileBirthRecord.MotherInformation.Name::suffix);
  }

  // address
  @Test
  void should_return_the_current_address_when_there_is_nothing_to_merge_with() {

    PatientFileBirthRecord.MotherInformation.Address address =
        new PatientFileBirthRecord.MotherInformation.Address(null, null, null, null, null, null);

    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(null, address);
    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(null, null);

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address()).isSameAs(address);

  }

  @Test
  void should_return_the_next_address_when_when_there_is_nothing_to_merge_with() {

    PatientFileBirthRecord.MotherInformation.Address address =
        new PatientFileBirthRecord.MotherInformation.Address(null, null, null, null, null, null);

    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(null, null);
    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(null, address);

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address()).isSameAs(address);

  }

  @Test
  void should_merge_address_using_the_street_address_from_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            "street-address-value",
            null,
            null,
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            "next-street-address-value",
            null,
            null,
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address())
        .returns("street-address-value", PatientFileBirthRecord.MotherInformation.Address::address);
  }

  @Test
  void should_merge_address_using_the_street_address_from_next_when_not_present_in_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            "next-street-address-value",
            null,
            null,
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address())
        .returns("next-street-address-value", PatientFileBirthRecord.MotherInformation.Address::address);
  }

  @Test
  void should_merge_address_using_the_street_address_2_from_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            "street-address-2-value",
            null,
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            "next-street-address-2-value",
            null,
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address())
        .returns("street-address-2-value", PatientFileBirthRecord.MotherInformation.Address::address2);
  }

  @Test
  void should_merge_address_using_the_street_address_2_from_next_when_not_present_in_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            "next-street-address-2-value",
            null,
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address())
        .returns("next-street-address-2-value", PatientFileBirthRecord.MotherInformation.Address::address2);
  }

  @Test
  void should_merge_address_using_the_city_from_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            "city-value",
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            "next-city-value",
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address())
        .returns("city-value", PatientFileBirthRecord.MotherInformation.Address::city);
  }

  @Test
  void should_merge_address_using_the_city_from_next_when_not_present_in_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            "next-city-value",
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address())
        .returns("next-city-value", PatientFileBirthRecord.MotherInformation.Address::city);
  }

  @Test
  void should_merge_address_using_the_state_from_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            "state-value",
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            "next-state-value",
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address())
        .returns("state-value", PatientFileBirthRecord.MotherInformation.Address::state);
  }

  @Test
  void should_merge_address_using_the_state_from_next_when_not_present_in_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            "next-state-value",
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address())
        .returns("next-state-value", PatientFileBirthRecord.MotherInformation.Address::state);
  }

  @Test
  void should_merge_address_using_the_county_from_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            null,
            "county-value",
            null
        )
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            null,
            "next-county-value",
            null
        )
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address())
        .returns("county-value", PatientFileBirthRecord.MotherInformation.Address::county);
  }

  @Test
  void should_merge_address_using_the_county_from_next_when_not_present_in_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            null,
            "next-county-value",
            null
        )
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address())
        .returns("next-county-value", PatientFileBirthRecord.MotherInformation.Address::county);
  }

  @Test
  void should_merge_address_using_the_zipcode_from_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            null,
            null,
            "zipcode-value"
        )
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            null,
            null,
            "next-zipcode-value"
        )
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address())
        .returns("zipcode-value", PatientFileBirthRecord.MotherInformation.Address::zipcode);
  }

  @Test
  void should_merge_address_using_the_zipcode_from_next_when_not_present_in_current() {
    PatientFileBirthRecord.MotherInformation current = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            null,
            null,
            null
        )
    );

    PatientFileBirthRecord.MotherInformation next = new PatientFileBirthRecord.MotherInformation(
        null,
        new PatientFileBirthRecord.MotherInformation.Address(
            null,
            null,
            null,
            null,
            null,
            "next-zipcode-value"
        )
    );

    PatientFileBirthRecord.MotherInformation actual = PatientFileBirthRecordMerger.merge(current, next);

    assertThat(actual.address())
        .returns("next-zipcode-value", PatientFileBirthRecord.MotherInformation.Address::zipcode);
  }
}

package gov.cdc.nbs.patient.file.events.record.birth;

class PatientFileBirthRecordMerger {

  private PatientFileBirthRecordMerger() {}

  static PatientFileBirthRecord merge(
      final PatientFileBirthRecord current, final PatientFileBirthRecord next) {

    PatientFileBirthRecord.MotherInformation mother = merge(current.mother(), next.mother());

    return current.withMother(mother);
  }

  static PatientFileBirthRecord.MotherInformation merge(
      final PatientFileBirthRecord.MotherInformation current,
      final PatientFileBirthRecord.MotherInformation next) {
    if (current == null) {
      return next;
    } else if (next == null) {
      return current;
    }

    PatientFileBirthRecord.MotherInformation.Name name = merge(current.name(), next.name());
    PatientFileBirthRecord.MotherInformation.Address address =
        merge(current.address(), next.address());

    return new PatientFileBirthRecord.MotherInformation(name, address);
  }

  private static PatientFileBirthRecord.MotherInformation.Name merge(
      final PatientFileBirthRecord.MotherInformation.Name current,
      final PatientFileBirthRecord.MotherInformation.Name next) {
    if (current == null) {
      return next;
    } else if (next == null) {
      return current;
    }

    String first = coalesce(current.first(), next.first());
    String middle = coalesce(current.middle(), next.middle());
    String last = coalesce(current.last(), next.last());
    String suffix = coalesce(current.suffix(), next.suffix());

    return new PatientFileBirthRecord.MotherInformation.Name(first, middle, last, suffix);
  }

  private static PatientFileBirthRecord.MotherInformation.Address merge(
      final PatientFileBirthRecord.MotherInformation.Address current,
      final PatientFileBirthRecord.MotherInformation.Address next) {
    if (current == null) {
      return next;
    } else if (next == null) {
      return current;
    }

    String address = coalesce(current.address(), next.address());
    String address2 = coalesce(current.address2(), next.address2());
    String city = coalesce(current.city(), next.city());
    String state = coalesce(current.state(), next.state());
    String county = coalesce(current.county(), next.county());
    String zipcode = coalesce(current.zipcode(), next.zipcode());

    return new PatientFileBirthRecord.MotherInformation.Address(
        address, address2, city, state, county, zipcode);
  }

  private static String coalesce(final String first, final String second) {
    return first != null ? first : second;
  }
}

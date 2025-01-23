package gov.cdc.nbs.patient.profile;

public record PatientProfile(long id, String local, short version, String status) {

  public static String STATUS_ACTIVE = "ACTIVE";
  public static String STATUS_INACTIVE = "INACTIVE";
  public static String STATUS_SUPERSEDED = "SUPERSEDED";

  public PatientProfile(long id, String local, short version) {
    this(id, local, version, STATUS_ACTIVE);
  }

}

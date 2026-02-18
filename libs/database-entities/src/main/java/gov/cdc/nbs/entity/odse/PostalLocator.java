package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Postal_locator")
public class PostalLocator extends Locator {

  private static final String YES = "Y";

  @Id
  @Column(name = "postal_locator_uid", nullable = false, updatable = false)
  private Long id;

  @Column(name = "census_block_cd", length = 20)
  private String censusBlockCd;

  @Column(name = "census_minor_civil_division_cd", length = 20)
  private String censusMinorCivilDivisionCd;

  @Column(name = "census_track_cd", length = 20)
  private String censusTrackCd;

  @Column(name = "city_cd", length = 20)
  private String cityCd;

  @Column(name = "city_desc_txt", length = 100)
  private String city;

  @Column(name = "cntry_cd", length = 20)
  private String country;

  @Column(name = "cntry_desc_txt", length = 100)
  private String cntryDescTxt;

  @Column(name = "cnty_cd", length = 20)
  private String county;

  @Column(name = "cnty_desc_txt", length = 100)
  private String cntyDescTxt;

  @Column(name = "MSA_congress_district_cd", length = 20)
  private String msaCongressDistrictCd;

  @Column(name = "region_district_cd", length = 20)
  private String regionDistrictCd;

  @Column(name = "state_cd", length = 20)
  private String state;

  @Column(name = "street_addr1", length = 100)
  private String streetAddr1;

  @Column(name = "street_addr2", length = 100)
  private String streetAddr2;

  @Column(name = "user_affiliation_txt", length = 20)
  private String userAffiliationTxt;

  @Column(name = "zip_cd", length = 20)
  private String zipCd;

  @Column(name = "geocode_match_ind", length = 1)
  private String geocodeMatchInd;

  @Column(name = "within_city_limits_ind", length = 3)
  private String withinCityLimitsInd;

  @Column(name = "census_tract", length = 10)
  private String censusTract;

  protected PostalLocator() {}

  public PostalLocator(
      final PatientCommand.AddAddress address, final EntityLocatorParticipationId identifier) {
    super(address);
    this.id = identifier.getLocatorUid();
    this.streetAddr1 = address.address1();
    this.streetAddr2 = address.address2();
    this.city = address.city();
    this.state = address.state();
    this.zipCd = address.zip();
    this.county = address.county();
    this.country = address.country();
    this.censusTract = address.censusTract();
  }

  PostalLocator(final long identifier, final PatientCommand.UpdateBirth birth) {
    super(birth);

    this.id = identifier;
  }

  PostalLocator(final long identifier, final PatientCommand.UpdateMortality mortality) {
    super(mortality);

    this.id = identifier;
  }

  public void update(final PatientCommand.UpdateBirth birth) {

    long before = Objects.hash(this.city, this.state, this.county, this.country);

    this.city = birth.city();
    this.state = birth.state();
    this.county = birth.county();
    this.country = birth.country();

    long after = Objects.hash(this.city, this.state, this.county, this.country);

    if (before != after) {
      changed(birth);
    }
  }

  public void update(final PatientCommand.UpdateMortality mortality) {

    long before = Objects.hash(this.city, this.state, this.county, this.country);

    if (Objects.equals(mortality.deceased(), YES)) {
      this.city = mortality.city();
      this.state = mortality.state();
      this.county = mortality.county();
      this.country = mortality.country();
    } else {
      this.city = null;
      this.state = null;
      this.county = null;
      this.country = null;
    }

    long after = Objects.hash(this.city, this.state, this.county, this.country);

    if (before != after) {
      changed(mortality);
    }
  }

  public void update(final PatientCommand.UpdateAddress update) {
    this.streetAddr1 = update.address1();
    this.streetAddr2 = update.address2();
    this.city = update.city();
    this.state = update.state();
    this.zipCd = update.zip();
    this.county = update.county();
    this.country = update.country();
    this.censusTract = update.censusTract();

    changed(update);
  }

  public Long identifier() {
    return id;
  }

  public String address1() {
    return streetAddr1;
  }

  public String address2() {
    return streetAddr2;
  }

  public String city() {
    return city;
  }

  public String county() {
    return county;
  }

  public String zip() {
    return zipCd;
  }

  public String state() {
    return state;
  }

  public String country() {
    return country;
  }

  public String censusTract() {
    return censusTract;
  }

  @Override
  public String toString() {
    return "PostalLocator{" + "id=" + id + '}';
  }
}

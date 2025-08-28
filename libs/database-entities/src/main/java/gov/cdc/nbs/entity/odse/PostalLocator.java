package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.patient.PatientCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "Postal_locator")
public class PostalLocator extends Locator {

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
  private String cityDescTxt;

  @Column(name = "cntry_cd", length = 20)
  private String cntryCd;

  @Column(name = "cntry_desc_txt", length = 100)
  private String cntryDescTxt;

  @Column(name = "cnty_cd", length = 20)
  private String cntyCd;

  @Column(name = "cnty_desc_txt", length = 100)
  private String cntyDescTxt;

  @Column(name = "MSA_congress_district_cd", length = 20)
  private String msaCongressDistrictCd;

  @Column(name = "region_district_cd", length = 20)
  private String regionDistrictCd;

  @Column(name = "state_cd", length = 20)
  private String stateCd;

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

  protected PostalLocator() {

  }

  public PostalLocator(final PatientCommand.AddAddress address, final EntityLocatorParticipationId identifier) {
    super(address);
    this.id = identifier.getLocatorUid();
    this.streetAddr1 = address.address1();
    this.streetAddr2 = address.address2();
    this.cityDescTxt = address.city();
    this.stateCd = address.state();
    this.zipCd = address.zip();
    this.cntyCd = address.county();
    this.cntryCd = address.country();
    this.censusTract = address.censusTract();
  }

  PostalLocator(
      final long identifier,
      final PatientCommand.UpdateBirth birth) {
    super(birth);

    this.id = identifier;
  }

  PostalLocator(
      final long identifier,
      final PatientCommand.UpdateMortality mortality) {
    super(mortality);

    this.id = identifier;
  }

  public void update(final PatientCommand.UpdateBirth birth) {
    this.cityDescTxt = birth.city();
    this.stateCd = birth.state();
    this.cntyCd = birth.county();
    this.cntryCd = birth.country();

    changed(birth);
  }

  public void update(final PatientCommand.UpdateMortality mortality) {
    if (Objects.equals(mortality.deceased(), Deceased.Y.value())) {
      this.cityDescTxt = mortality.city();
      this.stateCd = mortality.state();
      this.cntyCd = mortality.county();
      this.cntryCd = mortality.country();
    } else {
      this.cityDescTxt = null;
      this.stateCd = null;
      this.cntyCd = null;
      this.cntryCd = null;
    }

    changed(mortality);
  }

  public void update(final PatientCommand.UpdateAddress update) {
    this.streetAddr1 = update.address1();
    this.streetAddr2 = update.address2();
    this.cityDescTxt = update.city();
    this.stateCd = update.state();
    this.zipCd = update.zip();
    this.cntyCd = update.county();
    this.cntryCd = update.country();
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
    return cityDescTxt;
  }

  public String county() {
    return cntyCd;
  }

  public String zip() {
    return zipCd;
  }

  public String state() {
    return stateCd;
  }

  public String country() {
    return cntryCd;
  }

  public String censusTract() {
    return censusTract;
  }



  @Override
  public String toString() {
    return "PostalLocator{" +
        "id=" + id +
        '}';
  }

}

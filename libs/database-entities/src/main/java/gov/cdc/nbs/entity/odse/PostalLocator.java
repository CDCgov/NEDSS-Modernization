package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.address.City;
import gov.cdc.nbs.address.Country;
import gov.cdc.nbs.address.County;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientCommand.AddMortalityLocator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Getter
@Setter
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

    public PostalLocator() {

    }

    public PostalLocator(final PatientCommand.AddAddress address) {
        super(address);
        this.id = address.id();
        this.streetAddr1 = address.address1();
        this.streetAddr2 = address.address2();
        applyCity(address.city());
        this.stateCd = address.state();
        this.zipCd = address.zip();
        applyCounty(address.county());
        applyCountry(address.country());
        this.censusTract = address.censusTract();
    }


    public PostalLocator(AddMortalityLocator add) {
        super(add);
        this.id = add.id();
        this.cityDescTxt = add.cityOfDeath();
        this.cntryCd = add.countryOfDeath();
        this.cntyCd = add.countyOfDeath();
        this.stateCd = add.stateOfDeath();
    }

    private void applyCity(final City city) {
        if (city != null) {
            this.cityCd = city.code();
            this.cityDescTxt = city.description();
        }
    }

    private void applyCounty(final County county) {
        if (county != null) {
            this.cntyCd = county.code();
            this.cntyDescTxt = county.description();
        }
    }

    private void applyCountry(final Country county) {
        if (county != null) {
            this.cntryCd = county.code();
            this.cntryDescTxt = county.description();
        }
    }

    @Override
    public String toString() {
        return "PostalLocator{" +
                "id=" + id +
                '}';
    }
}

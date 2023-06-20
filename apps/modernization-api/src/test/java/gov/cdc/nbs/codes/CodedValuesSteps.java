package gov.cdc.nbs.codes;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CodedValuesSteps {

    @Autowired
    GeneralCodedValueResolver generalResolver;

    @Autowired
    PrimaryOccupationResolver occupationResolver;

    @Autowired
    PrimaryLanguageResolver languageResolver;

    @Autowired
    DetailedRacesResolver detailedRacesResolver;

    @Autowired
    GenderCodedValueResolver genderResolver;

    @Autowired
    CountryCodedValueResolver countryResolver;

    @Autowired
    StateCodedValueResolver stateResolver;

    @Autowired
    CountyCodedValueResolver countyResolver;

    private Collection<CodedValue> codedValues;
    private Collection<GroupedCodedValue> groupedCodedValues;

    @Before("@coded-values")
    public void reset() {
        this.codedValues = null;
        this.groupedCodedValues = null;
    }

    @When("I want to retrieve the {string} value set")
    public void i_want_to_retrieve_the_named_value_set(String set) {

        switch (set) {
            case "Marital Status" -> this.codedValues = generalResolver.maritalStatuses();
            case "Highest Level of Education" -> this.codedValues = generalResolver.educationLevels();
            case "Race (Category)" -> this.codedValues = generalResolver.raceCategories();
            case "Ethnicity" -> this.codedValues = generalResolver.ethnicGroups();
            case "Spanish Origin (detailed ethnicity)" -> this.codedValues = generalResolver.detailedEthnicities();
            case "Ethnicity Reason Unknown" -> this.codedValues = generalResolver.ethnicityUnknownReasons();
            case "Gender Reason Unknown" -> this.codedValues = generalResolver.genderUnknownReasons();
            case "Transgender (preferred gender)" -> this.codedValues = generalResolver.preferredGenders();
            case "Primary Occupation" -> this.codedValues = this.occupationResolver.primaryOccupations();
            case "Primary Language" -> this.codedValues = this.languageResolver.primaryLanguages();
            case "Name Type" -> this.codedValues = generalResolver.nameTypes();
            case "Name Prefix" -> this.codedValues = generalResolver.prefixes();
            case "Name Suffix" -> this.codedValues = generalResolver.suffixes();
            case "Degree" -> this.codedValues = generalResolver.degrees();
            case "Address Type" -> this.codedValues = generalResolver.addressTypes();
            case "Address Use" -> this.codedValues = generalResolver.addressUses();
            case "Phone Type" -> this.codedValues = generalResolver.phoneTypes();
            case "Phone Use" -> this.codedValues = generalResolver.phoneUses();
            case "Gender" -> this.codedValues = this.genderResolver.genders();
            case "Country" -> this.codedValues = this.countryResolver.countries();
            case "State" -> this.codedValues = this.stateResolver.states();
            default -> this.codedValues = List.of();
        }

    }

    @Then("a value set is returned")
    public void a_value_set_is_returned() {
        assertThat(codedValues).isNotEmpty();
    }

    @When("I want to retrieve the {string} value set by {string}")
    public void i_want_to_retrieve_the_named_value_set_by_group(String set, String group) {
        switch (set) {
            case "Detailed Race" -> this.groupedCodedValues = this.detailedRacesResolver.detailedRaces(group);
            case "County" -> this.groupedCodedValues = this.countyResolver.counties(group);
            default -> this.groupedCodedValues = List.of();
        }
    }

    @Then("a grouped value set is returned")
    public void a_grouped_value_set_is_returned() {
        assertThat(groupedCodedValues).isNotEmpty();
    }

}

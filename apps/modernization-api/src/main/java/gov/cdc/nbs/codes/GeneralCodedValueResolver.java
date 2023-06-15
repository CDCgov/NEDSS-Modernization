package gov.cdc.nbs.codes;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class GeneralCodedValueResolver {

    private final GeneralCodedValueFinder finder;

    public GeneralCodedValueResolver(final GeneralCodedValueFinder finder) {
        this.finder = finder;
    }

    @QueryMapping
    public Collection<CodedValue> maritalStatuses() {
        return finder.all("P_MARITAL", CodedValues.withStandardizedName());
    }

    @QueryMapping
    public Collection<CodedValue> educationLevels() {
        return finder.all("P_EDUC_LVL", CodedValues.withStandardizedName());
    }

    @QueryMapping
    public Collection<CodedValue> raceCategories() {
        return finder.all("RACE_CALCULATED");
    }

    @QueryMapping
    public Collection<CodedValue> ethnicGroups() {
        return finder.all("PHVS_ETHNICITYGROUP_CDC_UNK", CodedValues.withStandardizedName());
    }

    @QueryMapping
    public Collection<CodedValue> detailedEthnicities() {
        return finder.all("P_ETHN");
    }

    @QueryMapping
    public Collection<CodedValue> ethnicityUnknownReasons() {
        return finder.all("P_ETHN_UNK_REASON", CodedValues.withStandardizedName());
    }

    @QueryMapping
    public Collection<CodedValue> genderUnknownReasons() {
        return finder.all("SEX_UNK_REASON", CodedValues.withStandardizedName());
    }

    @QueryMapping
    public Collection<CodedValue> preferredGenders() {
        return finder.all("NBS_STD_GENDER_PARPT");
    }
}

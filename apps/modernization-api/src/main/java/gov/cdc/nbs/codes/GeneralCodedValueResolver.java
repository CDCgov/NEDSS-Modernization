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
        return finder.all("P_EDUC_LVL", CodedValues.withEducationName());
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

    @QueryMapping
    public Collection<CodedValue> nameTypes() {
        return finder.all("P_NM_USE", CodedValues.withStandardizedName());
    }

    @QueryMapping
    public Collection<CodedValue> prefixes() {
        return finder.all("P_NM_PFX");
    }

    @QueryMapping
    public Collection<CodedValue> suffixes() {
        return finder.all("P_NM_SFX");
    }

    @QueryMapping
    public Collection<CodedValue> degrees() {
        return finder.all("P_NM_DEG");
    }

    @QueryMapping
    public Collection<CodedValue> addressTypes() {
        return finder.all("EL_TYPE_PST_PAT", CodedValues.withStandardizedName());
    }

    @QueryMapping
    public Collection<CodedValue> addressUses() {
        return finder.all("EL_USE_PST_PAT", CodedValues.withStandardizedName());
    }

    @QueryMapping
    public Collection<CodedValue> phoneTypes() {
        return finder.all("EL_TYPE_TELE_PAT", CodedValues.withStandardizedName());
    }

    @QueryMapping
    public Collection<CodedValue> phoneUses() {
        return finder.all("EL_USE_TELE_PAT", CodedValues.withStandardizedName());
    }

    @QueryMapping
    public Collection<CodedValue> identificationTypes() {
        return finder.all("EI_TYPE_PAT");
    }

    @QueryMapping
    public Collection<CodedValue> assigningAuthorities() {
        return finder.all("EI_AUTH_PAT");
    }
}

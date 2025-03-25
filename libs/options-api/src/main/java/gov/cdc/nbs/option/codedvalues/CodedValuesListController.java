package gov.cdc.nbs.option.codedvalues;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.option.Option;

@RestController
@RequestMapping("/nbs/api/options/codedvalues/")
public class CodedValuesListController {
  private final CodedValuesListFinder finder;

  public CodedValuesListController(final CodedValuesListFinder finder) {
    this.finder = finder;
  }

  @GetMapping("addressTypes")
  public Collection<Option> addressTypes() {
    return finder.find("EL_TYPE_PST_PAT");
  }

  @GetMapping("addressUses")
  public Collection<Option> addressUses() {
    return finder.find("EL_USE_PST_PAT");
  }

  @GetMapping("assigningAuthorities")
  public Collection<Option> assigningAuthorities() {
    return finder.find("EI_AUTH_PAT");
  }

  @GetMapping("degrees")
  public Collection<Option> degrees() {
    return finder.find("P_NM_DEG");
  }

  @GetMapping("detailedEthnicities")
  public Collection<Option> detailedEthnicities() {
    return finder.find("P_ETHN");
  }

  @GetMapping("educationLevels")
  public Collection<Option> educationLevels() {
    return finder.find("P_EDUC_LVL");
  }

  @GetMapping("ethnicGroups")
  public Collection<Option> ethnicGroups() {
    return finder.find("PHVS_ETHNICITYGROUP_CDC_UNK");
  }

  @GetMapping("ethnicityUnknownReasons")
  public Collection<Option> ethnicityUnknownReasons() {
    return finder.find("P_ETHN_UNK_REASON");
  }

  @GetMapping("genderUnknownReasons")
  public Collection<Option> genderUnknownReasons() {
    return finder.find("SEX_UNK_REASON");
  }

  @GetMapping("maritalStatuses")
  public Collection<Option> maritalStatuses() {
    return finder.find("P_MARITAL");
  }


  @GetMapping("identificationTypes")
  public Collection<Option> identificationTypes() {
    return finder.find("EI_TYPE_PAT");
  }


  @GetMapping("nameTypes")
  public Collection<Option> nameTypes() {
    return finder.find("P_NM_USE");
  }

  @GetMapping("phoneTypes")
  public Collection<Option> phoneTypes() {
    return finder.find("EL_TYPE_TELE_PAT");
  }


  @GetMapping("phoneUses")
  public Collection<Option> phoneUses() {
    return finder.find("EL_USE_TELE_PAT");
  }

  @GetMapping("prefixes")
  public Collection<Option> prefixes() {
    return finder.find("P_NM_PFX");
  }

  @GetMapping("preferredGenders")
  public Collection<Option> preferredGenders() {
    return finder.find("NBS_STD_GENDER_PARPT");
  }

  @GetMapping("raceCategories")
  public Collection<Option> raceCategories() {
    return finder.find("RACE_CALCULATED");
  }


  @GetMapping("suffixes")
  public Collection<Option> suffixes() {
    return finder.find("P_NM_SFX");
  }

}

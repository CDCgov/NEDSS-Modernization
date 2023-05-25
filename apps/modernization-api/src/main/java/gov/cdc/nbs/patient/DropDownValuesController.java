package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.srte.NaicsIndustryCode;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Indicator;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.repository.NaicsIndustryCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class DropDownValuesController {

    private final NaicsIndustryCodeRepository naicsIndustryCodeRepository;


    @QueryMapping
    public KeyValuePairResults findNameSuffixes() {
        List<KeyValuePair> keyValuePairList = Arrays.stream(Suffix.values())
                .map(value -> KeyValuePair.builder()
                        .key(value.name())
                        .value(value.display())
                        .build())
                .toList();
        return KeyValuePairResults.builder()
                .content(keyValuePairList)
                .total(keyValuePairList.size())
                .build();
    }

    @QueryMapping
    public KeyValuePairResults findGenders() {
        List<KeyValuePair> keyValuePairList = Arrays.stream(Gender.values())
                .map(value -> KeyValuePair.builder()
                        .key(value.name())
                        .value(value.display())
                        .build())
                .toList();
        return KeyValuePairResults.builder()
                .content(keyValuePairList)
                .total(keyValuePairList.size())
                .build();
    }

    @QueryMapping
    public KeyValuePairResults findYesNoUnk() {
        List<KeyValuePair> keyValuePairList = Arrays.stream(Indicator.values())
                .map(value -> KeyValuePair.builder()
                        .key(value.name())
                        .value(value.getDescription())
                        .build())
                .toList();
        return KeyValuePairResults.builder()
                .content(keyValuePairList)
                .total(keyValuePairList.size())
                .build();
    }

    @QueryMapping
    public KeyValuePairResults findNameType() {
        List<KeyValuePair> keyValuePairList = Arrays.stream(Indicator.values())
                .map(value -> KeyValuePair.builder()
                        .key(value.name())
                        .value(value.getDescription())
                        .build())
                .toList();
        return KeyValuePairResults.builder()
                .content(keyValuePairList)
                .total(keyValuePairList.size())
                .build();
    }

    public NaicsIndustryCodeResults findAllNaicsIndustryCodes() {
        List<NaicsIndustryCode> naicsIndustryCodeRepositoryAll = naicsIndustryCodeRepository.findAll();
        return NaicsIndustryCodeResults.builder()
                .total(naicsIndustryCodeRepositoryAll.size())
                .content(naicsIndustryCodeRepositoryAll)
                .build();
    }
}

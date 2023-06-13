package gov.cdc.nbs.codes;

import gov.cdc.nbs.message.enums.Gender;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
public class GenderCodedValueResolver {

    @QueryMapping
    public List<CodedValue> genders() {
        return Arrays.stream(Gender.values())
            .map(this::map)
            .toList();
    }

    private CodedValue map(final Gender gender) {
        return new CodedValue(gender.value(), gender.display());
    }
}

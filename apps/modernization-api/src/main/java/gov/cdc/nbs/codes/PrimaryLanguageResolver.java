package gov.cdc.nbs.codes;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class PrimaryLanguageResolver {

    private final PrimaryLanguageValueSetFinder finder;

    public PrimaryLanguageResolver(final PrimaryLanguageValueSetFinder finder) {
        this.finder = finder;
    }

    @QueryMapping
    public Collection<CodedValue> primaryLanguages() {
        return finder.all();
    }

}

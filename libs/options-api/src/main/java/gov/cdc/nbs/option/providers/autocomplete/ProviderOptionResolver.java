package gov.cdc.nbs.option.providers.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProviderOptionResolver extends SQLBasedOptionResolver {

    private static final String QUERY = """
            with [user]([value], [name]) as (
                select
                    person_uid,
                    ISNULL(first_nm + ' ', '') + ISNULL(last_nm, '')
                from Person
                where
                    cd='PRV'
            )
            select
                [value],
                [name],
                row_number() over( order by [name])
            from [user]
            where [name] like :criteria

            order by
                [name]

            offset 0 rows
            fetch next :limit rows only
            """;

    public ProviderOptionResolver(final NamedParameterJdbcTemplate template) {
        super(QUERY, template);
    }
}

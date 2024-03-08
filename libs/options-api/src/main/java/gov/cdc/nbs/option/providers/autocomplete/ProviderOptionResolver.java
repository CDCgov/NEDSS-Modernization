package gov.cdc.nbs.option.providers.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProviderOptionResolver extends SQLBasedOptionResolver {

    private static final String QUERY = """
            with [user]([value], [name], [quickCode]) AS (
                select
                    person_uid,
                    ISNULL(first_nm + ' ', '') + ISNULL(last_nm, ''),
                    root_extension_txt
                from Person
                left join Entity_id on entity_uid=person_uid and type_cd='QEC'
                where
                    cd='PRV'
            )
            select
                [value],
                [name],
                row_number() over( order by [name])
            from [user]
            where [quickCode]=:quickCode or [name] like :criteria or [name] like :prefixCriteria

            order by
                [name]

            offset 0 rows
            fetch next :limit rows only
            """;

    public ProviderOptionResolver(final NamedParameterJdbcTemplate template) {
        super(QUERY, template);
    }
}

package gov.cdc.nbs.option.facilities.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FacilityOptionResolver extends SQLBasedOptionResolver {

    private static final String QUERY = """
            with [facility]([value], [name]) as (
                select
                    organization_uid,
                    display_nm
                from Organization
            )
            select
                [value],
                [name],
                row_number() over( order by [name])
            from [facility]
            where [name] like :criteria

            order by
                [name]

            offset 0 rows
            fetch next :limit rows only
            """;

    public FacilityOptionResolver(final NamedParameterJdbcTemplate template) {
        super(QUERY, template);
    }
}

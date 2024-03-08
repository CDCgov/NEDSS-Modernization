package gov.cdc.nbs.option.facilities.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FacilityOptionResolver extends SQLBasedOptionResolver {

    private static final String QUERY = """
            with [facility]([value], [name], [quickCode]) as (
                select
                    organization_uid,
                    display_nm,
                    root_extension_txt
                from Organization
                left join Entity_id on entity_uid=organization_uid and type_cd='QEC'
            )
            select
                [value],
                [name],
                row_number() over( order by [name])
            from [facility]
            where [quickCode]=:quickCode or [name] like :criteria or [name] like :prefixCriteria

            order by
                [name]

            offset 0 rows
            fetch next :limit rows only
            """;

    public FacilityOptionResolver(final NamedParameterJdbcTemplate template) {
        super(QUERY, template);
    }
}

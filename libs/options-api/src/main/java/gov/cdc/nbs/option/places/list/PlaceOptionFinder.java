package gov.cdc.nbs.option.places.list;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class PlaceOptionFinder extends SQLBasedOptionFinder {

  private static final String QUERY =
      """
      select 
          root_extension_txt                                 as [value], 
          nm                                                 as [name],
          row_number() over(order by [root_extension_txt])   as [order]
      from NBS_ODSE..Place  p
      inner join NBS_ODSE..Entity_id ei on 
          p.place_uid = ei.entity_uid 
          and ei.type_cd='QEC' 
          and ei.root_extension_txt is not null 
          and ei.root_extension_txt != ''
      order by
          root_extension_txt
      """;

  PlaceOptionFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }
}

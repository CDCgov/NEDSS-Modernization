package gov.cdc.nbs.entity.elasticsearch;

import gov.cdc.nbs.entity.enums.converter.ElasticsearchInstantValueConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.ValueConverter;

import java.time.Instant;

import static gov.cdc.nbs.util.Constants.DATE_PATTERN;

@Getter
@Setter
@Builder
public class AssociatedInvestigation {

  @Field(name = "public_health_case_uid", type = FieldType.Long)
  private Long publicHealthCaseUid;

  @Field(name = "cd_desc_txt", type = FieldType.Keyword)
  private String cdDescTxt;

  @Field(name = "local_id", type = FieldType.Keyword)
  private String localId;

  @Field(name = "last_change_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant lastChgTime;

  @Field(name = "act_relationship_last_change_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant actRelationshipLastChgTime;

}

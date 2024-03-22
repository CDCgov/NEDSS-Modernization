package gov.cdc.nbs.entity.elasticsearch;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Builder
public class ElasticsearchOrganizationParticipation {
  public static final String TYPE_CD = "type_cd";
  public static final String ENTITY_ID = "entity_id";



  @Field(name = "type_cd", type = FieldType.Keyword)
  private String typeCd;

  @Field(name = "entity_id", type = FieldType.Long)
  private Long entityId;

}

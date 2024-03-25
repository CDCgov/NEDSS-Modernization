package gov.cdc.nbs.entity.elasticsearch;

import gov.cdc.nbs.entity.enums.converter.ElasticsearchInstantValueConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.ValueConverter;

import java.time.Instant;

import static gov.cdc.nbs.util.Constants.DATE_PATTERN;

@Getter
@Setter
@Builder
public class ElasticsearchPersonParticipation {
  public static final String TYPE_CD = "type_cd";
  public static final String ENTITY_ID = "entity_id";
  public static final String SUBJECT_CLASS_CD = "subject_class_cd";
  public static final String LAST_NAME = "last_name";
  public static final String BIRTH_TIME = "birth_time";
  public static final String PERSON_PARENT_UID = "person_parent_uid";



  @Field(name = "type_cd", type = FieldType.Keyword)
  private String typeCd;

  @Field(name = "entity_id", type = FieldType.Long)
  private Long entityId;

  @Field(name = "local_id", type = FieldType.Keyword)
  private String localId;

  @Field(name = "subject_class_cd", type = FieldType.Keyword)
  private String subjectClassCd;

  @Field(name = "first_name", type = FieldType.Text)
  private String firstName;

  // allows sorting
  @MultiField(mainField = @Field(name = "last_name", type = FieldType.Text), otherFields = {
      @InnerField(suffix = "keyword", type = FieldType.Keyword, normalizer = "lowercase")
  })
  private String lastName;

  @Field(name = "birth_time", type = FieldType.Date, format = {}, pattern = DATE_PATTERN)
  @ValueConverter(ElasticsearchInstantValueConverter.class)
  private Instant birthTime;

  @Field(name = "curr_sex_cd", type = FieldType.Keyword)
  private String currSexCd;

  @Field(name = "person_cd", type = FieldType.Keyword)
  private String personCd;

  @Field(name = "person_parent_uid", type = FieldType.Long)
  private Long personParentUid;

}

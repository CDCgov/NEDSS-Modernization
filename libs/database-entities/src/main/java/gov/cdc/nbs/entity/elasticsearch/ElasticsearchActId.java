package gov.cdc.nbs.entity.elasticsearch;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Builder
public class ElasticsearchActId {
  public static final String ACT_ID_SEQ = "act_id_seq";
  public static final String ROOT_EXTENSION_TXT = "root_extension_txt";
  public static final String TYPE_CD = "type_cd";


  @Field(name = "act_id_seq", type = FieldType.Integer)
  private Integer actIdSeq;

  @Field(name = "root_extension_txt", type = FieldType.Keyword)
  private String rootExtensionTxt;

  @Field(name = "type_cd", type = FieldType.Keyword)
  private String typeCd;

}

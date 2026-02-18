package gov.cdc.nbs.questionbank.page;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageMetaDataDownloader {

  private final JdbcTemplate jdbcTemplate;
  private static final String PAGE_NAME = "page_nm";
  private static final String ORDER_NBR = "order_nbr";
  private static final String QUESTION_IDENTIFIER = "question_identifier";
  private static final String QUESTION_LABEL = "question_label";
  private static final String DATA_TYPE = "data_type";
  private static final String ENABLE_IND = "enable_ind";
  private static final String DISPLAY_IND = "display_ind";
  private static final String OTHER_VALUE_IND_CD = "other_value_ind_cd";

  private static final String[] SIMPLE_PAGE_METADATA_HEADER =
      new String[] {
        PAGE_NAME,
        ORDER_NBR,
        QUESTION_IDENTIFIER,
        "question_nm",
        QUESTION_LABEL,
        "question_type",
        "desc_txt",
        "question_tool_tip",
        DATA_TYPE,
        "ui_display_type",
        "code_set_nm ",
        " value_set_code",
        "value_set_nm ",
        ENABLE_IND,
        DISPLAY_IND,
        "required_ind",
        "publish_ind_cd",
        "repeats_ind_cd",
        "field_size",
        "max_length",
        "mask",
        "min_value",
        "max_value",
        "default_value",
        OTHER_VALUE_IND_CD,
        "future_date_ind_cd",
        "unit_type_cd",
        "unit_code_set_nm",
        "unit_value",
        "question_unit_identifier",
        "unit_parent_identifier",
        "data_location",
        "part_type_cd",
        "participation_type",
        "data_cd",
        "data_use_cd",
        "standard_question_ind_cd",
        "standard_nnd_ind_cd",
        "coinfection_ind_cd",
        " repeat_group_seq_nbr",
        "question_group_seq_nbr",
        "batch_table_appear_ind_cd",
        "batch_table_column_width",
        "batch_table_header",
        "block_nm",
        "block_pivot_nbr",
        "question_identifier_nnd",
        "question_label_nnd",
        "question_required_nnd",
        "question_data_type_nnd",
        "hl7_segment_field",
        "order_group_id",
        "question_map",
        "indicator_cd",
        "group_nm",
        "sub_group_nm",
        "rdb_table_nm",
        "rdb_column_nm",
        "data_mart_column_nm",
        "rpt_admin_column_nm",
        "admin_comment"
      };

  private static final String[] COMPREHENSIVE_PAGE_METADATA_HEADER =
      new String[] {
        PAGE_NAME,
        ORDER_NBR,
        QUESTION_IDENTIFIER,
        "question_nm",
        QUESTION_LABEL,
        "question_type",
        "desc_txt",
        "question_oid",
        "question_oid_system_txt",
        "question_tool_tip",
        DATA_TYPE,
        "nbs_ui_component_uid",
        "ui_display_type",
        "code_set_group_id",
        "code_set_nm ",
        " value_set_code",
        "value_set_nm ",
        ENABLE_IND,
        DISPLAY_IND,
        "required_ind",
        "publish_ind_cd",
        "version_ctrl_nbr",
        "repeats_ind_cd",
        "field_size",
        "max_length",
        "mask",
        "min_value",
        "max_value",
        "default_value",
        OTHER_VALUE_IND_CD,
        "future_date_ind_cd",
        "unit_type_cd",
        "unit_code_set_nm",
        "unit_value",
        "question_unit_identifier",
        "unit_parent_identifier",
        "data_location",
        "legacy_data_location",
        "part_type_cd",
        "participation_type",
        "data_cd",
        "data_use_cd",
        "entry_method",
        "standard_question_ind_cd",
        "standard_nnd_ind_cd",
        "coinfection_ind_cd",
        " repeat_group_seq_nbr",
        "question_group_seq_nbr",
        "batch_table_appear_ind_cd",
        "batch_table_column_width",
        "batch_table_header",
        "block_nm",
        "block_pivot_nbr",
        "question_identifier_nnd",
        "question_label_nnd",
        "question_required_nnd",
        "question_data_type_nnd",
        "hl7_segment_field",
        "order_group_id",
        "question_map",
        "indicator_cd",
        "group_nm",
        "sub_group_nm",
        "rdb_table_nm",
        "rdb_column_nm",
        "data_mart_column_nm",
        "rpt_admin_column_nm",
        "admin_comment"
      };

  private static final String[] PAGE_VOCABULARY_METADATA_HEADER =
      new String[] {
        "code_set_nm", "value_set_code", "value_set_nm", "code", "code_desc_txt",
            "code_short_desc_txt",
        "status_cd", "concept_code", "concept_nm", "concept_preferred_nm", "code_set_group_id",
            "code_system_cd",
        "code_system_desc_txt", "concept_type_cd", "effective_from_time", "effective_to_time",
            "add_time"
      };

  private static final String[] PAGE_QUESTION_VOCABULARY_METADATA_HEADER =
      new String[] {
        PAGE_NAME,
        ORDER_NBR,
        QUESTION_IDENTIFIER,
        QUESTION_LABEL,
        DATA_TYPE,
        ENABLE_IND,
        DISPLAY_IND,
        OTHER_VALUE_IND_CD,
        "code_set_nm",
        "value_set_code",
        "value_set_nm",
        "code",
        "code_desc_txt",
        "code_short_desc_txt",
        "status_cd",
        "effective_from_time",
        "effective_to_time",
        "concept_code",
        "concept_nm",
        "concept_preferred_nm",
        "code_system_cd",
        "code_system_desc_txt",
        "concept_type_cd",
        "add_time"
      };

  private static final String FIND_SIMPLE_QUESTION_METADATA_BY_TEMPLATE_SQL =
      """
        SELECT
                            NBS_ODSE.dbo.WA_template.template_nm AS page_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.order_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.question_identifier,
                            NBS_ODSE.dbo.WA_UI_metadata.question_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.question_label,
                            NBS_ODSE.dbo.WA_UI_metadata.question_type,
                            NBS_ODSE.dbo.WA_UI_metadata.desc_txt,
                            NBS_ODSE.dbo.WA_UI_metadata.question_tool_tip,
                            NBS_ODSE.dbo.WA_UI_metadata.data_type,
                            NBS_ODSE.dbo.NBS_ui_component.type_cd_desc AS ui_display_type,
                            NBS_SRTE.dbo.Codeset.code_set_nm code_set_nm,
                            NBS_SRTE.dbo.Codeset.value_set_code,
                            NBS_SRTE.dbo.Codeset.value_set_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.enable_ind,
                            NBS_ODSE.dbo.WA_UI_metadata.display_ind,
                            NBS_ODSE.dbo.WA_UI_metadata.required_ind,
                            NBS_ODSE.dbo.WA_UI_metadata.publish_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.repeats_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.field_size,
                            NBS_ODSE.dbo.WA_UI_metadata.max_length,
                            NBS_ODSE.dbo.WA_UI_metadata.mask,
                            NBS_ODSE.dbo.WA_UI_metadata.min_value,
                            NBS_ODSE.dbo.WA_UI_metadata.max_value,
                            NBS_ODSE.dbo.WA_UI_metadata.default_value,
                            NBS_ODSE.dbo.WA_UI_metadata.other_value_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.future_date_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.unit_type_cd,
                            NBS_SRTE.dbo.Codeset.code_set_nm code_set_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.unit_value,
                            NBS_ODSE.dbo.WA_UI_metadata.question_unit_identifier,
                            NBS_ODSE.dbo.WA_UI_metadata.unit_parent_identifier,
                            NBS_ODSE.dbo.WA_UI_metadata.data_location,
                            NBS_ODSE.dbo.WA_UI_metadata.part_type_cd,
                            NBS_SRTE.dbo.Participation_type.type_desc_txt AS participation_type,
                            NBS_ODSE.dbo.WA_UI_metadata.data_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.data_use_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.standard_question_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.standard_nnd_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.coinfection_ind_cd,
                            NBS_ODSE.dbo.WA_NND_metadata.repeat_group_seq_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.question_group_seq_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.batch_table_appear_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.batch_table_column_width,
                            NBS_ODSE.dbo.WA_UI_metadata.batch_table_header,
                            NBS_ODSE.dbo.WA_UI_metadata.block_nm,
                            NBS_ODSE.dbo.WA_RDB_metadata.block_pivot_nbr,
                            NBS_ODSE.dbo.WA_NND_metadata.question_identifier_nnd,
                            NBS_ODSE.dbo.WA_NND_metadata.question_label_nnd,
                            NBS_ODSE.dbo.WA_NND_metadata.question_required_nnd,
                            NBS_ODSE.dbo.WA_NND_metadata.question_data_type_nnd,
                            NBS_ODSE.dbo.WA_NND_metadata.hl7_segment_field,
                            NBS_ODSE.dbo.WA_NND_metadata.order_group_id,
                            NBS_ODSE.dbo.WA_NND_metadata.question_map,
                            NBS_ODSE.dbo.WA_NND_metadata.indicator_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.group_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.sub_group_nm,
                            NBS_ODSE.dbo.WA_RDB_metadata.rdb_table_nm,
                            NBS_ODSE.dbo.WA_RDB_metadata.rdb_column_nm,
                            NBS_ODSE.dbo.WA_RDB_metadata.user_defined_column_nm AS data_mart_column_nm,
                            NBS_ODSE.dbo.WA_RDB_metadata.rpt_admin_column_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.admin_comment
                       FROM
                            NBS_ODSE.dbo.WA_UI_metadata WITH (nolock)  /* ON Code_value_general.code_desc_txt = NBS_ODSE.dbo.WA_UI_metadata.question_oid */
                       LEFT OUTER JOIN
                            NBS_ODSE.dbo.WA_template WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.wa_template_uid = NBS_ODSE.dbo.WA_template.wa_template_uid
                       LEFT OUTER JOIN
                            NBS_ODSE.dbo.WA_RDB_metadata WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.wa_ui_metadata_uid = NBS_ODSE.dbo.WA_RDB_metadata.wa_ui_metadata_uid
                       LEFT OUTER JOIN
                            NBS_ODSE.dbo.WA_NND_metadata WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.wa_ui_metadata_uid = NBS_ODSE.dbo.WA_NND_metadata.wa_ui_metadata_uid
                       LEFT OUTER JOIN
                            NBS_SRTE.dbo.Participation_type WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.part_type_cd = Participation_type.type_cd
                       LEFT OUTER JOIN
                            NBS_ODSE.dbo.NBS_ui_component WITH (nolock) ON  NBS_ODSE.dbo.WA_UI_metadata.nbs_ui_component_uid = NBS_ODSE.dbo.NBS_ui_component.nbs_ui_component_uid
                       LEFT OUTER JOIN
                            NBS_SRTE.dbo.Codeset WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id = Codeset.code_set_group_id
                       LEFT OUTER JOIN
                            NBS_SRTE.dbo.Code_value_general WITH (nolock) ON NBS_SRTE.dbo.Code_value_general.code_set_nm = Codeset.code_set_nm

                       WHERE  (NBS_ODSE.dbo.WA_ui_metadata.wa_template_uid =?
                       AND WA_UI_metadata.question_identifier NOT LIKE '%_UI_%'
                       AND WA_UI_metadata.question_identifier NOT LIKE 'MSG%' )

                            GROUP BY NBS_ODSE.dbo.WA_template.template_nm , NBS_ODSE.dbo.WA_UI_metadata.order_nbr, NBS_ODSE.dbo.WA_UI_metadata.question_identifier,
                            NBS_ODSE.dbo.WA_UI_metadata.question_nm, NBS_ODSE.dbo.WA_UI_metadata.desc_txt, NBS_ODSE.dbo.WA_UI_metadata.question_oid,
                            NBS_ODSE.dbo.WA_UI_metadata.question_label,
                            NBS_ODSE.dbo.WA_UI_metadata.question_tool_tip, NBS_ODSE.dbo.WA_UI_metadata.data_location, NBS_ODSE.dbo.WA_UI_metadata.data_type, NBS_SRTE.dbo.Codeset.code_set_nm,
                            NBS_ODSE.dbo.NBS_ui_component.type_cd_desc , NBS_ODSE.dbo.WA_UI_metadata.group_nm, NBS_SRTE.dbo.Participation_type.type_desc_txt ,
                            NBS_ODSE.dbo.WA_UI_metadata.data_cd, NBS_ODSE.dbo.WA_UI_metadata.data_use_cd, NBS_ODSE.dbo.WA_UI_metadata.sub_group_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.display_ind, NBS_ODSE.dbo.WA_UI_metadata.enable_ind, NBS_ODSE.dbo.WA_UI_metadata.required_ind,
                            NBS_ODSE.dbo.WA_UI_metadata.other_value_ind_cd, NBS_ODSE.dbo.WA_UI_metadata.future_date_ind_cd, NBS_ODSE.dbo.WA_UI_metadata.publish_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.default_value, NBS_ODSE.dbo.WA_UI_metadata.max_length, NBS_ODSE.dbo.WA_UI_metadata.field_size,
                            NBS_ODSE.dbo.WA_UI_metadata.mask, NBS_ODSE.dbo.WA_UI_metadata.min_value, NBS_ODSE.dbo.WA_UI_metadata.max_value,
                            NBS_ODSE.dbo.WA_UI_metadata.unit_type_cd, NBS_ODSE.dbo.WA_UI_metadata.unit_value, NBS_ODSE.dbo.WA_UI_metadata.question_unit_identifier,
                            NBS_ODSE.dbo.WA_UI_metadata.unit_parent_identifier, NBS_ODSE.dbo.WA_UI_metadata.block_nm, NBS_ODSE.dbo.WA_RDB_metadata.block_pivot_nbr,
                            NBS_ODSE.dbo.WA_RDB_metadata.rdb_table_nm, NBS_ODSE.dbo.WA_RDB_metadata.rdb_column_nm,
                            NBS_ODSE.dbo.WA_RDB_metadata.user_defined_column_nm , NBS_ODSE.dbo.WA_RDB_metadata.rpt_admin_column_nm,
                            NBS_ODSE.dbo.WA_NND_metadata.question_identifier_nnd, NBS_ODSE.dbo.WA_NND_metadata.question_label_nnd,
                            NBS_ODSE.dbo.WA_NND_metadata.question_required_nnd, NBS_ODSE.dbo.WA_NND_metadata.question_data_type_nnd,
                            NBS_ODSE.dbo.WA_NND_metadata.hl7_segment_field, NBS_ODSE.dbo.WA_NND_metadata.order_group_id, NBS_ODSE.dbo.WA_UI_metadata.admin_comment,
                            NBS_SRTE.dbo.Codeset.value_set_code,
                            NBS_SRTE.dbo.Codeset.value_set_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.repeats_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.part_type_cd,
                            WA_UI_metadata.standard_question_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.standard_nnd_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.coinfection_ind_cd,
                            NBS_ODSE.dbo.WA_NND_metadata.repeat_group_seq_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.question_group_seq_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.batch_table_appear_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.batch_table_column_width,
                            NBS_ODSE.dbo.WA_UI_metadata.batch_table_header,
                            NBS_ODSE.dbo.WA_NND_metadata.question_map,
                            NBS_ODSE.dbo.WA_UI_metadata.desc_txt,
                            NBS_ODSE.dbo.WA_UI_metadata.question_type,
                            NBS_ODSE.dbo.WA_NND_metadata.indicator_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.nbs_ui_component_uid,
                            NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id,
                            NBS_ODSE.dbo.WA_UI_metadata.version_ctrl_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.legacy_data_location,
                            NBS_ODSE.dbo.WA_UI_metadata.entry_method,
                            NBS_ODSE.dbo.WA_UI_metadata.question_oid,
                            NBS_ODSE.dbo.WA_UI_metadata.question_oid_system_txt
                             ORDER BY participation_type, NBS_ODSE.dbo.WA_UI_metadata.order_nbr
      """;

  private static final String FIND_COMPREHENSIVE_QUESTION_METADATA_BY_TEMPLATE_SQL =
      """
        SELECT
                            NBS_ODSE.dbo.WA_template.template_nm AS page_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.order_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.question_identifier,
                            NBS_ODSE.dbo.WA_UI_metadata.question_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.question_label,
                            NBS_ODSE.dbo.WA_UI_metadata.question_type,
                            NBS_ODSE.dbo.WA_UI_metadata.desc_txt,
                            NBS_ODSE.dbo.WA_UI_metadata.question_oid,
                            NBS_ODSE.dbo.WA_UI_metadata.question_oid_system_txt,
                            NBS_ODSE.dbo.WA_UI_metadata.question_tool_tip,
                            NBS_ODSE.dbo.WA_UI_metadata.data_type,
                            NBS_ODSE.dbo.WA_UI_metadata.nbs_ui_component_uid,
                            NBS_ODSE.dbo.NBS_ui_component.type_cd_desc AS ui_display_type,
                            NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id,
                            NBS_SRTE.dbo.Codeset.code_set_nm code_set_nm,
                            NBS_SRTE.dbo.Codeset.value_set_code,
                            NBS_SRTE.dbo.Codeset.value_set_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.enable_ind,
                            NBS_ODSE.dbo.WA_UI_metadata.display_ind,
                            NBS_ODSE.dbo.WA_UI_metadata.required_ind,
                            NBS_ODSE.dbo.WA_UI_metadata.publish_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.version_ctrl_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.repeats_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.field_size,
                            NBS_ODSE.dbo.WA_UI_metadata.max_length,
                            NBS_ODSE.dbo.WA_UI_metadata.mask,
                            NBS_ODSE.dbo.WA_UI_metadata.min_value,
                            NBS_ODSE.dbo.WA_UI_metadata.max_value,
                            NBS_ODSE.dbo.WA_UI_metadata.default_value,
                            NBS_ODSE.dbo.WA_UI_metadata.other_value_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.future_date_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.unit_type_cd,
                            NBS_SRTE.dbo.Codeset.code_set_nm code_set_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.unit_value,
                            NBS_ODSE.dbo.WA_UI_metadata.question_unit_identifier,
                            NBS_ODSE.dbo.WA_UI_metadata.unit_parent_identifier,
                            NBS_ODSE.dbo.WA_UI_metadata.data_location,
                            NBS_ODSE.dbo.WA_UI_metadata.legacy_data_location,
                            NBS_ODSE.dbo.WA_UI_metadata.part_type_cd,
                            NBS_SRTE.dbo.Participation_type.type_desc_txt AS participation_type,
                            NBS_ODSE.dbo.WA_UI_metadata.data_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.data_use_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.entry_method,
                            NBS_ODSE.dbo.WA_UI_metadata.standard_question_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.standard_nnd_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.coinfection_ind_cd,
                            NBS_ODSE.dbo.WA_NND_metadata.repeat_group_seq_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.question_group_seq_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.batch_table_appear_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.batch_table_column_width,
                            NBS_ODSE.dbo.WA_UI_metadata.batch_table_header,
                            NBS_ODSE.dbo.WA_UI_metadata.block_nm,
                            NBS_ODSE.dbo.WA_RDB_metadata.block_pivot_nbr,
                            NBS_ODSE.dbo.WA_NND_metadata.question_identifier_nnd,
                            NBS_ODSE.dbo.WA_NND_metadata.question_label_nnd,
                            NBS_ODSE.dbo.WA_NND_metadata.question_required_nnd,
                            NBS_ODSE.dbo.WA_NND_metadata.question_data_type_nnd,
                            NBS_ODSE.dbo.WA_NND_metadata.hl7_segment_field,
                            NBS_ODSE.dbo.WA_NND_metadata.order_group_id,
                            NBS_ODSE.dbo.WA_NND_metadata.question_map,
                            NBS_ODSE.dbo.WA_NND_metadata.indicator_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.group_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.sub_group_nm,
                            NBS_ODSE.dbo.WA_RDB_metadata.rdb_table_nm,
                            NBS_ODSE.dbo.WA_RDB_metadata.rdb_column_nm,
                            NBS_ODSE.dbo.WA_RDB_metadata.user_defined_column_nm AS data_mart_column_nm,
                            NBS_ODSE.dbo.WA_RDB_metadata.rpt_admin_column_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.admin_comment
                       FROM
                            NBS_ODSE.dbo.WA_UI_metadata WITH (nolock)  /* ON Code_value_general.code_desc_txt = NBS_ODSE.dbo.WA_UI_metadata.question_oid */
                       LEFT OUTER JOIN
                            NBS_ODSE.dbo.WA_template WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.wa_template_uid = NBS_ODSE.dbo.WA_template.wa_template_uid
                       LEFT OUTER JOIN
                            NBS_ODSE.dbo.WA_RDB_metadata WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.wa_ui_metadata_uid = NBS_ODSE.dbo.WA_RDB_metadata.wa_ui_metadata_uid
                       LEFT OUTER JOIN
                            NBS_ODSE.dbo.WA_NND_metadata WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.wa_ui_metadata_uid = NBS_ODSE.dbo.WA_NND_metadata.wa_ui_metadata_uid
                       LEFT OUTER JOIN
                            NBS_SRTE.dbo.Participation_type WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.part_type_cd = Participation_type.type_cd
                       LEFT OUTER JOIN
                            NBS_ODSE.dbo.NBS_ui_component WITH (nolock) ON  NBS_ODSE.dbo.WA_UI_metadata.nbs_ui_component_uid = NBS_ODSE.dbo.NBS_ui_component.nbs_ui_component_uid
                       LEFT OUTER JOIN
                            NBS_SRTE.dbo.Codeset WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id = Codeset.code_set_group_id
                       LEFT OUTER JOIN
                            NBS_SRTE.dbo.Code_value_general WITH (nolock) ON NBS_SRTE.dbo.Code_value_general.code_set_nm = Codeset.code_set_nm

                       WHERE  NBS_ODSE.dbo.WA_ui_metadata.wa_template_uid =?

                            GROUP BY NBS_ODSE.dbo.WA_template.template_nm , NBS_ODSE.dbo.WA_UI_metadata.order_nbr, NBS_ODSE.dbo.WA_UI_metadata.question_identifier,
                            NBS_ODSE.dbo.WA_UI_metadata.question_nm, NBS_ODSE.dbo.WA_UI_metadata.desc_txt, NBS_ODSE.dbo.WA_UI_metadata.question_oid,
                            NBS_ODSE.dbo.WA_UI_metadata.question_label,
                            NBS_ODSE.dbo.WA_UI_metadata.question_tool_tip, NBS_ODSE.dbo.WA_UI_metadata.data_location, NBS_ODSE.dbo.WA_UI_metadata.data_type, NBS_SRTE.dbo.Codeset.code_set_nm,
                            NBS_ODSE.dbo.NBS_ui_component.type_cd_desc , NBS_ODSE.dbo.WA_UI_metadata.group_nm, NBS_SRTE.dbo.Participation_type.type_desc_txt ,
                            NBS_ODSE.dbo.WA_UI_metadata.data_cd, NBS_ODSE.dbo.WA_UI_metadata.data_use_cd, NBS_ODSE.dbo.WA_UI_metadata.sub_group_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.display_ind, NBS_ODSE.dbo.WA_UI_metadata.enable_ind, NBS_ODSE.dbo.WA_UI_metadata.required_ind,
                            NBS_ODSE.dbo.WA_UI_metadata.other_value_ind_cd, NBS_ODSE.dbo.WA_UI_metadata.future_date_ind_cd, NBS_ODSE.dbo.WA_UI_metadata.publish_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.default_value, NBS_ODSE.dbo.WA_UI_metadata.max_length, NBS_ODSE.dbo.WA_UI_metadata.field_size,
                            NBS_ODSE.dbo.WA_UI_metadata.mask, NBS_ODSE.dbo.WA_UI_metadata.min_value, NBS_ODSE.dbo.WA_UI_metadata.max_value,
                            NBS_ODSE.dbo.WA_UI_metadata.unit_type_cd, NBS_ODSE.dbo.WA_UI_metadata.unit_value, NBS_ODSE.dbo.WA_UI_metadata.question_unit_identifier,
                            NBS_ODSE.dbo.WA_UI_metadata.unit_parent_identifier, NBS_ODSE.dbo.WA_UI_metadata.block_nm, NBS_ODSE.dbo.WA_RDB_metadata.block_pivot_nbr,
                            NBS_ODSE.dbo.WA_RDB_metadata.rdb_table_nm, NBS_ODSE.dbo.WA_RDB_metadata.rdb_column_nm,
                            NBS_ODSE.dbo.WA_RDB_metadata.user_defined_column_nm , NBS_ODSE.dbo.WA_RDB_metadata.rpt_admin_column_nm,
                            NBS_ODSE.dbo.WA_NND_metadata.question_identifier_nnd, NBS_ODSE.dbo.WA_NND_metadata.question_label_nnd,
                            NBS_ODSE.dbo.WA_NND_metadata.question_required_nnd, NBS_ODSE.dbo.WA_NND_metadata.question_data_type_nnd,
                            NBS_ODSE.dbo.WA_NND_metadata.hl7_segment_field, NBS_ODSE.dbo.WA_NND_metadata.order_group_id, NBS_ODSE.dbo.WA_UI_metadata.admin_comment,
                            NBS_SRTE.dbo.Codeset.value_set_code,
                            NBS_SRTE.dbo.Codeset.value_set_nm,
                            NBS_ODSE.dbo.WA_UI_metadata.repeats_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.part_type_cd,
                            WA_UI_metadata.standard_question_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.standard_nnd_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.coinfection_ind_cd,
                            NBS_ODSE.dbo.WA_NND_metadata.repeat_group_seq_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.question_group_seq_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.batch_table_appear_ind_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.batch_table_column_width,
                            NBS_ODSE.dbo.WA_UI_metadata.batch_table_header,
                            NBS_ODSE.dbo.WA_NND_metadata.question_map,
                            NBS_ODSE.dbo.WA_UI_metadata.desc_txt,
                            NBS_ODSE.dbo.WA_UI_metadata.question_type,
                            NBS_ODSE.dbo.WA_NND_metadata.indicator_cd,
                            NBS_ODSE.dbo.WA_UI_metadata.nbs_ui_component_uid,
                            NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id,
                            NBS_ODSE.dbo.WA_UI_metadata.version_ctrl_nbr,
                            NBS_ODSE.dbo.WA_UI_metadata.legacy_data_location,
                            NBS_ODSE.dbo.WA_UI_metadata.entry_method,
                            NBS_ODSE.dbo.WA_UI_metadata.question_oid,
                            NBS_ODSE.dbo.WA_UI_metadata.question_oid_system_txt
                             ORDER BY participation_type, NBS_ODSE.dbo.WA_UI_metadata.order_nbr
      """;

  private static final String FIND_PAGE_VOCABULARY_BY_TEMPLATE_SQL =
      """
      SELECT distinct COALESCE(NBS_SRTE.dbo.Codeset.code_set_nm, codeset2.code_set_nm) code_set_nm,
              COALESCE(NBS_SRTE.dbo.Codeset.value_set_code,codeset2.value_set_code) value_set_code,
              COALESCE(NBS_SRTE.dbo.Codeset.value_set_nm,codeset2.value_set_nm) value_set_nm,
              COALESCE(NBS_SRTE.dbo.Code_value_general.code,codevaluegeneral2.code) code,
              COALESCE(NBS_SRTE.dbo.Code_value_general.code_desc_txt,codevaluegeneral2.code_desc_txt) code_desc_txt,
              COALESCE(NBS_SRTE.dbo.Code_value_general.code_short_desc_txt,codevaluegeneral2.code_short_desc_txt) code_short_desc_txt,
              COALESCE(NBS_SRTE.dbo.Code_value_general.status_cd,codevaluegeneral2.status_cd) status_cd,
              COALESCE(NBS_SRTE.dbo.Code_value_general.concept_code,codevaluegeneral2.concept_code) concept_code,
              COALESCE(NBS_SRTE.dbo.Code_value_general.concept_nm,codevaluegeneral2.concept_nm) concept_nm,
              COALESCE(NBS_SRTE.dbo.Code_value_general.concept_preferred_nm,codevaluegeneral2.concept_preferred_nm) concept_preferred_nm,
              COALESCE(NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id, codeset2.code_set_group_id) code_set_group_id,
              COALESCE(NBS_SRTE.dbo.Code_value_general.code_system_cd,codevaluegeneral2.code_system_cd) code_system_cd,
              COALESCE(NBS_SRTE.dbo.Code_value_general.code_system_desc_txt,codevaluegeneral2.code_system_desc_txt) code_system_desc_txt,
              COALESCE(NBS_SRTE.dbo.Code_value_general.concept_type_cd,codevaluegeneral2.concept_type_cd) concept_type_cd,
              COALESCE(NBS_SRTE.dbo.Code_value_general.effective_from_time,codevaluegeneral2.effective_from_time) effective_from_time,
              COALESCE(NBS_SRTE.dbo.Code_value_general.effective_to_time,codevaluegeneral2.effective_to_time) effective_to_time,
              COALESCE(NBS_SRTE.dbo.Code_value_general.add_time,codevaluegeneral2.add_time) add_time

              FROM  NBS_ODSE.dbo.WA_UI_metadata WITH (nolock)  LEFT OUTER JOIN NBS_ODSE.dbo.WA_template WITH (nolock) ON
              NBS_ODSE.dbo.WA_UI_metadata.wa_template_uid = NBS_ODSE.dbo.WA_template.wa_template_uid
              LEFT OUTER JOIN NBS_SRTE.dbo.Codeset WITH (nolock) ON  NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id = Codeset.code_set_group_id
              LEFT OUTER JOIN NBS_SRTE.dbo.Codeset codeset2 WITH (nolock) ON  NBS_ODSE.dbo.WA_UI_metadata.unit_value = CAST(Codeset2.code_set_group_id AS VARCHAR(10)) AND unit_type_cd = 'CODED'
              AND unit_value NOT IN (SELECT CAST(code_set_group_id AS VARCHAR(10)) FROM WA_UI_metadata WHERE wa_template_uid = ? AND code_set_group_id IS NOT NULL)

              LEFT OUTER JOIN NBS_SRTE.dbo.code_value_general codevaluegeneral2 WITH (nolock) ON codevaluegeneral2.code_set_nm =
              codeset2.code_set_nm
              LEFT OUTER JOIN  NBS_SRTE.dbo.code_value_general WITH (nolock) ON NBS_SRTE.dbo.code_value_general.code_set_nm =
              NBS_SRTE.dbo.Codeset.code_set_nm WHERE  (NBS_ODSE.dbo.WA_UI_metadata.wa_template_uid = ?)
              AND ((NBS_SRTE.dbo.Codeset.code_set_nm is not NULL AND NBS_SRTE.dbo.Codeset.code_set_nm <> 'OUTBREAK_NM') OR (NBS_ODSE.dbo.WA_UI_METADATA.unit_type_cd='CODED' AND codeset2.code_set_nm IS NOT NULL))

              GROUP BY NBS_SRTE.dbo.Codeset.code_set_nm, NBS_SRTE.dbo.Codeset.value_set_code,
               NBS_SRTE.dbo.Codeset.value_set_nm, NBS_SRTE.dbo.Code_value_general.code,
               NBS_SRTE.dbo.Code_value_general.code_desc_txt, NBS_SRTE.dbo.Code_value_general.code_short_desc_txt,
               NBS_SRTE.dbo.Code_value_general.status_cd, NBS_SRTE.dbo.Code_value_general.concept_code,
               NBS_SRTE.dbo.Code_value_general.concept_nm, NBS_SRTE.dbo.Code_value_general.concept_preferred_nm,
               NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id, NBS_SRTE.dbo.Code_value_general.code_system_cd,
               NBS_SRTE.dbo.Code_value_general.code_system_desc_txt, NBS_SRTE.dbo.Code_value_general.concept_type_cd,
               NBS_SRTE.dbo.Code_value_general.effective_from_time, NBS_SRTE.dbo.Code_value_general.effective_to_time,
               NBS_SRTE.dbo.Code_value_general.add_time, NBS_ODSE.dbo.WA_UI_metadata.order_nbr,
               NBS_ODSE.dbo.WA_UI_metadata.unit_type_cd, NBS_ODSE.dbo.WA_UI_metadata.unit_value,
              codeset2.code_set_nm,
              codeset2.value_set_code,
              codeset2.value_set_nm,
              codevaluegeneral2.code,
              codevaluegeneral2.code_desc_txt,
              codevaluegeneral2.code_short_desc_txt,
              codevaluegeneral2.status_cd,
              codevaluegeneral2.concept_code,
              codevaluegeneral2.concept_nm,
              codevaluegeneral2.concept_preferred_nm,
              wa_ui_metadata.code_set_group_id,
              codevaluegeneral2.code_system_cd,
              codevaluegeneral2.code_system_desc_txt,
              codevaluegeneral2.concept_type_cd,
              codevaluegeneral2.effective_from_time,
              codevaluegeneral2.effective_to_time,
              codevaluegeneral2.add_time,
              codeset2.code_set_group_id
        """;

  private static final String FIND_PAGE_QUESTION_VOCABULARY_BY_TEMPLATE_SQL =
      """
      SELECT distinct
              NBS_ODSE.dbo.WA_template.template_nm AS page_nm,
              NBS_ODSE.dbo.WA_UI_metadata.order_nbr,
              NBS_ODSE.dbo.WA_UI_metadata.question_identifier,
              NBS_ODSE.dbo.WA_UI_metadata.question_label,
              NBS_ODSE.dbo.WA_UI_metadata.data_type,
              NBS_ODSE.dbo.WA_UI_metadata.enable_ind,
              NBS_ODSE.dbo.WA_UI_metadata.display_ind,
              NBS_ODSE.dbo.WA_UI_metadata.other_value_ind_cd,
              NBS_SRTE.dbo.Codeset.code_set_nm,
              NBS_SRTE.dbo.Codeset.value_set_code,
              NBS_SRTE.dbo.Codeset.value_set_nm,
              NBS_SRTE.dbo.Code_value_general.code,
              NBS_SRTE.dbo.Code_value_general.code_desc_txt,
              NBS_SRTE.dbo.Code_value_general.code_short_desc_txt,
              NBS_SRTE.dbo.Code_value_general.status_cd,
              NBS_SRTE.dbo.Code_value_general.effective_from_time,
              NBS_SRTE.dbo.Code_value_general.effective_to_time,
              NBS_SRTE.dbo.Code_value_general.concept_code,
              NBS_SRTE.dbo.Code_value_general.concept_nm,
              NBS_SRTE.dbo.Code_value_general.concept_preferred_nm,
              NBS_SRTE.dbo.Code_value_general.code_system_cd,
              NBS_SRTE.dbo.Code_value_general.code_system_desc_txt,
              NBS_SRTE.dbo.Code_value_general.concept_type_cd,
              NBS_SRTE.dbo.Code_value_general.add_time

              FROM  NBS_ODSE.dbo.WA_UI_metadata WITH (nolock)  LEFT OUTER JOIN NBS_ODSE.dbo.WA_template WITH (nolock) ON
              NBS_ODSE.dbo.WA_UI_metadata.wa_template_uid = NBS_ODSE.dbo.WA_template.wa_template_uid

              LEFT OUTER JOIN NBS_SRTE.dbo.Codeset WITH (nolock) ON
              NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id = Codeset.code_set_group_id
              LEFT OUTER JOIN  NBS_SRTE.dbo.code_value_general WITH (nolock) ON NBS_SRTE.dbo.code_value_general.code_set_nm = NBS_SRTE.dbo.Codeset.code_set_nm
              WHERE  (NBS_ODSE.dbo.WA_UI_metadata.wa_template_uid = ?) AND NBS_SRTE.dbo.Codeset.code_set_nm is not NULL AND NBS_SRTE.dbo.Codeset.code_set_nm <> 'OUTBREAK_NM' GROUP BY
              NBS_SRTE.dbo.Codeset.code_set_nm,
              NBS_SRTE.dbo.Codeset.value_set_code,
              NBS_SRTE.dbo.Codeset.value_set_nm,
              NBS_SRTE.dbo.Code_value_general.code,
              NBS_SRTE.dbo.Code_value_general.code_desc_txt,
              NBS_SRTE.dbo.Code_value_general.code_short_desc_txt,
              NBS_SRTE.dbo.Code_value_general.status_cd,
              NBS_SRTE.dbo.Code_value_general.concept_code,
              NBS_SRTE.dbo.Code_value_general.concept_nm,
              NBS_SRTE.dbo.Code_value_general.concept_preferred_nm,
              NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id,
              NBS_SRTE.dbo.Code_value_general.code_system_cd,
              NBS_SRTE.dbo.Code_value_general.code_system_desc_txt,
              NBS_SRTE.dbo.Code_value_general.concept_type_cd,
              NBS_SRTE.dbo.Code_value_general.effective_from_time,
              NBS_SRTE.dbo.Code_value_general.effective_to_time,
              NBS_SRTE.dbo.Code_value_general.add_time,
              NBS_ODSE.dbo.WA_UI_metadata.order_nbr,
              NBS_ODSE.dbo.WA_template.template_nm,
              NBS_ODSE.dbo.WA_UI_metadata.question_identifier,
              NBS_ODSE.dbo.WA_UI_metadata.question_label,
              NBS_ODSE.dbo.WA_UI_metadata.data_type,
              NBS_ODSE.dbo.WA_UI_metadata.enable_ind,
              NBS_ODSE.dbo.WA_UI_metadata.display_ind,
              NBS_ODSE.dbo.WA_UI_metadata.other_value_ind_cd
      """;

  public ByteArrayInputStream downloadPageMetadataByWaTemplateUid(Long waTemplateUid)
      throws IOException {
    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
      Sheet sheet1 = workbook.createSheet("Simple Question Metadata");
      printSheet(
          workbook,
          sheet1,
          SIMPLE_PAGE_METADATA_HEADER,
          findSimpleQuestionByWaTemplateUid(waTemplateUid));
      Sheet sheet2 = workbook.createSheet("Comprehensive Question Metadata");
      printSheet(
          workbook,
          sheet2,
          COMPREHENSIVE_PAGE_METADATA_HEADER,
          findComprehensiveQuestionByWaTemplateUid(waTemplateUid));
      Sheet sheet3 = workbook.createSheet("Vocabulary Metadata");
      printSheet(
          workbook,
          sheet3,
          PAGE_VOCABULARY_METADATA_HEADER,
          findPageVocabularyByWaTemplateUid(waTemplateUid));
      Sheet sheet4 = workbook.createSheet("Question With Vocabulary");
      printSheet(
          workbook,
          sheet4,
          PAGE_QUESTION_VOCABULARY_METADATA_HEADER,
          findPageQuestionVocabularyByWaTemplateUid(waTemplateUid));
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new IOException("Error Downloading Excel Sheet");
    }
  }

  public void printSheet(
      XSSFWorkbook workbook, Sheet sheet, String[] header, List<Object[]> dataList) {
    Cell cell = null;
    Row headerRow = sheet.createRow(0);

    CellStyle dateCellStyle = workbook.createCellStyle();
    CreationHelper createHelper = workbook.getCreationHelper();
    dateCellStyle.setDataFormat(
        createHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss.000"));

    CellStyle rightAlignedCellStyle = workbook.createCellStyle();
    rightAlignedCellStyle.setAlignment(HorizontalAlignment.RIGHT);

    for (int i = 0; i < header.length; i++) {
      headerRow.createCell(i).setCellValue(header[i]);
    }
    int rowIndex = 1;
    for (Object[] data : dataList) {
      Row row = sheet.createRow(rowIndex++);
      int cellIndex = 0;
      for (Object cellData : data) {
        switch (cellData) {
          case Date date -> {
            cell = row.createCell(cellIndex++);
            cell.setCellValue(date);
            cell.setCellStyle(dateCellStyle);
          }
          case Number number -> {
            cell = row.createCell(cellIndex++);
            cell.setCellValue(((number).doubleValue()));
            cell.setCellStyle(rightAlignedCellStyle);
          }
          case null, default ->
              row.createCell(cellIndex++).setCellValue(cellData != null ? cellData.toString() : "");
        }
      }
    }
  }

  public List<Object[]> findSimpleQuestionByWaTemplateUid(Long waTemplateUid) {
    return jdbcTemplate.query(
        FIND_SIMPLE_QUESTION_METADATA_BY_TEMPLATE_SQL,
        setter -> setter.setLong(1, waTemplateUid),
        (rs, rowNum) -> getResultSetRow(rs));
  }

  public List<Object[]> findComprehensiveQuestionByWaTemplateUid(Long waTemplateUid) {
    return jdbcTemplate.query(
        FIND_COMPREHENSIVE_QUESTION_METADATA_BY_TEMPLATE_SQL,
        setter -> setter.setLong(1, waTemplateUid),
        (rs, rowNum) -> getResultSetRow(rs));
  }

  public List<Object[]> findPageVocabularyByWaTemplateUid(Long waTemplateUid) {
    return jdbcTemplate.query(
        FIND_PAGE_VOCABULARY_BY_TEMPLATE_SQL,
        setter -> {
          setter.setLong(1, waTemplateUid);
          setter.setLong(2, waTemplateUid);
        },
        (rs, rowNum) -> getResultSetRow(rs));
  }

  public List<Object[]> findPageQuestionVocabularyByWaTemplateUid(Long waTemplateUid) {
    return jdbcTemplate.query(
        FIND_PAGE_QUESTION_VOCABULARY_BY_TEMPLATE_SQL,
        setter -> setter.setLong(1, waTemplateUid),
        (rs, rowNum) -> getResultSetRow(rs));
  }

  public Object[] getResultSetRow(ResultSet rs) throws SQLException {
    int columnCount = rs.getMetaData().getColumnCount();
    Object[] row = new Object[columnCount];
    for (int i = 1; i <= columnCount; i++) {
      row[i - 1] = rs.getObject(i);
    }
    return row;
  }
}

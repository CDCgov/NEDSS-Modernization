package gov.cdc.nbs.questionbank.page.util;

public interface QueryStore {

    String FIND_PAGE_METADATA_BY_TEMPLATE_SQL =
            "SELECT " +
                    "NBS_ODSE.dbo.WA_template.template_nm AS \"page_nm\", " +
                    "NBS_ODSE.dbo.WA_UI_metadata.order_nbr," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_identifier," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_nm," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_label," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_type," +
                    "NBS_ODSE.dbo.WA_UI_metadata.desc_txt," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_tool_tip," +
                    "NBS_ODSE.dbo.WA_UI_metadata.data_type," +
                    "NBS_ODSE.dbo.NBS_ui_component.type_cd_desc AS \"ui_display_type\"," +
                    "NBS_SRTE.dbo.Codeset.code_set_nm code_set_nm," +
                    "NBS_SRTE.dbo.Codeset.value_set_code," +
                    "NBS_SRTE.dbo.Codeset.value_set_nm," +
                    "NBS_ODSE.dbo.WA_UI_metadata.enable_ind," +
                    "NBS_ODSE.dbo.WA_UI_metadata.display_ind," +
                    "NBS_ODSE.dbo.WA_UI_metadata.required_ind," +
                    "NBS_ODSE.dbo.WA_UI_metadata.publish_ind_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.repeats_ind_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.field_size," +
                    "NBS_ODSE.dbo.WA_UI_metadata.max_length," +
                    "NBS_ODSE.dbo.WA_UI_metadata.mask," +
                    "NBS_ODSE.dbo.WA_UI_metadata.min_value," +
                    "NBS_ODSE.dbo.WA_UI_metadata.max_value," +
                    "NBS_ODSE.dbo.WA_UI_metadata.default_value," +
                    "NBS_ODSE.dbo.WA_UI_metadata.other_value_ind_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.future_date_ind_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.unit_type_cd," +
                    "NBS_SRTE.dbo.Codeset.code_set_nm code_set_name," +
                    "NBS_ODSE.dbo.WA_UI_metadata.unit_value," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_unit_identifier," +
                    "NBS_ODSE.dbo.WA_UI_metadata.unit_parent_identifier," +
                    "NBS_ODSE.dbo.WA_UI_metadata.data_location," +
                    "NBS_ODSE.dbo.WA_UI_metadata.part_type_cd," +
                    "NBS_SRTE.dbo.Participation_type.type_desc_txt AS \"participation_type\"," +
                    "NBS_ODSE.dbo.WA_UI_metadata.data_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.data_use_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.standard_question_ind_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.standard_nnd_ind_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.coinfection_ind_cd," +
                    "NBS_ODSE.dbo.WA_NND_metadata.repeat_group_seq_nbr," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_group_seq_nbr," +
                    "NBS_ODSE.dbo.WA_UI_metadata.batch_table_appear_ind_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.batch_table_column_width," +
                    "NBS_ODSE.dbo.WA_UI_metadata.batch_table_header," +
                    "NBS_ODSE.dbo.WA_UI_metadata.block_nm," +
                    "NBS_ODSE.dbo.WA_RDB_metadata.block_pivot_nbr," +
                    "NBS_ODSE.dbo.WA_NND_metadata.question_identifier_nnd," +
                    "NBS_ODSE.dbo.WA_NND_metadata.question_label_nnd," +
                    "NBS_ODSE.dbo.WA_NND_metadata.question_required_nnd," +
                    "NBS_ODSE.dbo.WA_NND_metadata.question_data_type_nnd," +
                    "NBS_ODSE.dbo.WA_NND_metadata.hl7_segment_field," +
                    "NBS_ODSE.dbo.WA_NND_metadata.order_group_id," +
                    "NBS_ODSE.dbo.WA_NND_metadata.question_map," +
                    "NBS_ODSE.dbo.WA_NND_metadata.indicator_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.group_nm," +
                    "NBS_ODSE.dbo.WA_UI_metadata.sub_group_nm," +
                    "NBS_ODSE.dbo.WA_RDB_metadata.rdb_table_nm," +
                    "NBS_ODSE.dbo.WA_RDB_metadata.rdb_column_nm," +
                    "NBS_ODSE.dbo.WA_RDB_metadata.user_defined_column_nm AS \"data_mart_column_nm\"," +
                    "NBS_ODSE.dbo.WA_RDB_metadata.rpt_admin_column_nm," +
                    "NBS_ODSE.dbo.WA_UI_metadata.admin_comment " +

                    " FROM " +
                    "NBS_ODSE.dbo.WA_UI_metadata WITH (nolock) " +/* ON Code_value_general.code_desc_txt = NBS_ODSE.dbo.WA_UI_metadata.question_oid */" LEFT OUTER JOIN " +
                    "NBS_ODSE.dbo.WA_template WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.wa_template_uid = NBS_ODSE.dbo.WA_template.wa_template_uid LEFT OUTER JOIN " +
                    "NBS_ODSE.dbo.WA_RDB_metadata WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.wa_ui_metadata_uid = NBS_ODSE.dbo.WA_RDB_metadata.wa_ui_metadata_uid LEFT OUTER JOIN " +
                    "NBS_ODSE.dbo.WA_NND_metadata WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.wa_ui_metadata_uid = NBS_ODSE.dbo.WA_NND_metadata.wa_ui_metadata_uid LEFT OUTER JOIN " +
                    "NBS_SRTE.dbo.Participation_type WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.part_type_cd = Participation_type.type_cd LEFT OUTER JOIN " +
                    "NBS_ODSE.dbo.NBS_ui_component WITH (nolock) ON  " +
                    "NBS_ODSE.dbo.WA_UI_metadata.nbs_ui_component_uid = NBS_ODSE.dbo.NBS_ui_component.nbs_ui_component_uid LEFT OUTER JOIN " +
                    "NBS_SRTE.dbo.Codeset WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id = Codeset.code_set_group_id LEFT OUTER JOIN " +
                    "NBS_SRTE.dbo.Code_value_general WITH (nolock) ON NBS_SRTE.dbo.Code_value_general.code_set_nm = Codeset.code_set_nm " +

                    "WHERE  (NBS_ODSE.dbo.WA_ui_metadata.wa_template_uid =:waTemplateUid " +
                    "AND WA_UI_metadata.question_identifier NOT LIKE '%_UI_%' AND WA_UI_metadata.question_identifier NOT LIKE 'MSG%' ) " +

                    "GROUP BY NBS_ODSE.dbo.WA_template.template_nm , NBS_ODSE.dbo.WA_UI_metadata.order_nbr, NBS_ODSE.dbo.WA_UI_metadata.question_identifier," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_nm, NBS_ODSE.dbo.WA_UI_metadata.desc_txt, NBS_ODSE.dbo.WA_UI_metadata.question_oid," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_label," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_tool_tip, NBS_ODSE.dbo.WA_UI_metadata.data_location, NBS_ODSE.dbo.WA_UI_metadata.data_type, NBS_SRTE.dbo.Codeset.code_set_nm," +
                    "NBS_ODSE.dbo.NBS_ui_component.type_cd_desc , NBS_ODSE.dbo.WA_UI_metadata.group_nm, NBS_SRTE.dbo.Participation_type.type_desc_txt ," +
                    "NBS_ODSE.dbo.WA_UI_metadata.data_cd, NBS_ODSE.dbo.WA_UI_metadata.data_use_cd, NBS_ODSE.dbo.WA_UI_metadata.sub_group_nm," +
                    "NBS_ODSE.dbo.WA_UI_metadata.display_ind, NBS_ODSE.dbo.WA_UI_metadata.enable_ind, NBS_ODSE.dbo.WA_UI_metadata.required_ind," +
                    "NBS_ODSE.dbo.WA_UI_metadata.other_value_ind_cd, NBS_ODSE.dbo.WA_UI_metadata.future_date_ind_cd, NBS_ODSE.dbo.WA_UI_metadata.publish_ind_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.default_value, NBS_ODSE.dbo.WA_UI_metadata.max_length, NBS_ODSE.dbo.WA_UI_metadata.field_size," +
                    "NBS_ODSE.dbo.WA_UI_metadata.mask, NBS_ODSE.dbo.WA_UI_metadata.min_value, NBS_ODSE.dbo.WA_UI_metadata.max_value," +
                    "NBS_ODSE.dbo.WA_UI_metadata.unit_type_cd, NBS_ODSE.dbo.WA_UI_metadata.unit_value, NBS_ODSE.dbo.WA_UI_metadata.question_unit_identifier," +
                    "NBS_ODSE.dbo.WA_UI_metadata.unit_parent_identifier, NBS_ODSE.dbo.WA_UI_metadata.block_nm, NBS_ODSE.dbo.WA_RDB_metadata.block_pivot_nbr," +
                    "NBS_ODSE.dbo.WA_RDB_metadata.rdb_table_nm, NBS_ODSE.dbo.WA_RDB_metadata.rdb_column_nm," +
                    "NBS_ODSE.dbo.WA_RDB_metadata.user_defined_column_nm , NBS_ODSE.dbo.WA_RDB_metadata.rpt_admin_column_nm," +
                    "NBS_ODSE.dbo.WA_NND_metadata.question_identifier_nnd, NBS_ODSE.dbo.WA_NND_metadata.question_label_nnd," +
                    "NBS_ODSE.dbo.WA_NND_metadata.question_required_nnd, NBS_ODSE.dbo.WA_NND_metadata.question_data_type_nnd," +
                    "NBS_ODSE.dbo.WA_NND_metadata.hl7_segment_field, NBS_ODSE.dbo.WA_NND_metadata.order_group_id, NBS_ODSE.dbo.WA_UI_metadata.admin_comment, " +
                    "NBS_SRTE.dbo.Codeset.value_set_code," +
                    "NBS_SRTE.dbo.Codeset.value_set_nm," +
                    "NBS_ODSE.dbo.WA_UI_metadata.repeats_ind_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.part_type_cd," +
                    "WA_UI_metadata.standard_question_ind_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.standard_nnd_ind_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.coinfection_ind_cd," +
                    "NBS_ODSE.dbo.WA_NND_metadata.repeat_group_seq_nbr," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_group_seq_nbr," +
                    "NBS_ODSE.dbo.WA_UI_metadata.batch_table_appear_ind_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.batch_table_column_width," +
                    "NBS_ODSE.dbo.WA_UI_metadata.batch_table_header," +
                    "NBS_ODSE.dbo.WA_NND_metadata.question_map," +
                    "NBS_ODSE.dbo.WA_UI_metadata.desc_txt," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_type," +
                    "NBS_ODSE.dbo.WA_NND_metadata.indicator_cd," +
                    "NBS_ODSE.dbo.WA_UI_metadata.nbs_ui_component_uid," +
                    "NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id," +
                    "NBS_ODSE.dbo.WA_UI_metadata.version_ctrl_nbr," +
                    "NBS_ODSE.dbo.WA_UI_metadata.legacy_data_location," +
                    "NBS_ODSE.dbo.WA_UI_metadata.entry_method," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_oid," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_oid_system_txt" +

                    " ORDER BY participation_type, NBS_ODSE.dbo.WA_UI_metadata.order_nbr";


}

package gov.cdc.nbs.questionbank.page;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PageMetaDataDownloader {

    private final JdbcTemplate jdbcTemplate;

    String PageMetadataHeader = "page_nm, order_nbr, question_identifier, question_nm, question_label, question_type, desc_txt," +
            "question_tool_tip, data_type, ui_display_type, code_set_nm, value_set_code, value_set_nm, enable_ind," +
            "display_ind, required_ind, publish_ind_cd, repeats_ind_cd, field_size, max_length, mask, min_value," +
            "max_value, default_value, other_value_ind_cd, future_date_ind_cd, unit_type_cd, unit_code_set_nm," +
            "unit_value, question_unit_identifier, unit_parent_identifier, data_location, part_type_cd, participation_type," +
            "data_cd, data_use_cd, standard_question_ind_cd, standard_nnd_ind_cd, coinfection_ind_cd, repeat_group_seq_nbr," +
            " question_group_seq_nbr, batch_table_appear_ind_cd, batch_table_column_width, batch_table_header, block_nm," +
            "block_pivot_nbr, question_identifier_nnd, question_label_nnd, question_required_nnd, question_data_type_nnd," +
            "hl7_segment_field, order_group_id, question_map, indicator_cd, group_nm, sub_group_nm, rdb_table_nm, " +
            "rdb_column_nm, data_mart_column_nm, rpt_admin_column_nm, admin_comment";


    private static final String QUERY =
            "SELECT " +
                    "NBS_ODSE.dbo.WA_template.template_nm AS page_nm, " +
                    "NBS_ODSE.dbo.WA_UI_metadata.order_nbr," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_identifier," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_nm," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_label," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_type," +
                    "NBS_ODSE.dbo.WA_UI_metadata.desc_txt," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_tool_tip," +
                    "NBS_ODSE.dbo.WA_UI_metadata.data_type," +
                    "NBS_ODSE.dbo.NBS_ui_component.type_cd_desc AS ui_display_type," +
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
                    "NBS_SRTE.dbo.Codeset.code_set_nm code_set_nm," +
                    "NBS_ODSE.dbo.WA_UI_metadata.unit_value," +
                    "NBS_ODSE.dbo.WA_UI_metadata.question_unit_identifier," +
                    "NBS_ODSE.dbo.WA_UI_metadata.unit_parent_identifier," +
                    "NBS_ODSE.dbo.WA_UI_metadata.data_location," +
                    "NBS_ODSE.dbo.WA_UI_metadata.part_type_cd," +
                    "NBS_SRTE.dbo.Participation_type.type_desc_txt AS participation_type," +
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
                    "NBS_ODSE.dbo.WA_RDB_metadata.user_defined_column_nm AS data_mart_column_nm," +
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

                    "WHERE  (NBS_ODSE.dbo.WA_ui_metadata.wa_template_uid =? " +
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


    public ByteArrayInputStream downloadPageMetadataByWaTemplateUid(Long waTemplateUid) throws IOException {
        final CSVFormat format = CSVFormat.Builder.create().setQuoteMode(QuoteMode.MINIMAL).
                setHeader(PageMetadataHeader.split(",")).build();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            List<Object[]> pageMetadata = findPageMetadataByWaTemplateUid(waTemplateUid);
            String temp = "";
            for (Object[] data : pageMetadata) {
                for (Object element : data) {
                    temp = temp + ",";
                }
                temp = "";
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new IOException("Error downloading Page Metadata: " + e.getMessage());
        }
    }

    public List<Object[]> findPageMetadataByWaTemplateUid(Long waTemplateUid) {
        List<Object[]> result = jdbcTemplate.query(QUERY, new Object[]{waTemplateUid}, (rs, rowNum) -> {
            int columnCount = rs.getMetaData().getColumnCount();
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);
            }
            return row;
        });
        return result;
    }


}

package gov.cdc.nbs.questionbank.page.util;

public class PageConstants {

    private PageConstants() {
    }

    // Page State Change
    public static final String SAVE_DRAFT_SUCCESS = "Draft was saved successfully.";
    public static final String SAVE_DRAFT_FAIL = " Failed to update to draft status: ";
    public static final String DELETE_DRAFT_FAIL = " Failed to delete page draft. ";
    public static final String PAGE_NOT_FOUND = " Could not find page with given id.";
    public static final String DRAFT_NOT_FOUND = " Page does not have draft.";
    public static final String DRAFT_DELETE_SUCCESS = "page draft has been successfully deleted.";
    public static final String PUBLISHED_WITH_DRAFT = "Published With Draft";
    public static final String DRAFT = "Draft";
    public static final String PUBLISHED = "Published";

    //Page Create
    public static final String ADD_PAGE_MESSAGE = " page has been successfully added";
    public static final String ADD_PAGE_FAIL = "could not successfully add page";
    public static final String ADD_PAGE_NAME_EMPTY = "Page name is required.";
    public static final String ADD_PAGE_TEMPLATENAME_EXISTS = "A Template  with Template Name %s already exists in the system";
    public static final String ADD_PAGE_CONDITION_EMPTY = "At least one condition is required.";
    public static final String ADD_PAGE_EVENTTYPE_EMPTY = "EventType is required.";
    public static final String ADD_PAGE_TEMPLATE_EMPTY = "Template is required.";
    public static final String ADD_PAGE_DATAMART_NAME_EXISTS = "A Page with Data Mart Name %s already exists in the system";
    public static final String ADD_PAGE_MMG_EMPTY = "MMG is required.";

    //Page Details
    public static final Long TAB_COMPONENT = 1010L;
    public static final Long SECTION_COMPONENT = 1015L;
    public static final Long SUB_SECTION_COMPONENT = 1016L;
    public static final Long GEN_QUESTION_COMPONENT = 1007L;
    public static final Long SPE_QUESTION_COMPONENT = 1008L;


    // General
    public static final String NOT_EXISTS = "Could not find template with given Id";
    public static final String PAGE_NOT_EXISTS = "Could not find page with given Id: ";
    public static final String BAD_REQUEST = "Could not process request. ";

    public static final String PAGE_METADATA_CSV_HEADER =
                   "page_nm, order_nbr, question_identifier, question_nm, question_label, question_type, desc_txt,"+
                    "question_tool_tip, data_type, ui_display_type, code_set_nm, value_set_code, value_set_nm, enable_ind,"+
                    "display_ind, required_ind, publish_ind_cd, repeats_ind_cd, field_size, max_length, mask, min_value,"+
                    "max_value, default_value, other_value_ind_cd, future_date_ind_cd, unit_type_cd, unit_code_set_nm,"+
                    "unit_value, question_unit_identifier, unit_parent_identifier, data_location, part_type_cd, participation_type,"+
                    "data_cd, data_use_cd, standard_question_ind_cd, standard_nnd_ind_cd, coinfection_ind_cd, repeat_group_seq_nbr,"+
                    " question_group_seq_nbr, batch_table_appear_ind_cd, batch_table_column_width, batch_table_header, block_nm,"+
                    "block_pivot_nbr, question_identifier_nnd, question_label_nnd, question_required_nnd, question_data_type_nnd,"+
                    "hl7_segment_field, order_group_id, question_map, indicator_cd, group_nm, sub_group_nm, rdb_table_nm, "+
                    "rdb_column_nm, data_mart_column_nm, rpt_admin_column_nm, admin_comment";

}

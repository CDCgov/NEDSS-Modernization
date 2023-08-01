package gov.cdc.nbs.questionbank.addsection.model;

public record CreateSectionRequest(long wa_ui_metadata_uid, long page, String name, String visible) {

}

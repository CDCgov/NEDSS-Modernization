package gov.cdc.nbs.questionbank.addtab.model;

public class CreateTabRequest {

    long waUiMetadataUid;
    long waTemplateUid;
    long nbsUiComponentUid;
    String questionLabel;
    String enableInd;
    String defaultValue;
    String displayInd;

    public String getDisplayInd() {
        return displayInd;
    }

    int orderNbr;
    String requiredInd;
    String recordStatusCd;
    int versionCtrlNbr;
    String questionIdentifier;
    String standardNndIndCd;

    public long getWaUiMetadataUid() {
        return waUiMetadataUid;
    }

    public long getWaTemplateUid() {
        return waTemplateUid;
    }

    public String getQuestionLabel() {
        return questionLabel;
    }

    public String getEnableInd() {
        return enableInd;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public int getOrderNbr() {
        return orderNbr;
    }

    public String getRequiredInd() {
        return requiredInd;
    }

    public String getRecordStatusCd() {
        return recordStatusCd;
    }

    public int getVersionCtrlNbr() {
        return versionCtrlNbr;
    }

    public String getQuestionIdentifier() {
        return questionIdentifier;
    }

    public String getStandardNndIndCd() {
        return standardNndIndCd;
    }

    public long getNbsUiComponentUid() {
        return nbsUiComponentUid;
    }
}

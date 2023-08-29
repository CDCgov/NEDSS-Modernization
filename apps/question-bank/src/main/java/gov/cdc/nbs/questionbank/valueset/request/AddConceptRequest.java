package gov.cdc.nbs.questionbank.valueset.request;

import java.time.Instant;
import io.swagger.annotations.ApiModelProperty;

public record AddConceptRequest(
        @ApiModelProperty(required = true) String code,
        @ApiModelProperty(required = true) String displayName,
        @ApiModelProperty(required = true) String shortDisplayName,
        @ApiModelProperty(required = true) Instant effectiveFromTime,
        Instant effectiveToTime,
        @ApiModelProperty(required = true, example = "A") StatusCode statusCode,
        String adminComments,
        @ApiModelProperty(required = true) MessagingInfo messagingInfo) {

    public record MessagingInfo(
            @ApiModelProperty(required = true) String conceptCode,
            @ApiModelProperty(required = true) String conceptName,
            @ApiModelProperty(required = true) String preferredConceptName,
            @ApiModelProperty(required = true, example = "ABNORMAL_FLAGS_HL7") String codeSystem) {
    }

    public enum StatusCode {
        A,
        I
    }
}



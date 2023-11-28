package gov.cdc.nbs.questionbank.question.addable;

import java.util.List;
import io.swagger.annotations.ApiModelProperty;

public record AddableQuestions(
    @ApiModelProperty(required = true) List<AddableQuestion> questions,
    @ApiModelProperty(required = true) long total) {

}

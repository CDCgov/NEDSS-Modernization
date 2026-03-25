package gov.cdc.nbs.questionbank.question.model;

import java.util.List;

public record DisplayControlOptions(
    List<DisplayOption> codedDisplayControl,
    List<DisplayOption> dateDisplayControl,
    List<DisplayOption> numericDisplayControl,
    List<DisplayOption> textDisplayControl) {}

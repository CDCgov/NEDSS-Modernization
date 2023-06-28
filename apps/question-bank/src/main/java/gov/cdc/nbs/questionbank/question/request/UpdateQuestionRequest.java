package gov.cdc.nbs.questionbank.question.request;

import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.UnitType;

public record UpdateQuestionRequest(
        String uniqueName, // editable if not in use
        String description,
        QuestionType type, // editable if not in use
        String label,
        String tooltip,
        Long displayControl,
        String adminComments,

        // Shared 
        String defaultValue,
        String mask,

        // Text question specific fields
        String fieldLength,

        // Date question specific fields
        boolean allowFutureDates,

        // Coded question specific fields
        // none

        // Numeric question specific fields
        Long minValue,
        Long maxValue,
        UnitType unitType,
        String unitValue,
        Long valueSet,

        // data mart
        String defaultLabelInReport,
        String datamartColumnName,
        String rdbColumnName, // Editable if not 'in use'


        // messaging info - always editable
        boolean includedInMessage,
        String messageVariableId,
        String labelInMessage,
        String codeSystem,
        boolean requiredInMessage,
        String hl7DataType) {

}

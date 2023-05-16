package gov.cdc.nbs.questionbank.questionnaire;

import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementGroupEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementRef;
import gov.cdc.nbs.questionbank.entities.DisplayGroupRef;
import gov.cdc.nbs.questionbank.entities.DropDownQuestionEntity;
import gov.cdc.nbs.questionbank.entities.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entities.QuestionnaireEntity;
import gov.cdc.nbs.questionbank.entities.Reference;
import gov.cdc.nbs.questionbank.entities.TextEntity;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entities.Value;
import gov.cdc.nbs.questionbank.entities.ValueSet;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;

class QuestionnaireMapper {

    /**
     * Converts a {@link gov.cdc.nbs.questionbank.entities.QuestionnaireEntity QuestionnaireEntity} into a
     * {@link gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire Questionnaire}.
     * 
     * @param optional
     * @return
     */
    public Questionnaire toQuestionnaire(QuestionnaireEntity entity) {
        return new Questionnaire(
                entity.getId(),
                entity.getConditionCodes(),
                entity.getReferences().stream().map(this::toElement).toList());
    }

    /**
     * Accepts a {@link gov.cdc.nbs.questionbank.entities.Reference Reference} and converts it into a
     * {@link gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire.Element Questionnaire.Element}.
     * 
     * A reference can be either a DisplayElementRef that references a
     * {@link gov.cdc.nbs.questionbank.entities.DisplayElementEntity DisplayElementEntity} or a DisplayElementGroupRef
     * that references a {@link gov.cdc.nbs.questionbank.entities.DisplayElementGroupEntity DisplayElementGroupEntity}.
     * 
     * @param ref
     * @return
     */
    Questionnaire.Element toElement(Reference ref) {
        if (ref instanceof DisplayElementRef displayElementRef) {
            return toDisplayElement(displayElementRef.getDisplayElementEntity());
        } else if (ref instanceof DisplayGroupRef displayGroupRef) {
            return toSection(displayGroupRef.getDisplayGroup());
        } else {
            throw new IllegalArgumentException("Unsupported display element reference type: " + ref.getReferenceType());
        }
    }

    /**
     * Converts a {@link gov.cdc.nbs.questionbank.entities.DisplayElementEntity DisplayElementEntity} to its
     * corresponding {@link gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire.Element Questionnaire.Element}.
     * 
     * @param element
     * @return
     */
    Questionnaire.DisplayElement toDisplayElement(DisplayElementEntity element) {
        if (element instanceof TextEntity t) {
            return new Questionnaire.Text(t.getId(), t.getText());
        } else if (element instanceof TextQuestionEntity tq) {
            return new Questionnaire.TextQuestion(
                    tq.getId(),
                    tq.getLabel(),
                    tq.getTooltip(),
                    tq.getMaxLength(),
                    tq.getPlaceholder());
        } else if (element instanceof NumericQuestionEntity nq) {
            return new Questionnaire.NumericQuestion(
                    nq.getId(),
                    nq.getLabel(),
                    nq.getTooltip(),
                    nq.getMinValue(),
                    nq.getMaxValue(),
                    toOptionSet(nq.getUnitsSet()));
        } else if (element instanceof DateQuestionEntity dq) {
            return new Questionnaire.DateQuestion(
                    dq.getId(),
                    dq.getLabel(),
                    dq.getTooltip(),
                    dq.isAllowFuture());

        } else if (element instanceof DropDownQuestionEntity ddq) {
            return new Questionnaire.DropDownQuestion(
                    ddq.getId(),
                    ddq.getLabel(),
                    ddq.getTooltip(),
                    toOptionSet(ddq.getValueSet()),
                    toOption(ddq.getDefaultAnswer()),
                    ddq.isMultiSelect());

        } else {
            throw new IllegalArgumentException("Unsupported display type: " + element.getDisplayType());
        }
    }

    private Questionnaire.Section toSection(DisplayElementGroupEntity displayGroup) {
        return new Questionnaire.Section(
                displayGroup.getId(),
                displayGroup.getLabel(),
                displayGroup.getElements().stream().map(this::toDisplayElement).toList());
    }

    private Questionnaire.OptionSet toOptionSet(ValueSet valueSet) {
        return new Questionnaire.OptionSet(
                valueSet.getId(),
                valueSet.getName(),
                valueSet.getDescription(),
                valueSet.getValues().stream().map(this::toOption).toList());
    }

    private Questionnaire.Option toOption(Value value) {
        return new Questionnaire.Option(
                value.getId(),
                value.getValue(),
                value.getDisplay());
    }

}

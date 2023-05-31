package gov.cdc.nbs.questionbank.questionnaire;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementGroupEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementRef;
import gov.cdc.nbs.questionbank.entities.DisplayGroupRef;
import gov.cdc.nbs.questionbank.entities.DropdownQuestionEntity;
import gov.cdc.nbs.questionbank.entities.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entities.QuestionnaireEntity;
import gov.cdc.nbs.questionbank.entities.ReferenceEntity;
import gov.cdc.nbs.questionbank.entities.TabEntity;
import gov.cdc.nbs.questionbank.entities.TextEntity;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entities.ValueEntity;
import gov.cdc.nbs.questionbank.entities.ValueSet;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;

@Component
public class EntityMapper {

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
                entity.getTabs().stream().map(this::toTab).toList());
    }

    Questionnaire.Tab toTab(TabEntity entity) {
        return new Questionnaire.Tab(entity.getName(), entity.getReferences().stream().map(this::toElement).toList());
    }

    /**
     * Accepts a {@link gov.cdc.nbs.questionbank.entities.ReferenceEntity ReferenceEntity} and converts it into a
     * {@link gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire.Element Questionnaire.Element}.
     * 
     * A referenceEntity can be either a DisplayElementRef that references a
     * {@link gov.cdc.nbs.questionbank.entities.DisplayElementEntity DisplayElementEntity} or a DisplayElementGroupRef
     * that references a {@link gov.cdc.nbs.questionbank.entities.DisplayElementGroupEntity DisplayElementGroupEntity}.
     * 
     * @param ref
     * @return
     */
    public Questionnaire.Element toElement(ReferenceEntity ref) {
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
     * corresponding {@link gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire.DisplayElement
     * Questionnaire.DisplayElement}.
     * 
     * @param element
     * @return
     */
    public Questionnaire.DisplayElement toDisplayElement(DisplayElementEntity element) {
        if (element instanceof TextEntity t) {
            return toText(t);
        } else if (element instanceof TextQuestionEntity tq) {
            return toTextQuestion(tq);
        } else if (element instanceof NumericQuestionEntity nq) {
            return toNumericQuestion(nq);
        } else if (element instanceof DateQuestionEntity dq) {
            return toDateQuestion(dq);
        } else if (element instanceof DropdownQuestionEntity ddq) {
            return toDropdownQuestion(ddq);
        } else {
            throw new IllegalArgumentException("Unsupported display type: " + element.getDisplayType());
        }
    }

    public Questionnaire.DropdownQuestion toDropdownQuestion(DropdownQuestionEntity ddq) {
        return new Questionnaire.DropdownQuestion(
                ddq.getId(),
                ddq.getVersion(),
                ddq.getLabel(),
                ddq.getTooltip(),
                toOptionSet(ddq.getValueSet()),
                toOption(ddq.getDefaultAnswer()),
                ddq.isMultiSelect(),
                ddq.getCodeSet());
    }

    public Questionnaire.DateQuestion toDateQuestion(DateQuestionEntity dq) {
        return new Questionnaire.DateQuestion(
                dq.getId(),
                dq.getVersion(),
                dq.getLabel(),
                dq.getTooltip(),
                dq.isAllowFuture(),
                dq.getCodeSet());
    }

    public Questionnaire.NumericQuestion toNumericQuestion(NumericQuestionEntity nq) {
        return new Questionnaire.NumericQuestion(
                nq.getId(),
                nq.getVersion(),
                nq.getLabel(),
                nq.getTooltip(),
                nq.getMinValue(),
                nq.getMaxValue(),
                nq.getDefaultNumericValue(),
                toOptionSet(nq.getUnitsSet()),
                nq.getCodeSet());
    }

    public Questionnaire.TextQuestion toTextQuestion(TextQuestionEntity tq) {
        return new Questionnaire.TextQuestion(
                tq.getId(),
                tq.getVersion(),
                tq.getLabel(),
                tq.getTooltip(),
                tq.getMaxLength(),
                tq.getPlaceholder(),
                tq.getDefaultTextValue(),
                tq.getCodeSet());
    }

    public Questionnaire.Text toText(TextEntity t) {
        return new Questionnaire.Text(t.getId(),
                t.getVersion(),
                t.getText());
    }

    public Questionnaire.Section toSection(DisplayElementGroupEntity displayGroup) {
        return new Questionnaire.Section(
                displayGroup.getId(),
                displayGroup.getLabel(),
                displayGroup.getElements().stream().map(this::toDisplayElement).toList());
    }

    public Questionnaire.OptionSet toOptionSet(ValueSet valueSet) {
        return new Questionnaire.OptionSet(
                valueSet.getId(),
                valueSet.getName(),
                valueSet.getDescription(),
                valueSet.getValues().stream().map(this::toOption).toList());
    }

    public Questionnaire.Option toOption(ValueEntity value) {
        return new Questionnaire.Option(
                value.getId(),
                value.getValue(),
                value.getDisplay());
    }

}

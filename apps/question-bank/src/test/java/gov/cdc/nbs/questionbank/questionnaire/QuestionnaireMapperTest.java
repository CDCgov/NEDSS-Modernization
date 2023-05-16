package gov.cdc.nbs.questionbank.questionnaire;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
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
import gov.cdc.nbs.questionbank.entities.ValueSet.ValueSetType;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire.DateQuestion;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire.DropDownQuestion;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire.NumericQuestion;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire.Option;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire.Section;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire.Text;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire.TextQuestion;

class QuestionnaireMapperTest {

    private final QuestionnaireMapper questionnaireMapper = new QuestionnaireMapper();

    @Test
    void should_return_questionnaire() {
        Questionnaire q = questionnaireMapper.toQuestionnaire(emptyEntity());
        assertNotNull(q);
    }

    @Test
    void toElement_should_throw_exception_for_invalid_type() {
        Reference ref = new Reference() {
            @Override
            public String getReferenceType() {
                return "invalid type";
            }
        };
        assertThrows(IllegalArgumentException.class, () -> questionnaireMapper.toElement(ref));
    }

    @Test
    void toDisplayElement_should_throw_exception_for_invalid_type() {
        DisplayElementEntity element = new DisplayElementEntity() {
            @Override
            public String getDisplayType() {
                return "bad type";
            }
        };
        assertThrows(IllegalArgumentException.class, () -> questionnaireMapper.toDisplayElement(element));
    }

    @Test
    void should_return_questionnaire_with_correct_content() {
        QuestionnaireEntity entity = fullEntity();
        Questionnaire q = questionnaireMapper.toQuestionnaire(entity);
        assertNotNull(q);
        assertThat(q.conditions()).containsExactly(entity.getConditionCodes().get(0));


        // text element
        var textEntity = ((TextEntity) ((DisplayElementRef) entity.getReferences().get(0)).getDisplayElementEntity());
        var textElement = (Text) q.elements().get(0);
        assertEquals(textEntity.getText(), textElement.text());
        assertEquals(textEntity.getId().longValue(), textElement.id());

        // text question
        var textQuestionEntity =
                ((TextQuestionEntity) ((DisplayElementRef) entity.getReferences().get(1)).getDisplayElementEntity());
        var textQuestionElement = (TextQuestion) q.elements().get(1);
        assertEquals(textQuestionEntity.getLabel(), textQuestionElement.label());
        assertEquals(textQuestionEntity.getId().longValue(), textQuestionElement.id());
        assertEquals(textQuestionEntity.getMaxLength(), textQuestionElement.maxLength());
        assertEquals(textQuestionEntity.getPlaceholder(), textQuestionElement.placeholder());
        assertEquals(textQuestionEntity.getTooltip(), textQuestionElement.tooltip());

        // numeric question
        var numericQuestionEntity =
                ((NumericQuestionEntity) ((DisplayElementRef) entity.getReferences().get(2)).getDisplayElementEntity());
        var numericQuestionElement = (NumericQuestion) q.elements().get(2);
        assertEquals(numericQuestionEntity.getLabel(), numericQuestionElement.label());
        assertEquals(numericQuestionEntity.getId().longValue(), numericQuestionElement.id());
        assertEquals(numericQuestionEntity.getMaxValue(), numericQuestionElement.maxValue());
        assertEquals(numericQuestionEntity.getMinValue(), numericQuestionElement.minValue());
        assertEquals(numericQuestionEntity.getTooltip(), numericQuestionElement.tooltip());

        assertEquals(numericQuestionEntity.getUnitsSet().getName(), numericQuestionElement.unitOptions().name());
        assertEquals(numericQuestionEntity.getUnitsSet().getDescription(),
                numericQuestionElement.unitOptions().description());

        numericQuestionEntity.getUnitsSet().getValues().forEach(v -> {
            Optional<Option> o = numericQuestionElement.unitOptions().options().stream().filter(option -> {
                return option.display().equals(v.getDisplay()) &&
                        option.id() == v.getId().longValue() &&
                        option.value().equals(v.getValue());
            }).findFirst();
            assertTrue(o.isPresent());
        });

        // date question
        var dateQuestionEntity =
                ((DateQuestionEntity) ((DisplayElementRef) entity.getReferences().get(3)).getDisplayElementEntity());
        var dateQuestionElement = (DateQuestion) q.elements().get(3);
        assertEquals(dateQuestionEntity.getId().longValue(), dateQuestionElement.id());
        assertEquals(dateQuestionEntity.getLabel(), dateQuestionElement.label());
        assertEquals(dateQuestionEntity.getTooltip(), dateQuestionElement.tooltip());
        assertEquals(dateQuestionEntity.isAllowFuture(), dateQuestionElement.allowFutureDates());

        // drop down question
        var ddQuestionEntity =
                ((DropDownQuestionEntity) ((DisplayElementRef) entity.getReferences().get(4))
                        .getDisplayElementEntity());
        var ddQuestionElement = (DropDownQuestion) q.elements().get(4);

        assertEquals(ddQuestionEntity.getId().longValue(), ddQuestionElement.id());
        assertEquals(ddQuestionEntity.getLabel(), ddQuestionElement.label());
        assertEquals(ddQuestionEntity.getTooltip(), ddQuestionElement.tooltip());
        assertEquals(ddQuestionEntity.isMultiSelect(), ddQuestionElement.isMultiSelect());
        assertEquals(ddQuestionEntity.getDefaultAnswer().getDisplay(), ddQuestionElement.defaultOption().display());
        assertEquals(ddQuestionEntity.getDefaultAnswer().getValue(), ddQuestionElement.defaultOption().value());
        assertEquals(ddQuestionEntity.getValueSet().getName(), ddQuestionElement.optionSet().name());
        assertEquals(ddQuestionEntity.getValueSet().getId().longValue(), ddQuestionElement.optionSet().id());
        assertEquals(ddQuestionEntity.getValueSet().getDescription(), ddQuestionElement.optionSet().description());

        ddQuestionEntity.getValueSet().getValues().forEach(v -> {
            Optional<Option> o = ddQuestionElement.optionSet().options().stream().filter(option -> {
                return option.display().equals(v.getDisplay()) &&
                        option.id() == v.getId().longValue() &&
                        option.value().equals(v.getValue());
            }).findFirst();
            assertTrue(o.isPresent());
        });

        // display group
        var displayGroupEntity =
                ((DisplayElementGroupEntity) ((DisplayGroupRef) entity.getReferences().get(5)).getDisplayGroup());
        var section = (Section) q.elements().get(5);
        assertEquals(displayGroupEntity.getId().longValue(), section.id());
        assertEquals(displayGroupEntity.getLabel(), section.label());


        TextEntity groupTextEntity = (TextEntity) displayGroupEntity.getElements().get(0);
        Text sectionText = (Text) section.elements().get(0);

        assertEquals(groupTextEntity.getId().longValue(), sectionText.id());
        assertEquals(groupTextEntity.getText(), sectionText.text());
    }


    private QuestionnaireEntity emptyEntity() {
        QuestionnaireEntity entity = new QuestionnaireEntity();
        entity.setId(1L);
        entity.setConditionCodes(Arrays.asList("condition"));
        entity.setReferences(new ArrayList<>());
        entity.setRules(new ArrayList<>());
        return entity;
    }

    private QuestionnaireEntity fullEntity() {
        QuestionnaireEntity entity = new QuestionnaireEntity();
        entity.setId(1L);
        entity.setConditionCodes(Arrays.asList("condition"));
        entity.setReferences(allReferenceTypes());
        return entity;
    }

    private List<Reference> allReferenceTypes() {
        var references = new ArrayList<Reference>();
        references.add(displayRef(textEntity(), 1));
        references.add(displayRef(textQuestionEntity(), 2));
        references.add(displayRef(numericQuestionEntity(), 3));
        references.add(displayRef(dateQuestionEntity(), 4));
        references.add(displayRef(dropDownQuestionEntity(), 5));

        references.add(displayGroupRef(groupElementEntity(), 6));
        return references;
    }

    private DisplayGroupRef displayGroupRef(DisplayElementGroupEntity entity, Integer order) {
        var ref = new DisplayGroupRef();
        ref.setDisplayGroup(entity);
        ref.setDisplayOrder(order);
        return ref;
    }

    private DisplayElementGroupEntity groupElementEntity() {
        var group = new DisplayElementGroupEntity(
                "A group of display elements",
                Arrays.asList(textEntity()));
        group.setId(99L);
        return group;
    }

    private DisplayElementRef displayRef(DisplayElementEntity entity, Integer order) {
        var ref = new DisplayElementRef();
        ref.setDisplayElementEntity(entity);
        ref.setDisplayOrder(order);
        return ref;
    }

    private DisplayElementEntity textEntity() {
        var e = new TextEntity();
        e.setId(1L);
        e.setText("Text element");
        return e;
    }

    private DisplayElementEntity dropDownQuestionEntity() {
        var e = new DropDownQuestionEntity();
        e.setId(2L);
        e.setDefaultAnswer(milliliters(null));
        e.setLabel("Dropdown question label");
        e.setMultiSelect(true);
        e.setValueSet(unitValueSet());
        e.setVersion(1);
        return e;
    }

    private DisplayElementEntity dateQuestionEntity() {
        var e = new DateQuestionEntity();
        e.setId(3L);
        e.setAllowFuture(true);
        e.setLabel("Date question label");
        e.setVersion(1);
        e.setTooltip("Date question tooltip");
        return e;
    }

    private DisplayElementEntity textQuestionEntity() {
        var e = new TextQuestionEntity();
        e.setId(4L);
        e.setLabel("Text question label");
        e.setMaxLength(100);
        e.setPlaceholder("Text Question Placeholder");
        e.setTooltip("Text Question tooltip");
        e.setVersion(1);
        return e;
    }

    private DisplayElementEntity numericQuestionEntity() {
        var e = new NumericQuestionEntity();
        e.setId(5L);
        e.setLabel("Numeric question label");
        e.setTooltip("Numeric question tooltip");
        e.setMaxValue(100);
        e.setMinValue(15);
        e.setVersion(2);
        e.setUnitsSet(unitValueSet());
        return e;
    }

    private ValueSet unitValueSet() {
        var v = new ValueSet();
        v.setId(6L);
        v.setCode("unit-code");
        v.setDescription("Value set for units");
        v.setName("Units");
        v.setType(ValueSetType.LOCAL);
        v.setValues(unitValues(v));
        return v;
    }

    private List<Value> unitValues(ValueSet v) {
        var values = new ArrayList<Value>();
        values.add(milliliters(v));
        values.add(cubicCentimeters(v));
        return values;
    }

    private Value milliliters(ValueSet vs) {
        Value v = new Value();
        v.setId(7L);
        v.setCode("m");
        v.setDisplay("milliliters");
        v.setDisplayOrder(1);
        v.setValue("mL");
        v.setValueSet(vs);
        return v;
    }

    private Value cubicCentimeters(ValueSet vs) {
        Value v = new Value();
        v.setId(8L);
        v.setCode("cc");
        v.setDisplay("cubic centimeters");
        v.setDisplayOrder(2);
        v.setValue("cc");
        v.setValueSet(vs);
        return v;
    }
}

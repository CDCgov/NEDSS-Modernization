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
import java.util.UUID;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementGroupEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementRef;
import gov.cdc.nbs.questionbank.entities.DisplayGroupRef;
import gov.cdc.nbs.questionbank.entities.DropdownQuestionEntity;
import gov.cdc.nbs.questionbank.entities.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entities.QuestionnaireEntity;
import gov.cdc.nbs.questionbank.entities.Reference;
import gov.cdc.nbs.questionbank.entities.TextEntity;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entities.ValueEntity;
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
        assertEquals(0, q.elements().size());
    }

    @Test
    void should_return_questionnaire_with_content() {
        Questionnaire q = questionnaireMapper.toQuestionnaire(fullEntity());
        assertNotNull(q);
        assertEquals(6, q.elements().size());
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
    void toElement_should_return_text_element() {
        DisplayElementRef ref = displayRef(textEntity(), 1);
        Questionnaire.Element element = questionnaireMapper.toElement(ref);
        assertThat(element).isInstanceOf(Questionnaire.Text.class);
    }

    @Test
    void toElement_should_return_section() {
        DisplayElementGroupEntity entity = groupElementEntity();
        DisplayGroupRef ref = displayGroupRef(entity, 1);
        Questionnaire.Section element = (Section) questionnaireMapper.toElement(ref);
        assertEquals(entity.getId(), element.id());
        assertEquals(entity.getLabel(), element.label());
        TextEntity textEntity = (TextEntity) entity.getElements().get(0);
        Questionnaire.Text text = (Text) element.elements().get(0);
        assertEquals(textEntity.getText(), text.text());
        assertEquals(textEntity.getId(), text.id());
        assertEquals(textEntity.getVersion(), text.version());
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
    void toDisplayElement_should_return_Text() {
        TextEntity entity = textEntity();
        Questionnaire.Text element = (Text) questionnaireMapper.toDisplayElement(entity);
        assertEquals(entity.getText(), element.text());
        assertEquals(entity.getId(), element.id());
        assertEquals(entity.getVersion(), element.version());
    }

    @Test
    void toDisplayElement_should_return_TextQuestion() {
        TextQuestionEntity entity = textQuestionEntity();
        Questionnaire.TextQuestion element = (TextQuestion) questionnaireMapper.toDisplayElement(entity);

        assertEquals(entity.getLabel(), element.label());
        assertEquals(entity.getId(), element.id());
        assertEquals(entity.getVersion(), element.version());
        assertEquals(entity.getMaxLength(), element.maxLength());
        assertEquals(entity.getPlaceholder(), element.placeholder());
        assertEquals(entity.getTooltip(), element.tooltip());
    }

    @Test
    void toDisplayElement_should_return_NumericQuestion() {
        NumericQuestionEntity entity = numericQuestionEntity();
        Questionnaire.NumericQuestion element = (NumericQuestion) questionnaireMapper.toDisplayElement(entity);
        assertEquals(entity.getLabel(), element.label());
        assertEquals(entity.getId(), element.id());
        assertEquals(entity.getVersion(), element.version());
        assertEquals(entity.getMaxValue(), element.maxValue());
        assertEquals(entity.getMinValue(), element.minValue());
        assertEquals(entity.getTooltip(), element.tooltip());

        assertEquals(entity.getUnitsSet().getName(), element.unitOptions().name());
        assertEquals(entity.getUnitsSet().getDescription(), element.unitOptions().description());

        entity.getUnitsSet().getValues().forEach(v -> {
            Optional<Option> o = element.unitOptions().options().stream().filter(option -> {
                return option.display().equals(v.getDisplay()) &&
                        option.id() == v.getId() &&
                        option.value().equals(v.getValue());
            }).findFirst();
            assertTrue(o.isPresent());
        });
    }

    @Test
    void toDisplayElement_should_return_DateQuestion() {
        DateQuestionEntity entity = dateQuestionEntity();
        Questionnaire.DateQuestion element = (DateQuestion) questionnaireMapper.toDisplayElement(entity);
        assertEquals(entity.getId(), element.id());
        assertEquals(entity.getVersion(), element.version());
        assertEquals(entity.getLabel(), element.label());
        assertEquals(entity.getTooltip(), element.tooltip());
        assertEquals(entity.isAllowFuture(), element.allowFutureDates());
    }

    @Test
    void toDisplayElement_should_return_DropDownQuestion() {
        DropdownQuestionEntity entity = dropDownQuestionEntity();
        Questionnaire.DropDownQuestion element = (DropDownQuestion) questionnaireMapper.toDisplayElement(entity);
        assertEquals(entity.getId(), element.id());
        assertEquals(entity.getVersion(), element.version());
        assertEquals(entity.getLabel(), element.label());
        assertEquals(entity.getTooltip(), element.tooltip());
        assertEquals(entity.isMultiSelect(), element.isMultiSelect());
        assertEquals(entity.getDefaultAnswer().getDisplay(), element.defaultOption().display());
        assertEquals(entity.getDefaultAnswer().getValue(), element.defaultOption().value());
        assertEquals(entity.getValueSet().getName(), element.optionSet().name());
        assertEquals(entity.getValueSet().getId(), element.optionSet().id());
        assertEquals(entity.getValueSet().getDescription(), element.optionSet().description());

        entity.getValueSet().getValues().forEach(v -> {
            Optional<Option> o = element.optionSet().options().stream().filter(option -> {
                return option.display().equals(v.getDisplay()) &&
                        option.id() == v.getId() &&
                        option.value().equals(v.getValue());
            }).findFirst();
            assertTrue(o.isPresent());
        });
    }

    private QuestionnaireEntity emptyEntity() {
        QuestionnaireEntity entity = new QuestionnaireEntity();
        entity.setId(UUID.randomUUID());
        entity.setConditionCodes(Arrays.asList("condition"));
        entity.setReferences(new ArrayList<>());
        entity.setRules(new ArrayList<>());
        return entity;
    }

    private QuestionnaireEntity fullEntity() {
        QuestionnaireEntity entity = new QuestionnaireEntity();
        entity.setId(UUID.randomUUID());
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
        group.setId(UUID.randomUUID());
        return group;
    }

    private DisplayElementRef displayRef(DisplayElementEntity entity, Integer order) {
        var ref = new DisplayElementRef();
        ref.setDisplayElementEntity(entity);
        ref.setDisplayOrder(order);
        return ref;
    }

    private TextEntity textEntity() {
        var e = new TextEntity();
        e.setId(UUID.randomUUID());
        e.setVersion(1);
        e.setText("Text element");
        return e;
    }

    private DropdownQuestionEntity dropDownQuestionEntity() {
        var e = new DropdownQuestionEntity();
        e.setId(UUID.randomUUID());
        e.setVersion(1);
        e.setDefaultAnswer(milliliters(null));
        e.setLabel("Dropdown question label");
        e.setMultiSelect(true);
        e.setValueSet(unitValueSet());
        return e;
    }

    private DateQuestionEntity dateQuestionEntity() {
        var e = new DateQuestionEntity();
        e.setId(UUID.randomUUID());
        e.setVersion(1);
        e.setAllowFuture(true);
        e.setLabel("Date question label");
        e.setTooltip("Date question tooltip");
        return e;
    }

    private TextQuestionEntity textQuestionEntity() {
        var e = new TextQuestionEntity();
        e.setId(UUID.randomUUID());
        e.setVersion(1);
        e.setLabel("Text question label");
        e.setMaxLength(100);
        e.setPlaceholder("Text Question Placeholder");
        e.setTooltip("Text Question tooltip");
        return e;
    }

    private NumericQuestionEntity numericQuestionEntity() {
        var e = new NumericQuestionEntity();
        e.setId(UUID.randomUUID());
        e.setVersion(1);
        e.setLabel("Numeric question label");
        e.setTooltip("Numeric question tooltip");
        e.setMaxValue(100);
        e.setMinValue(15);
        e.setUnitsSet(unitValueSet());
        return e;
    }

    private ValueSet unitValueSet() {
        var v = new ValueSet();
        v.setId(UUID.randomUUID());
        v.setCode("unit-code");
        v.setDescription("Value set for units");
        v.setName("Units");
        v.setType(ValueSetType.LOCAL);
        v.setValues(unitValues(v));
        return v;
    }

    private List<ValueEntity> unitValues(ValueSet v) {
        var values = new ArrayList<ValueEntity>();
        values.add(milliliters(v));
        values.add(cubicCentimeters(v));
        return values;
    }

    private ValueEntity milliliters(ValueSet vs) {
        ValueEntity v = new ValueEntity();
        v.setId(UUID.randomUUID());
        v.setCode("m");
        v.setDisplay("milliliters");
        v.setDisplayOrder(1);
        v.setValue("mL");
        v.setValueSet(vs);
        return v;
    }

    private ValueEntity cubicCentimeters(ValueSet vs) {
        ValueEntity v = new ValueEntity();
        v.setId(UUID.randomUUID());
        v.setCode("cc");
        v.setDisplay("cubic centimeters");
        v.setDisplayOrder(2);
        v.setValue("cc");
        v.setValueSet(vs);
        return v;
    }
}

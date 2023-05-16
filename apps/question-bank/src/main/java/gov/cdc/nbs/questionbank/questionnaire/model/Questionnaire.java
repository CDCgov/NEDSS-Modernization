package gov.cdc.nbs.questionbank.questionnaire.model;

import java.util.Collection;
import java.util.List;

public record Questionnaire(
        long id,
        List<String> conditions,
        List<Element> elements) {

    public sealed interface Element permits Section, DisplayElement {
        public long id = 0L;
    }

    public sealed interface DisplayElement extends Element
            permits Text,
            TextQuestion,
            NumericQuestion,
            DateQuestion,
            DropDownQuestion {
    }

    public record Section(
            long id,
            String label,
            List<DisplayElement> elements) implements Element {
    }

    public record Text(
            long id,
            String text) implements DisplayElement {
    }

    public record TextQuestion(
            long id,
            String label,
            String tooltip,
            Integer maxLength,
            String placeholder) implements DisplayElement {
    }

    public record NumericQuestion(
            long id,
            String label,
            String tooltip,
            Integer minValue,
            Integer maxValue,
            OptionSet unitOptions) implements DisplayElement {
    }

    public record DropDownQuestion(
            long id,
            String label,
            String tooltip,
            OptionSet optionSet,
            Option defaultOption,
            boolean isMultiSelect) implements DisplayElement {
    }

    public record DateQuestion(
            long id,
            String label,
            String tooltip,
            boolean allowFutureDates) implements DisplayElement {
    }

    public record OptionSet(
            long id,
            String name,
            String description,
            Collection<Option> options) {
    }

    public record Option(
            long id,
            String value,
            String display) {
    }

}

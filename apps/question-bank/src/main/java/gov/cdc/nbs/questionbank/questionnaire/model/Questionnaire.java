package gov.cdc.nbs.questionbank.questionnaire.model;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import gov.cdc.nbs.questionbank.entities.enums.CodeSet;

public record Questionnaire(
        UUID id,
        List<String> conditions,
        List<Tab> tabs) {

    public record Tab(String name, List<Element> elements) {
    }
    public sealed interface Element permits Section, DisplayElement {

    }

    public sealed interface DisplayElement extends Element
            permits Text,
            Question {
        UUID id();

        Integer version();
    }

    public sealed interface Question extends DisplayElement
            permits TextQuestion,
            NumericQuestion,
            DateQuestion,
            DropdownQuestion {
        CodeSet codeSet();

    }

    public record Section(
            UUID id,
            String label,
            List<DisplayElement> elements) implements Element {
    }

    public record Text(
            UUID id,
            Integer version,
            String text) implements DisplayElement {
    }

    public record TextQuestion(
            UUID id,
            Integer version,
            String label,
            String tooltip,
            Integer maxLength,
            String placeholder,
            String defaultValue,
            CodeSet codeSet) implements Question {
    }

    public record NumericQuestion(
            UUID id,
            Integer version,
            String label,
            String tooltip,
            Integer minValue,
            Integer maxValue,
            Integer defaultValue,
            OptionSet unitOptions,
            CodeSet codeSet) implements Question {
    }

    public record DropdownQuestion(
            UUID id,
            Integer version,
            String label,
            String tooltip,
            OptionSet optionSet,
            Option defaultOption,
            boolean isMultiSelect,
            CodeSet codeSet) implements Question {
    }

    public record DateQuestion(
            UUID id,
            Integer version,
            String label,
            String tooltip,
            boolean allowFutureDates,
            CodeSet codeSet) implements Question {
    }

    public record OptionSet(
            UUID id,
            String name,
            String description,
            Collection<Option> options) {
    }

    public record Option(
            UUID id,
            String value,
            String display) {
    }

}

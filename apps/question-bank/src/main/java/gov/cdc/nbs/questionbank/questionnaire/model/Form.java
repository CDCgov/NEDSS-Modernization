package gov.cdc.nbs.questionbank.questionnaire.model;

import java.util.Collection;

public record Form(
        long id,
        Collection<String> conditions,
        String type,
        Collection<Section> sections) {

    public record Section(
            long id,
            String label,
            Collection<Element> elements) {
    }

    public interface Element {
        public long id = 0L;
        public long version = 0L;
        public String label = "";
        public String tooltip = "";
        public boolean required = false;
    }

    public record TextElement(
            Integer maxLength,
            String placeholder) implements Element {
    }

    public record NumericElement(
            Integer minValue,
            Integer maxValue,
            OptionSet unitOptions) {
    }

    public record DropDownElement(
            OptionSet optionSet,
            Option defaultOption,
            boolean isMultiSelect) {
    }

    public record DateElement(
            boolean allowFutureDates) {
    }

    public record OptionSet(
            long id,
            long version,
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

package gov.cdc.nbs.questionbank.question.request;

import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import lombok.Getter;
import lombok.Setter;
import gov.cdc.nbs.questionbank.question.model.DisplayOption;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public abstract class CreateQuestionRequest {
        private CodeSet codeSet;
        private String uniqueId;
        private String uniqueName;
        private String subgroup;
        private String description;
        private String label;
        private String tooltip;
        private Long displayControl;
        private ReportingInfo dataMartInfo;
        private MessagingInfo messagingInfo;
        private String adminComments;

        public record ReportingInfo(
                        String reportLabel,
                        String defaultRdbTableName,
                        String rdbColumnName,
                        String dataMartColumnName) {
        }

        public record MessagingInfo(
                        boolean includedInMessage,
                        String messageVariableId,
                        String labelInMessage,
                        String codeSystem,
                        boolean requiredInMessage,
                        String hl7DataType) {
        }

        public enum CodedQuestionType {
                SINGLE_SELECT(1007, "Single-Select (Drop down)"),
                MULTI_SELECT(1013, "Multi-Select (List Box)"),
                READONLY_SINGLE_SELECT_SAVE(1024, "Readonly single-select save"),
                READONLY_MULTI_SELECT_SAVE(1025, "Readonly multi-select save"),
                READONLY_SINGLE_SELECT_NO_SAVE(1027, "Readonly single-select no save"),
                READONLY_MULTI_SELECT_NO_SAVE(1028, "Readonly multi-select no save"),
                CODE_LOOKUP(1031, "Code Lookup");

                private final long key;
                private final String value;

                CodedQuestionType(long key, String value) {
                        this.key = key;
                        this.value = value;
                }

                public static List<DisplayOption> getDisplayOptions() {
                        return Arrays.stream(values()).map(CodedQuestionType::toDisplayOption).toList();
                }

                private DisplayOption toDisplayOption() {
                        return new DisplayOption(key,value);
                }
        }

        public enum DateQuestionType {
                USER_ENTERED(1008, "User entered text, number, or date"),
                READONLY_USER_ENTERED(1026, "Readonly User entered text, number, or date"),
                READONLY_USER_NO_SAVE(1029, "Readonly User text, number, or date no save");
                private final long key;
                private final String value;

                DateQuestionType(long key, String value) {
                        this.key = key;
                        this.value = value;
                }

                public static List<DisplayOption> getDisplayOptions() {
                        return Arrays.stream(values()).map(DateQuestionType::toDisplayOption).toList();
                }

                private DisplayOption toDisplayOption() {
                        return new DisplayOption(key,value);
                }
        }

        public enum NumericQuestionType {
                USER_ENTERED(1008, "User entered text, number, or date"),
                READONLY_USER_ENTERED(1026, "Readonly User entered text, number, or date"),
                READONLY_USER_NO_SAVE(1029, "Readonly User text, number, or date no save");

                private final long key;
                private final String value;

                NumericQuestionType(long key, String value) {
                        this.key = key;
                        this.value = value;
                }

                public static List<DisplayOption> getDisplayOptions() {
                        return Arrays.stream(values()).map(NumericQuestionType::toDisplayOption).toList();
                }

                private DisplayOption toDisplayOption() {
                        return new DisplayOption(key,value);
                }

        }

        public enum TextQuestionType {
                USER_ENTERED(1008, "User entered text, number, or date"),
                MULTI_LINE_USER_ENTERED(1009, "Multi-line user-entered text"),
                MULTI_LINE_NOTES(1019, "Multi-line Notes with User/Date Stamp"),
                READONLY_USER_ENTERED(1026, "Readonly User entered text, number, or date"),
                READONLY_USER_NO_SAVE(1029, "Readonly User text, number, or date no save");

                private final long key;
                private final String value;

                TextQuestionType(long key, String value) {
                        this.key = key;
                        this.value = value;
                }

                public static List<DisplayOption> getDisplayOptions() {
                        return Arrays.stream(values()).map(TextQuestionType::toDisplayOption).toList();
                }

                private DisplayOption toDisplayOption() {
                        return new DisplayOption(key,value);
                }

        }


}

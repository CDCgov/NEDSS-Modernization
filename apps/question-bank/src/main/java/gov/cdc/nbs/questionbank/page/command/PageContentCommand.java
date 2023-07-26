package gov.cdc.nbs.questionbank.page.command;

import java.time.Instant;
import gov.cdc.nbs.questionbank.entity.WaTemplate;

public sealed interface PageContentCommand {
    long userId();

    Instant requestedOn();

    public sealed interface AddQuestion extends PageContentCommand {
        WaTemplate page();

        Long uiComponent();

        String label();

        String tooltip();

        Integer orderNumber();

        String adminComment();

        String dataLocation();

        String description();

        String questionType();

        String questionName();

        String questionIdentifier();

        String questionOid();

        String questionOidSystem();

        String groupName();

        long userId();

        Instant requestedOn();

        public record AddTextQuestion(
                // Text question fields
                String defaultValue,
                String mask,
                String fieldLength,

                // general fields
                WaTemplate page,
                Long uiComponent,
                String label,
                String tooltip,
                Integer orderNumber,
                String adminComment,
                String dataLocation,
                String description,
                String questionType,
                String questionName,
                String questionIdentifier,
                String questionOid,
                String questionOidSystem,
                String groupName,
                long userId,
                Instant requestedOn) implements AddQuestion {
        }

        public record AddDateQuestion(
                // Date question fields
                String mask,
                Character futureDateIndicator,

                // general fields
                WaTemplate page,
                Long uiComponent,
                String label,
                String tooltip,
                Integer orderNumber,
                String adminComment,
                String dataLocation,
                String description,
                String questionType,
                String questionName,
                String questionIdentifier,
                String questionOid,
                String questionOidSystem,
                String groupName,
                long userId,
                Instant requestedOn) implements AddQuestion {
        }

        public record AddNumericQuestion(
                // Numeric question fields
                String mask,
                String fieldLength,
                String defaultValue,
                Long minValue,
                Long maxValue,
                String unitTypeCd,
                String unitValue,

                // general fields
                WaTemplate page,
                Long uiComponent,
                String label,
                String tooltip,
                Integer orderNumber,
                String adminComment,
                String dataLocation,
                String description,
                String questionType,
                String questionName,
                String questionIdentifier,
                String questionOid,
                String questionOidSystem,
                String groupName,
                long userId,
                Instant requestedOn) implements AddQuestion {
        }

        public record AddCodedQuestion(
                // Coded question fields
                Long codeSetGroupId,
                String defaultValue,

                // general fields
                WaTemplate page,
                Long uiComponent,
                String label,
                String tooltip,
                Integer orderNumber,
                String adminComment,
                String dataLocation,
                String description,
                String questionType,
                String questionName,
                String questionIdentifier,
                String questionOid,
                String questionOidSystem,
                String groupName,
                long userId,
                Instant requestedOn) implements AddQuestion {
        }
    }


}

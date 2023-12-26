package gov.cdc.nbs.questionbank.page.command;

import java.time.Instant;
import java.util.List;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;

public sealed interface PageContentCommand {
        long userId();

        Instant requestedOn();

        public record AddLineSeparator(
                        WaTemplate page,
                        Integer orderNumber,
                        long userId,
                        String adminComments,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record UpdateDefaultStaticElement(
                        long userId,
                        String adminComments,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record AddReadOnlyParticipantsList(
                        WaTemplate page,
                        Integer orderNumber,
                        long userId,
                        String adminComments,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record AddOrignalElectronicDocList(
                        WaTemplate page,
                        Integer orderNumber,
                        long userId,
                        String adminComments,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record AddHyperLink(
                        WaTemplate page,
                        Integer orderNumber,
                        long userId,
                        String adminComments,
                        String label,
                        String linkUrl,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record UpdateHyperlink(
                        long userId,
                        String adminComments,
                        String label,
                        String linkUrl,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record AddReadOnlyComments(
                        WaTemplate page,
                        Integer orderNumber,
                        long userId,
                        String comments,
                        String adminComments,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record UpdateReadOnlyComments(
                        long userId,
                        String comments,
                        String adminComments,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record AddQuestion(
                        Long page,
                        WaQuestion question,
                        long subsection,
                        long userId,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record AddTab(
                        String label,
                        boolean visible,
                        String identifier,
                        long userId,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record UpdateTab(
                        String label,
                        boolean visible,
                        long tab,
                        long userId,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record DeleteTab(
                        long tabId,
                        long userId,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record AddSection(
                        String label,
                        boolean visible,
                        String identifier,
                        long tab,
                        long userId,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record UpdateSection(
                        String label,
                        boolean visible,
                        long sectionId,
                        long userId,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record DeleteSection(
                        long setionId,
                        long userId,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record AddSubsection(
                        String label,
                        boolean visible,
                        String identifier,
                        long section,
                        long userId,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record UpdateSubsection(
                        String label,
                        boolean visible,
                        long subsection,
                        long userId,
                        Instant requestedOn) implements PageContentCommand {
        }

        public record DeleteSubsection(
                        long subsectionId,
                        long userId,
                        Instant requestedOn) implements PageContentCommand {

        }

        public record GroupSubsection(
                long subsection,
                String blockName,
                List<GroupSubSectionRequest.Batch> batches,
                long userId,
                Instant requestedOn) implements PageContentCommand {
        }

        public record UnGroupSubsection(
                long subsection,
                List<Long> batches,
                long userId,
                Instant requestedOn) implements PageContentCommand {
        }

        public record DeleteQuestion(
                Long page,
                WaUiMetadata question,
                long userId,
                Instant requestedOn) implements PageContentCommand {
        }

        public record AddRule(
                String ruleCd,
                String errMsgTxt,
                String recordStatusCd,
                String javascriptFunction,
                String javascriptFunctionNm,
                long userId,
                Instant requestedOn) implements PageContentCommand {
        }

        public record DeleteRule(
                long ruleId,
                long userId,
                Instant requestedOn)implements PageContentCommand {
        }



}

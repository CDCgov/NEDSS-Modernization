package gov.cdc.nbs.questionbank.question.response;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ValueSetConcept {

    private String localCode;
    private String uiDisplayName;
    private String conceptCode;
    private String messagingConceptName;
    private String codeSystemName;
    private String status;
    private LocalDate effectiveFrom;

}

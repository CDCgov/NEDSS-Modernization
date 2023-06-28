package gov.cdc.nbs.questionbank.question.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Concept {

    private String code;
    private String display;

}
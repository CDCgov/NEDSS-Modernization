package gov.cdc.nbs.questionbank.phin.model;

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

package gov.cdc.nbs.questionbank.phin.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Telecom {

    private String system;
    private String value;

}

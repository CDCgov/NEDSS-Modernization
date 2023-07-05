package gov.cdc.nbs.questionbank.phin.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Link {

    private String relation;
    private String url;

}

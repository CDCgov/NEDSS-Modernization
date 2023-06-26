package gov.cdc.nbs.questionbank.question.model;

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

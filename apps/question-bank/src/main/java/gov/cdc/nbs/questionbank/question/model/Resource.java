package gov.cdc.nbs.questionbank.question.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Resource {

    private String resourceType;
    private String id;
    private Text text;
    private String url;
    private List<Identifier> identifier;

}

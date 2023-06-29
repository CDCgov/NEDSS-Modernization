package gov.cdc.nbs.questionbank.question.response;

import gov.cdc.nbs.questionbank.question.model.Entry;
import gov.cdc.nbs.questionbank.question.model.Link;
import gov.cdc.nbs.questionbank.question.model.Meta;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ValueSetApiResponse {

    private String resourceType;
    private String id;
    private Meta meta;
    private String type;
    private long total;
    private List<Link> link;
    private List<Entry> entry;

}

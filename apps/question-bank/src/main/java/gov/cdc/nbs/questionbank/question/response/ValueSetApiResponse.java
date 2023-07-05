package gov.cdc.nbs.questionbank.question.response;

import lombok.*;

import java.util.List;
import gov.cdc.nbs.questionbank.phin.model.Entry;
import gov.cdc.nbs.questionbank.phin.model.Link;
import gov.cdc.nbs.questionbank.phin.model.Meta;

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

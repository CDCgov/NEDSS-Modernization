package gov.cdc.nbs.questionbank.question.response;

import gov.cdc.nbs.questionbank.question.model.Compose;
import gov.cdc.nbs.questionbank.question.model.Contact;
import gov.cdc.nbs.questionbank.question.model.Identifier;
import gov.cdc.nbs.questionbank.question.model.Text;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PhinvadsValueSetByIDData {

    private String resourceType;
    private String id;
    private Text text;
    private String url;
    private List<Identifier> identifier;
    private String version;
    private String name;
    private String title;
    private String status;
    private boolean experimental;
    private LocalDate date;
    private String publisher;
    private List<Contact> contact;
    private String description;
    private Compose compose;

    private int clientRespCode;


}

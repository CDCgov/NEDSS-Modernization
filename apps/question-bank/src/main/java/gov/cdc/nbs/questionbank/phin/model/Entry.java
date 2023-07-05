package gov.cdc.nbs.questionbank.phin.model;

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
public class Entry {

    private String fullUrl;
    private Resource resource;
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

}

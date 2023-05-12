package gov.cdc.nbs.questionbank.entities;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "display_group", catalog = "question_bank")
public class DisplayGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "label", nullable = false)
    private String label;

    @ElementCollection
    @CollectionTable(
            name = "display_group_elements",
            joinColumns = @JoinColumn(name = "element_id"))
    private List<DisplayElement> elements;

}

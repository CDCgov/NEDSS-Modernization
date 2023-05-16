package gov.cdc.nbs.questionbank.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "display_element_group", catalog = "question_bank")
public class DisplayElementGroupEntity implements Serializable {

    public DisplayElementGroupEntity(String label, List<DisplayElementEntity> elements) {
        this.version = 1;
        this.label = label;
        this.elements = elements;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Id
    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "label", nullable = false)
    private String label;

    @OrderBy("display_order")
    @OrderColumn(name = "display_order")
    @ElementCollection
    @JoinTable(
            name = "display_element_group_elements",
            inverseJoinColumns = {@JoinColumn(name = "element_id"), @JoinColumn(name = "element_version")},
            joinColumns = {@JoinColumn(name = "group_id"), @JoinColumn(name = "group_version")})
    private List<DisplayElementEntity> elements;

}

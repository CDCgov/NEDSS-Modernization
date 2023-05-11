package gov.cdc.nbs.questionbank.entities;



import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "value_set", catalog = "question_bank")
public class ValueSet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Id
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "code", nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ValueSetType type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 300)
    private String description;

    @OneToMany(mappedBy = "valueSet", fetch = FetchType.LAZY)
    private Set<Value> values;

    @Embedded
    private Audit audit;

    public static enum ValueSetType {
        LOCAL,
        PHIN
    }

}

package gov.cdc.nbs.questionbank.entities;



import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "display_element", catalog = "question_bank")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@IdClass(DisplayElementId.class)
@DiscriminatorColumn(name = "display_type", discriminatorType = DiscriminatorType.STRING)
public abstract class DisplayElementEntity implements Serializable {

    @Id
    @GenericGenerator(name = "UUID", strategy = "gov.cdc.nbs.questionbank.entities.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @Column(name = "id", columnDefinition = "uniqueidentifier", nullable = false)
    private UUID id;

    @Id
    @Column(name = "version", nullable = false)
    private Integer version;

    @Embedded
    private AuditInfo audit;

    public abstract String getDisplayType();
}

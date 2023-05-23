package gov.cdc.nbs.questionbank.entities;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "[value]", catalog = "question_bank")
public class ValueEntity implements Serializable {

    @Id
    @GenericGenerator(name = "UUID", strategy = "gov.cdc.nbs.questionbank.entities.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @Column(name = "id", columnDefinition = "uniqueidentifier", nullable = false)
    private UUID id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "display", nullable = false)
    private String display;

    @Column(name = "[value]", nullable = false)
    private String value;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "value_set_id")
    private ValueSet valueSet;
}

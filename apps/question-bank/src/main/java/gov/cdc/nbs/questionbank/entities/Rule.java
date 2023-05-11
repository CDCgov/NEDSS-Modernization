package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "[rule]", catalog = "question_bank")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({@JoinColumn(name = "id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "version", nullable = false, insertable = false, updatable = false)})
    private Question sourceQuestion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({@JoinColumn(name = "id", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "version", nullable = false, insertable = false, updatable = false)})
    private Question targetQuestion;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @Column(name = "expression", nullable = false)
    private Expression expression;

    public static enum Type {
        DATE_COMPARE
    }

    public static enum Expression {
        GREATER_THAN,
        GREATER_THAN_OR_EQUAL_TO,
        LESS_THAN,
        LESS_THAN_OR_EQUAL_TO,
    }
}

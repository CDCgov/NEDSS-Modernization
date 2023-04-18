package gov.cdc.nbs.questionbank.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "question", catalog = "question_bank")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "label", length = 300)
    private String label;

    @Column(name = "tooltip", length = 200)
    private String tooltip;

    @Column(name = "required")
    private boolean required;

    @ManyToOne
    @JoinColumn(name = "answer_set_id")
    private AnswerSet answerSet;

    @ManyToOne
    @JoinColumn(name = "default_answer_id")
    private Answer defaultAnswer;

    @Column(name = "multiselect")
    private boolean multiSelect;

    @Column(name = "max_length")
    private Integer maxLength;

    @Column(name = "placeholder", length = 100)
    private String placeholder;

    @Column(name = "min_value")
    private Integer minValue;

    @Column(name = "max_value")
    private Integer maxValue;

    @Column(name = "units_set")
    private Long unitsSet;

    @Column(name = "allow_future_dates")
    private boolean allowFuture;
}

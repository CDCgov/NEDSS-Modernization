package gov.cdc.nbs.questionbank.entities;



import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "answer_set", catalog = "question_bank", schema = "question_bank")
public class AnswerSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @OneToMany(mappedBy = "answerSet", fetch = FetchType.LAZY)
    private Set<Answer> answers;

}

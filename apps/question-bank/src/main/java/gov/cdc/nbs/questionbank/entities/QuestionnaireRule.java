package gov.cdc.nbs.questionbank.entities;

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

@Entity
@Table(name = "questionnaire_rule", catalog = "question_bank")
public class QuestionnaireRule {

    @Id
    @GenericGenerator(name = "UUID", strategy = "gov.cdc.nbs.questionbank.entities.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @Column(name = "id", columnDefinition = "uniqueidentifier", nullable = false)
    private UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id")
    private QuestionnaireEntity questionnaire;

}

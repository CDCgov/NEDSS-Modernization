package gov.cdc.nbs.questionbank.questionnaire;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import gov.cdc.nbs.questionbank.entities.Audit;
import gov.cdc.nbs.questionbank.entities.QuestionGroup;
import gov.cdc.nbs.questionbank.entities.Rule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questionnaire", catalog = "question_bank")
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "questionnaire_conditions",
            joinColumns = @JoinColumn(name = "questionnaire_id"))
    private List<String> conditionCodes;

    @Column(name = "questionnaire_type", length = 100, nullable = false)
    private String questionnaireType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "questionnaire_question_group",
            joinColumns = @JoinColumn(name = "questionnaire_id"),
            inverseJoinColumns = @JoinColumn(name = "question_group_id"))
    private List<QuestionGroup> questionGroups;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "questionnaire_rules",
            joinColumns = @JoinColumn(name = "questionnaire_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id"))
    private List<Rule> rules;

    @Embedded
    private Audit audit;
}

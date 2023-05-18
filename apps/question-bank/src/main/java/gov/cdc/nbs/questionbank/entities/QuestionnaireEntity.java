package gov.cdc.nbs.questionbank.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questionnaire", catalog = "question_bank")
public class QuestionnaireEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @CollectionTable(
            name = "questionnaire_conditions",
            joinColumns = @JoinColumn(name = "questionnaire_id"))
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> conditionCodes;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @OrderBy("display_order")
    @OneToMany(mappedBy = "questionnaire", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reference> references;

    @OneToMany(mappedBy = "questionnaire", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionnaireRule> rules;

}

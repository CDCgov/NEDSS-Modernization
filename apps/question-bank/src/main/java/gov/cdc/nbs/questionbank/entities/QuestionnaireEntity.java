package gov.cdc.nbs.questionbank.entities;

import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(VersionId.class)
@Table(name = "questionnaire", catalog = "question_bank")
public class QuestionnaireEntity {

    @Id
    @GenericGenerator(name = "UUID", strategy = "gov.cdc.nbs.questionbank.entities.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @Column(name = "id", columnDefinition = "uniqueidentifier", nullable = false)
    private UUID id;

    @Id
    @Column(name = "version", nullable = false)
    private Integer version;

    @CollectionTable(
            name = "questionnaire_conditions",
            joinColumns = {
                    @JoinColumn(name = "questionnaire_id"),
                    @JoinColumn(name = "questionnaire_version")
            })
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> conditionCodes;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @OrderBy("display_order")
    @OneToMany(mappedBy = "questionnaire", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TabEntity> tabs;

    @OneToMany(mappedBy = "questionnaire", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuestionnaireRule> rules;

}

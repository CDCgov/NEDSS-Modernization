package gov.cdc.nbs.entity.syphilis_qb;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;



@Entity
@Table(name = "questionnaire")
public class Questionnaire {
    @Id
    @GeneratedValue
    @Column(name = "questionnaire_id", nullable = false)
    private Long id;

    @Column(name = "condition_uid")
    private Long conditionUid;

    @Column(name = "questionnaire_type")
    private String questionnaireType;
}
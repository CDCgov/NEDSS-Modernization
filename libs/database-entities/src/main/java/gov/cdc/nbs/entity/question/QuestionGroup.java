package gov.cdc.nbs.entity.question;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;


@Entity
@Table(name = "question_group")
public class QuestionGroup {
    @Id
    @GeneratedValue
    @Column(name = "question_set_uid", nullable = false)
    private Long questionGroupId;

    @Column(name = "label")
    private String label;

    @OneToMany(mappedBy = "question_set_uid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questions;

}
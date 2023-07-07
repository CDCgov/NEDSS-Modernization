package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ParticipationTypeId implements Serializable {
    private static final long serialVersionUID = -5973895132673454942L;
    @Column(name = "act_class_cd", nullable = false, length = 20)
    private String actClassCd;

    @Column(name = "subject_class_cd", nullable = false, length = 20)
    private String subjectClassCd;

    @Column(name = "type_cd", nullable = false, length = 50)
    private String typeCd;

    @Column(name = "question_identifier", nullable = false, length = 50)
    private String questionIdentifier;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ParticipationTypeId entity = (ParticipationTypeId) o;
        return Objects.equals(this.subjectClassCd, entity.subjectClassCd) &&
                Objects.equals(this.typeCd, entity.typeCd) &&
                Objects.equals(this.questionIdentifier, entity.questionIdentifier) &&
                Objects.equals(this.actClassCd, entity.actClassCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectClassCd, typeCd, questionIdentifier, actClassCd);
    }

}

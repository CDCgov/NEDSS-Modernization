package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue(DateQuestionEntity.TYPE)
public class DateQuestionEntity extends DisplayElementEntity {
    static final String TYPE = "date_question";

    @Column(name = "label", length = 300)
    private String label;

    @Column(name = "tooltip", length = 200)
    private String tooltip;

    @Column(name = "allow_future_dates", nullable = false)
    private boolean allowFuture;

    @Override
    public String getDisplayType() {
        return TYPE;
    }
}

package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(TextEntity.TYPE)
public class TextEntity extends DisplayElementEntity {
    static final String TYPE = "text";

    @Column(name = "text")
    private String text;

    @Override
    public String getDisplayType() {
        return TYPE;
    }

}

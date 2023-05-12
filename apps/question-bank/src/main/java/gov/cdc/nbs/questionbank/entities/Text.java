package gov.cdc.nbs.questionbank.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(Text.type)
public class Text extends DisplayElement {
    static final String type = "text";

    @Column(name = "text")
    private String text;

    @Override
    public String getDisplayType() {
        return type;
    }

}

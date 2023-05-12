package gov.cdc.nbs.questionbank.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(DisplayElementRef.type)
public class DisplayElementRef extends ElementReference {
    static final String type = "display_element";

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "element_id")
    private DisplayElement element;

    @Override
    public String getElementType() {
        return type;
    }

}

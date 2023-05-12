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
@DiscriminatorValue(DisplayGroupRef.type)
public class DisplayGroupRef extends ElementReference {
    static final String type = "display_group";

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "element_group_id")
    private DisplayGroup displayGroup;

    @Override
    public String getElementType() {
        return type;
    }

}

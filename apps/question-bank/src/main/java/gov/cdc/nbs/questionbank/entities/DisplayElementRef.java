package gov.cdc.nbs.questionbank.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(DisplayElementRef.type)
public class DisplayElementRef extends Reference {
    static final String type = "display_element";

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({@JoinColumn(name = "display_element_id"), @JoinColumn(name = "display_element_version")})
    private DisplayElementEntity displayElementEntity;

    @Override
    public String getReferenceType() {
        return type;
    }

}

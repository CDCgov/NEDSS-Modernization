package gov.cdc.nbs.questionbank.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(DisplayGroupRef.TYPE)
public class DisplayGroupRef extends Reference {
    static final String TYPE = "display_group";

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    @JoinColumn(name = "group_version")
    private DisplayElementGroupEntity displayGroup;

    @Override
    public String getReferenceType() {
        return TYPE;
    }

}

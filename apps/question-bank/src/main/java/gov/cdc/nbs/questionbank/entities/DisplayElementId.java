package gov.cdc.nbs.questionbank.entities;

import java.io.Serializable;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class DisplayElementId implements Serializable {

    private UUID id;

    private Integer version;

    public DisplayElementId() {
        this.version = 1;
    }

    public DisplayElementId(UUID id, Integer version) {
        this.id = id;
        this.version = version;
    }

}

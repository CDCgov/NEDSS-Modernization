package gov.cdc.nbs.questionbank.entities;

import java.io.Serializable;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class DisplayElementId implements Serializable {

    private Long id;

    private Integer version;

    public DisplayElementId() {
        this.version = 1;
    }

    public DisplayElementId(Long id, Integer version) {
        this.id = id;
        this.version = version;
    }

}

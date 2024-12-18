package gov.cdc.nbs.dibbs.nbs_deduplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
public class BaseContainer implements Serializable
{

    public BaseContainer() {
        // This constructor is intentionally left empty.
    }
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    protected boolean itNew;
    @JsonIgnore
    protected boolean itOld;
    @JsonIgnore
    protected boolean itDirty;
    @JsonIgnore
    protected boolean itDelete;
    @JsonIgnore
    protected String superClassType;
    @JsonIgnore
    protected transient Collection<Object> ldfs;
}

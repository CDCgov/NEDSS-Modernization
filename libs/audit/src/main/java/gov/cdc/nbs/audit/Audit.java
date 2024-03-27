package gov.cdc.nbs.audit;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.time.Instant;

@Embeddable
public class Audit {

    @Embedded
    private Added added;

    @Embedded
    private Changed changed;

    protected Audit() {

    }

    public Audit(final long who, final Instant when, final String why) {
        this.added = new Added(who, when, why);
        this.changed = new Changed(who, when);
    }

    public Audit(final long who, final Instant when) {
        this.added = new Added(who, when);
        this.changed = new Changed(who, when);
    }

    public void changed(final long who, final Instant when) {
        this.changed = new Changed(who, when);
    }

    public void changed(final long who, final Instant when, final String why) {
        this.changed = new Changed(who, when, why);
    }

    public Added added() {
        return added;
    }

    public Changed changed() {
        return changed;
    }
}

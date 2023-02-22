package gov.cdc.nbs.audit;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
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

    void changed(final long who, final Instant when, final String why ) {
        this.changed = new Changed(who, when, why);
    }

    public Added getAdded() {
        return added;
    }

    public Changed getChanged() {
        return changed;
    }
}

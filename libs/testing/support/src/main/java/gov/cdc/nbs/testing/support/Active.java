package gov.cdc.nbs.testing.support;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Active<I> {

    private I item;

    public void active(final I identifier) {
        this.item = identifier;
    }

    public void reset() {
        this.item = null;
    }

    public Optional<I> maybeActive() {
        return Optional.ofNullable(this.item);
    }

    public I active() {
        return maybeActive()
            .orElseThrow(() -> new IllegalStateException("there is nothing active"));
    }

}

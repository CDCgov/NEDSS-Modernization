package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Added;
import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.Changed;

import java.time.Instant;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class AuditAssertions {

  public static Consumer<Audit> added(final long by, final String when) {
    return added(by, Instant.parse(when));
  }

  public static Consumer<Audit> added(final long by, final Instant when) {
    return audit -> assertThat(audit)
        .describedAs("Added by %s at %s", by, when)
        .extracting(Audit::added)
        .returns(by, Added::addedBy)
        .returns(when, Added::addedOn);
  }

  public static Consumer<Audit> changed(final long by, final String when) {
    return changed(by, Instant.parse(when));
  }

  public static Consumer<Audit> changed(final long by, final Instant when) {
    return audit -> assertThat(audit)
        .describedAs("Changed by %s at %s", by, when)
        .extracting(Audit::changed)
        .returns(by, Changed::changedBy)
        .returns(when, Changed::changedOn);
  }

  private AuditAssertions() {

  }

}

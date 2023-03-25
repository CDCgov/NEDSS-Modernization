package gov.cdc.nbs.identity;

import java.util.concurrent.atomic.AtomicLong;

public class TestUniqueIdGenerator {

  private final AtomicLong next;
  private final String suffix;

  public TestUniqueIdGenerator(final MotherSettings settings) {
    long starting = settings.starting();
    this.suffix = settings.suffix();
    this.next = new AtomicLong(starting + 1);
  }

  public long next() {
    return this.next.incrementAndGet();
  }

  public String nextLocal(final String type) {
    return type + next() + this.suffix;
  }

}

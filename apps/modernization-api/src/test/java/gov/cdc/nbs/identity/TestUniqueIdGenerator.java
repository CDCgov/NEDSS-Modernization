package gov.cdc.nbs.identity;

import java.util.concurrent.atomic.AtomicLong;

public class TestUniqueIdGenerator {

  private final AtomicLong next;
  private final long starting;

  public TestUniqueIdGenerator(long starting) {
    this.starting = starting;
    this.next = new AtomicLong(starting + 1);
  }

  public long starting() {
    return starting;
  }

  public long next() {
    return this.next.incrementAndGet();
  }

  public String nextLocal(final String type) {
    return type + next() + "TEST";
  }

}

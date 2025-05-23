package gov.cdc.nbs.events.lab.tests;

import java.math.BigDecimal;

public record ResultedTest(
    String name,
    String status,
    String coded,
    BigDecimal numeric,
    String high,
    String low,
    String unit
) {
}

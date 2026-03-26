package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import java.math.BigDecimal;

public record LabTestSummary(
    String name,
    String status,
    String coded,
    BigDecimal numeric,
    String high,
    String low,
    String unit) {}

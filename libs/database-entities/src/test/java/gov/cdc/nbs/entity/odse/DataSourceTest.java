package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DataSourceTest {

  @Test
  void should_throw_exception_with_null_values() {
    Throwable exception = assertThrows(NullPointerException.class, () -> new DataSource(null));

    assertEquals("statusCd is marked non-null but is null", exception.getMessage());
  }

  @Test
  void should_create_complete_data_source() {
    Long id = 1L;
    Integer maxLen = 123;
    Character conditionSecurity = 'N';
    Character jurisdictionSecurity = 'Y';
    Character reportingFacilitySecurity = 'Y';
    String dataSourceName = "nbs_ods.PHCDemographic";
    String dataSourceTitle = "Disease Counts by County";
    String dataSourceTypeCode = "N220";
    String descTxt = "Disease Counts by County sumarized to the Case Level";
    LocalDate effectiveFromTime = LocalDate.parse("2020-03-03");
    LocalDate effectiveToTime = LocalDate.parse("2020-03-04");
    String orgAccessPermission = "N";
    String progAreaAccessPermission = "N";
    Character statusCd = 'Y';
    LocalDateTime statusTime = LocalDateTime.parse("2020-03-03T10:15:30");

    DataSource actual =
        new DataSource(
            id,
            maxLen,
            conditionSecurity,
            jurisdictionSecurity,
            reportingFacilitySecurity,
            dataSourceName,
            dataSourceTitle,
            dataSourceTypeCode,
            descTxt,
            effectiveFromTime,
            effectiveToTime,
            orgAccessPermission,
            progAreaAccessPermission,
            statusCd,
            statusTime);

    assertThat(actual)
        .satisfies(ds -> assertEquals(id, ds.getId()))
        .satisfies(ds -> assertEquals(maxLen, ds.getColumnMaxLen()))
        .satisfies(ds -> assertEquals(conditionSecurity, ds.getConditionSecurity()))
        .satisfies(ds -> assertEquals(jurisdictionSecurity, ds.getJurisdictionSecurity()))
        .satisfies(ds -> assertEquals(reportingFacilitySecurity, ds.getReportingFacilitySecurity()))
        .satisfies(ds -> assertEquals(dataSourceName, ds.getDataSourceName()))
        .satisfies(ds -> assertEquals(dataSourceTypeCode, ds.getDataSourceTypeCode()))
        .satisfies(ds -> assertEquals(descTxt, ds.getDescTxt()))
        .satisfies(ds -> assertEquals(effectiveFromTime, ds.getEffectiveFromTime()))
        .satisfies(ds -> assertEquals(effectiveToTime, ds.getEffectiveToTime()))
        .satisfies(ds -> assertEquals(orgAccessPermission, ds.getOrgAccessPermission()))
        .satisfies(ds -> assertEquals(progAreaAccessPermission, ds.getProgAreaAccessPermission()))
        .satisfies(ds -> assertEquals(statusCd, ds.getStatusCd()))
        .satisfies(ds -> assertEquals(statusTime, ds.getStatusTime()));
  }
}

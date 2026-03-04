package gov.cdc.nbs.entity.odse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import gov.cdc.nbs.audit.Status;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class DataSourceTest {
  @Test
  void should_create_empty_data_source() {
    DataSource actual = new DataSource();

    assertThat(actual)
        .satisfies(ds -> assertNull(ds.getId()))
        .satisfies(ds -> assertNull(ds.getColumnMaxLen()))
        .satisfies(ds -> assertNull(ds.getConditionSecurity()))
        .satisfies(ds -> assertNull(ds.getJurisdictionSecurity()))
        .satisfies(ds -> assertNull(ds.getReportingFacilitySecurity()))
        .satisfies(ds -> assertNull(ds.getDataSourceName()))
        .satisfies(ds -> assertNull(ds.getDataSourceTitle()))
        .satisfies(ds -> assertNull(ds.getDataSourceTypeCode()))
        .satisfies(ds -> assertNull(ds.getDescTxt()))
        .satisfies(ds -> assertNull(ds.getEffectiveFromTime()))
        .satisfies(ds -> assertNull(ds.getEffectiveToTime()))
        .satisfies(ds -> assertNull(ds.getOrgAccessPermission()))
        .satisfies(ds -> assertNull(ds.getProgAreaAccessPermission()))
        .satisfies(ds -> assertNull(ds.getStatus()));
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
    Status status = new Status();

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
            status);

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
        .satisfies(ds -> assertEquals(status, ds.getStatus()));
  }
}

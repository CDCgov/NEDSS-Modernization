package gov.cdc.nbs.report.models;

import java.util.List;

public class ReportConfigurationOptions {
    private List<Long> defaultColumnUids;
    private Integer reportDays;

    public ReportConfigurationOptions() {}

    public ReportConfigurationOptions(List<Long> defaultColumnUids, Integer reportDays) {
        this.defaultColumnUids = defaultColumnUids;
        this.reportDays = reportDays;
    }

    // Getters and setters
    public List<Long> getDefaultColumnUids() { return defaultColumnUids; }
    public void setDefaultColumnUids(List<Long> uids) { this.defaultColumnUids = uids; }
    public Integer getReportDays() { return reportDays; }
    public void setReportDays(Integer days) { this.reportDays = days; }
}
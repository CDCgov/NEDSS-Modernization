package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Report_Filter")
public class ReportFilter {
    @Id
    @Column(name = "report_filter_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "data_source_uid", referencedColumnName = "data_source_uid", nullable = false)
    @JoinColumn(name = "report_uid", referencedColumnName = "report_uid", nullable = false)
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "filter_uid", nullable = false)
    private FilterCode filterUid;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "max_value_cnt")
    private Integer maxValueCnt;

    @Column(name = "min_value_cnt")
    private Integer minValueCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_uid")
    private DataSourceColumn columnUid;

}
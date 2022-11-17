package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Report_Sort_Column")
public class ReportSortColumn {
    @Id
    @Column(name = "report_sort_column_uid", nullable = false)
    private Long id;

    @Column(name = "report_sort_order_code", length = 4)
    private String reportSortOrderCode;

    @Column(name = "report_sort_sequence_num")
    private Integer reportSortSequenceNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "data_source_uid", referencedColumnName = "data_source_uid"),
            @JoinColumn(name = "report_uid", referencedColumnName = "report_uid")
    })
    private Report report;

    @Column(name = "column_uid")
    private Long columnUid;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}
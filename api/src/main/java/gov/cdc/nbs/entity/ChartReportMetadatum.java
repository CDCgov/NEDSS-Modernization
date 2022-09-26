package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Chart_report_metadata")
public class ChartReportMetadatum {
    @Id
    @Column(name = "chart_report_metadata_uid", nullable = false)
    private Long id;

    @Column(name = "chart_report_cd", nullable = false, length = 20)
    private String chartReportCd;

    @Column(name = "chart_report_desc_txt", nullable = false, length = 250)
    private String chartReportDescTxt;

    @Column(name = "external_class_nm", length = 250)
    private String externalClassNm;

    @Column(name = "external_method_nm", length = 250)
    private String externalMethodNm;

    @Column(name = "x_axis_title", length = 250)
    private String xAxisTitle;

    @Column(name = "y_axis_title", length = 250)
    private String yAxisTitle;

    @Column(name = "secured_ind_cd")
    private Character securedIndCd;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chart_type_uid", nullable = false)
    private ChartType chartTypeUid;

    @Column(name = "chart_report_short_desc_txt", nullable = false, length = 30)
    private String chartReportShortDescTxt;

    @Column(name = "object_nm", length = 30)
    private String objectNm;

    @Column(name = "operation_nm", length = 30)
    private String operationNm;

    @Column(name = "secured_by_object_operation")
    private Character securedByObjectOperation;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "default_ind_cd")
    private Character defaultIndCd;

}
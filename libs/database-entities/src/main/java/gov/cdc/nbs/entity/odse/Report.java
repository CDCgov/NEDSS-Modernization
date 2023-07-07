package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Report {
    @EmbeddedId
    private ReportId id;

    @MapsId("dataSourceUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "data_source_uid", nullable = false)
    private DataSource dataSourceUid;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_uid")
    private Long addUserUid;

    @Column(name = "desc_txt", length = 300)
    private String descTxt;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "filter_mode")
    private Character filterMode;

    @Column(name = "is_modifiable_ind")
    private Character isModifiableInd;

    @Column(name = "location", length = 300)
    private String location;

    @Column(name = "owner_uid")
    private Long ownerUid;

    @Column(name = "org_access_permis", length = 2000)
    private String orgAccessPermis;

    @Column(name = "prog_area_access_permis", length = 2000)
    private String progAreaAccessPermis;

    @Column(name = "report_title", length = 100)
    private String reportTitle;

    @Column(name = "report_type_code", length = 20)
    private String reportTypeCode;

    @Column(name = "shared")
    private Character shared;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "category", length = 20)
    private String category;

    @Column(name = "section_cd", nullable = false, length = 5)
    private String sectionCd;

}

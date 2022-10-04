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
@Table(name = "NBS_conversion_condition")
public class NbsConversionCondition {
    @Id
    @Column(name = "nbs_conversion_condition_uid", nullable = false)
    private Long id;

    @Column(name = "condition_cd", nullable = false, length = 50)
    private String conditionCd;

    @Column(name = "condition_cd_group_id")
    private Long conditionCdGroupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NBS_Conversion_Page_Mgmt_uid")
    private NbsConversionPageMgmt nbsConversionPageMgmtUid;

    @Column(name = "status_cd", length = 20)
    private String statusCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

}
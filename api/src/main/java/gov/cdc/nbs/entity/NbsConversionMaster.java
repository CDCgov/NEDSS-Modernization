package gov.cdc.nbs.entity;

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
@Table(name = "NBS_conversion_master")
public class NbsConversionMaster {
    @Id
    @Column(name = "nbs_conversion_master_uid", nullable = false)
    private Long id;

    @Column(name = "act_uid")
    private Long actUid;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "process_type_ind", nullable = false, length = 50)
    private String processTypeInd;

    @Column(name = "process_message_txt", length = 2000)
    private String processMessageTxt;

    @Column(name = "status_cd", length = 50)
    private String statusCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nbs_conversion_condition_uid")
    private NbsConversionCondition nbsConversionConditionUid;

}
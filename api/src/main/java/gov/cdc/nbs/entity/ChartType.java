package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Chart_type")
public class ChartType {
    @Id
    @Column(name = "chart_type_uid", nullable = false)
    private Long id;

    @Column(name = "chart_type_cd", nullable = false, length = 20)
    private String chartTypeCd;

    @Column(name = "chart_type_desc_txt", nullable = false, length = 250)
    private String chartTypeDescTxt;

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

}
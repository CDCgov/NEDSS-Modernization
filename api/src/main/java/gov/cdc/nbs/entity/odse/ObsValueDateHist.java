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
@Table(name = "Obs_value_date_hist")
public class ObsValueDateHist {
    @EmbeddedId
    private ObsValueDateHistId id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "observation_uid", referencedColumnName = "observation_uid", nullable = false),
            @JoinColumn(name = "obs_value_date_seq", referencedColumnName = "obs_value_date_seq", nullable = false)
    })
    private ObsValueDate obsValueDate;

    @Column(name = "duration_amt", length = 20)
    private String durationAmt;

    @Column(name = "duration_unit_cd", length = 20)
    private String durationUnitCd;

    @Column(name = "from_time")
    private Instant fromTime;

    @Column(name = "to_time")
    private Instant toTime;

}
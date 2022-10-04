package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Obs_value_numeric")
public class ObsValueNumeric {
    @EmbeddedId
    private ObsValueNumericId id;

    @MapsId("observationUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "observation_uid", nullable = false)
    private Observation observationUid;

    @Column(name = "high_range", length = 20)
    private String highRange;

    @Column(name = "low_range", length = 20)
    private String lowRange;

    @Column(name = "comparator_cd_1", length = 10)
    private String comparatorCd1;

    @Column(name = "numeric_value_1", precision = 15, scale = 5)
    private BigDecimal numericValue1;

    @Column(name = "numeric_value_2", precision = 15, scale = 5)
    private BigDecimal numericValue2;

    @Column(name = "numeric_unit_cd", length = 20)
    private String numericUnitCd;

    @Column(name = "separator_cd", length = 10)
    private String separatorCd;

    @Column(name = "numeric_scale_1")
    private Short numericScale1;

    @Column(name = "numeric_scale_2")
    private Short numericScale2;

}
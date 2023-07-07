package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "state_defined_field_data_hist")
public class StateDefinedFieldDataHist {
    @EmbeddedId
    private StateDefinedFieldDataHistId id;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "business_object_nm", nullable = false, length = 50)
    private String businessObjectNm;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "ldf_value", length = 2000)
    private String ldfValue;

}

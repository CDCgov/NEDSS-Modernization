package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Observation_reason_hist")
public class ObservationReasonHist {
    @EmbeddedId
    private ObservationReasonHistId id;

    @Column(name = "reason_desc_txt", length = 100)
    private String reasonDescTxt;

}

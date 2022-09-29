package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
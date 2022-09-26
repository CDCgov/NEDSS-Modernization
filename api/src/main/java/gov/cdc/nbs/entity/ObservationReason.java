package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Observation_reason")
public class ObservationReason {
    @EmbeddedId
    private ObservationReasonId id;

    @MapsId("observationUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "observation_uid", nullable = false)
    private Observation observationUid;

    @Column(name = "reason_desc_txt", length = 100)
    private String reasonDescTxt;

}
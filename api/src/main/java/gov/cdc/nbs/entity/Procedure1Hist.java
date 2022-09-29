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
@Table(name = "Procedure1_hist")
public class Procedure1Hist {
    @EmbeddedId
    private Procedure1HistId id;

    @MapsId("interventionUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "intervention_uid", nullable = false)
    private Procedure1 interventionUid;

    @Column(name = "approach_site_cd", length = 20)
    private String approachSiteCd;

    @Column(name = "approach_site_desc_txt", length = 100)
    private String approachSiteDescTxt;

}
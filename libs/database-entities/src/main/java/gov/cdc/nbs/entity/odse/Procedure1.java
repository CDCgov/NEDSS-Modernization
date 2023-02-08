package gov.cdc.nbs.entity.odse;

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
public class Procedure1 {
    @Id
    @Column(name = "intervention_uid", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "intervention_uid", nullable = false)
    private Intervention intervention;

    @Column(name = "approach_site_cd", length = 20)
    private String approachSiteCd;

    @Column(name = "approach_site_desc_txt", length = 100)
    private String approachSiteDescTxt;

}
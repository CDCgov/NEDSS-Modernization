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
@Table(name = "Substance_administration_hist")
public class SubstanceAdministrationHist {
    @EmbeddedId
    private SubstanceAdministrationHistId id;

    @MapsId("interventionUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "intervention_uid", nullable = false)
    private SubstanceAdministration interventionUid;

    @Column(name = "dose_qty", length = 10)
    private String doseQty;

    @Column(name = "dose_qty_unit_cd", length = 20)
    private String doseQtyUnitCd;

    @Column(name = "form_cd", length = 20)
    private String formCd;

    @Column(name = "form_desc_txt", length = 100)
    private String formDescTxt;

    @Column(name = "rate_qty", length = 10)
    private String rateQty;

    @Column(name = "rate_qty_unit_cd", length = 20)
    private String rateQtyUnitCd;

    @Column(name = "route_cd", length = 20)
    private String routeCd;

    @Column(name = "route_desc_txt", length = 100)
    private String routeDescTxt;

}
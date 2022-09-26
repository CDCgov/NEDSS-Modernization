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
@Table(name = "Organization_name_hist")
public class OrganizationNameHist {
    @EmbeddedId
    private OrganizationNameHistId id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "organization_uid", referencedColumnName = "organization_uid", nullable = false),
            @JoinColumn(name = "organization_name_seq", referencedColumnName = "organization_name_seq", nullable = false)
    })
    private OrganizationName organizationName;

    @Column(name = "nm_txt", length = 100)
    private String nmTxt;

    @Column(name = "nm_use_cd", length = 20)
    private String nmUseCd;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "default_nm_ind")
    private Character defaultNmInd;

}
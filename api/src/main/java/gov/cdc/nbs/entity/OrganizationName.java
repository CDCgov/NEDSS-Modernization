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
@Table(name = "Organization_name")
public class OrganizationName {
    @EmbeddedId
    private OrganizationNameId id;

    @MapsId("organizationUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_uid", nullable = false)
    private Organization organizationUid;

    @Column(name = "nm_txt", length = 100)
    private String nmTxt;

    @Column(name = "nm_use_cd", length = 20)
    private String nmUseCd;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "default_nm_ind")
    private Character defaultNmInd;

}
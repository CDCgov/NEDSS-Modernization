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
@Table(name = "NBS_metadata_rule")
public class NbsMetadataRule {
    @Id
    @Column(name = "nbs_metadata_rule_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "component_uid", nullable = false)
    private NbsQuestion componentUid;

    @Column(name = "component_identifier", length = 50)
    private String componentIdentifier;

    @Column(name = "component_type", length = 20)
    private String componentType;

}
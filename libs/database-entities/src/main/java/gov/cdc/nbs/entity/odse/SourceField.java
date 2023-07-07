package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Source_Field")
public class SourceField {
    @Id
    @Column(name = "source_field_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nbs_metadata_rule_uid", nullable = false)
    private NbsMetadataRule nbsMetadataRuleUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rule_instance_uid", nullable = false)
    private RuleInstance ruleInstanceUid;

    @Column(name = "source_field_seq_nbr")
    private Integer sourceFieldSeqNbr;

}

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
@Table(name = "Rule_Instance")
public class RuleInstance {
    @Id
    @Column(name = "rule_instance_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conseq_ind_uid", nullable = false)
    private ConsequenceIndicator conseqIndUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rule_uid", nullable = false)
    private Rule ruleUid;

    @Column(name = "comments")
    private String comments;

}
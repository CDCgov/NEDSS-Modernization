package gov.cdc.nbs.entity.odse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Rule {
    @Id
    @Column(name = "rule_uid", nullable = false)
    private Long id;

    @Column(name = "rule_name", length = 50)
    private String ruleName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rule_type_uid", nullable = false)
    private RuleType ruleTypeUid;

    @Column(name = "comments")
    private String comments;

}
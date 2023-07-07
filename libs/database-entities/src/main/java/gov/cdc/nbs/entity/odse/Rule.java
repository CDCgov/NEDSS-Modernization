package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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

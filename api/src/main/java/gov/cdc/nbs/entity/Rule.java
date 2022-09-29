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
@Table(name = "\"Rule\"")
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
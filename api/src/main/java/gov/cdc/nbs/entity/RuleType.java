package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Rule_Type")
public class RuleType {
    @Id
    @Column(name = "rule_type_uid", nullable = false)
    private Long id;

    @Column(name = "rule_type_code", nullable = false)
    private Character ruleTypeCode;

    @Column(name = "rule_type_description", length = 100)
    private String ruleTypeDescription;

}
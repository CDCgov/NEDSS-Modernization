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
@Table(name = "Form_Rule_Instance")
public class FormRuleInstance {
    @Id
    @Column(name = "form_rule_instance_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rule_instance_uid", nullable = false)
    private RuleInstance ruleInstanceUid;

    @Column(name = "form_code", nullable = false, length = 20)
    private String formCode;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

}

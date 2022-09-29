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
@Table(name = "Target_Value")
public class TargetValue {
    @Id
    @Column(name = "target_value_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_field_uid", nullable = false)
    private TargetField targetFieldUid;

    @Column(name = "target_value", length = 50)
    private String targetValue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "error_message_uid", nullable = false)
    private ErrorMessage errorMessageUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_type_uid")
    private OperatorType operatorTypeUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conseq_ind_uid", nullable = false)
    private ConsequenceIndicator conseqIndUid;

}
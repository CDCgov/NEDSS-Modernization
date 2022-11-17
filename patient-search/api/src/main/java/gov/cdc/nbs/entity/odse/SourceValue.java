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
@Table(name = "Source_Value")
public class SourceValue {
    @Id
    @Column(name = "source_value_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_field_uid", nullable = false)
    private SourceField sourceFieldUid;

    @Column(name = "source_value", length = 50)
    private String sourceValue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "operator_type_uid", nullable = false)
    private OperatorType operatorTypeUid;

}
package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Operator_type")
public class OperatorType {
    @Id
    @Column(name = "operator_type_uid", nullable = false)
    private Long id;

    @Column(name = "operator_type_code", length = 20)
    private String operatorTypeCode;

    @Column(name = "operator_type_desc_txt", length = 50)
    private String operatorTypeDescTxt;

}

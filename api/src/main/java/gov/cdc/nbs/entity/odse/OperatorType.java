package gov.cdc.nbs.entity.odse;

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
package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Data_Source_Operator")
public class DataSourceOperator {
    @Id
    @Column(name = "data_source_operator_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_operator_uid")
    private FilterOperator filterOperatorUid;

    @Column(name = "column_type_code", length = 20)
    private String columnTypeCode;

    @Column(name = "status_cd", nullable = false)
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}
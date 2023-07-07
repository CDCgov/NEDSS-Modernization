package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Filter_Operator")
public class FilterOperator {
    @Id
    @Column(name = "filter_operator_uid", nullable = false)
    private Long id;

    @Column(name = "filter_operator_code", length = 20)
    private String filterOperatorCode;

    @Column(name = "filter_operator_desc", length = 200)
    private String filterOperatorDesc;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}

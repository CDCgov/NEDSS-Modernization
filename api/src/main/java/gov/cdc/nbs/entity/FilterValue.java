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
@Table(name = "Filter_value")
public class FilterValue {
    @Id
    @Column(name = "value_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "report_filter_uid", nullable = false)
    private ReportFilter reportFilterUid;

    @Column(name = "sequence_nbr")
    private Short sequenceNbr;

    @Column(name = "value_type", length = 20)
    private String valueType;

    @Column(name = "column_uid")
    private Long columnUid;

    @Column(name = "operator", length = 20)
    private String operator;

    @Column(name = "value_txt", length = 2000)
    private String valueTxt;

}
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
@Table(name = "Data_source_column")
public class DataSourceColumn {
    @Id
    @Column(name = "column_uid", nullable = false)
    private Long id;

    @Column(name = "column_max_len")
    private Integer columnMaxLen;

    @Column(name = "column_name", length = 50)
    private String columnName;

    @Column(name = "column_title", length = 60)
    private String columnTitle;

    @Column(name = "column_type_code", length = 20)
    private String columnTypeCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "data_source_uid", nullable = false)
    private DataSource dataSourceUid;

    @Column(name = "desc_txt", length = 300)
    private String descTxt;

    @Column(name = "displayable")
    private Character displayable;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "filterable")
    private Character filterable;

    @Column(name = "status_cd", nullable = false)
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}
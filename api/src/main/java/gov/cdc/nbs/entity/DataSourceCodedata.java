package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "data_source_codedata")
public class DataSourceCodedata {
    @Id
    @Column(name = "data_source_codedata_uid", nullable = false)
    private Long id;

    @Column(name = "data_source_name", length = 300)
    private String dataSourceName;

    @Column(name = "column_name", length = 300)
    private String columnName;

    @Column(name = "codeset_name", nullable = false, length = 2000)
    private String codesetName;

    @Column(name = "code_desc_cd", length = 1)
    private String codeDescCd;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}
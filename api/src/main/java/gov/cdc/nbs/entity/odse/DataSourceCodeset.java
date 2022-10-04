package gov.cdc.nbs.entity.odse;

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
@Table(name = "Data_Source_Codeset")
public class DataSourceCodeset {
    @Id
    @Column(name = "data_source_codeset_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_uid")
    private DataSourceColumn columnUid;

    @Column(name = "code_desc_cd", length = 20)
    private String codeDescCd;

    @Column(name = "codeset_nm", length = 256)
    private String codesetNm;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}
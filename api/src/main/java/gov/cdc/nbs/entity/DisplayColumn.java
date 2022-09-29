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
@Table(name = "Display_column")
public class DisplayColumn {
    @Id
    @Column(name = "display_column_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "column_uid", nullable = false)
    private DataSourceColumn columnUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "data_source_uid", referencedColumnName = "data_source_uid", nullable = false),
            @JoinColumn(name = "report_uid", referencedColumnName = "report_uid", nullable = false)
    })
    private Report report;

    @Column(name = "sequence_nbr", nullable = false)
    private Short sequenceNbr;

    @Column(name = "status_cd", nullable = false)
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}
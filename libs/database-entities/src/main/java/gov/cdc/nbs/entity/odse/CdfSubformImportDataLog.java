package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CDF_subform_import_data_log")
public class CdfSubformImportDataLog {
    @EmbeddedId
    private CdfSubformImportDataLogId id;

    @MapsId("importLogUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "import_log_uid", nullable = false)
    private CdfSubformImportLog importLogUid;

    @Column(name = "action_type", length = 20)
    private String actionType;

    @Column(name = "import_time")
    private Instant importTime;

    @Column(name = "log_message_txt", length = 2000)
    private String logMessageTxt;

    @Column(name = "process_cd", length = 20)
    private String processCd;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

}

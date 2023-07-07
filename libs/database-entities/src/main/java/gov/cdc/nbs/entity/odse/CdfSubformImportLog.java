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
@Table(name = "CDF_subform_import_log")
public class CdfSubformImportLog {
    @Id
    @Column(name = "import_log_uid", nullable = false)
    private Long id;

    @Column(name = "log_message_txt", length = 2000)
    private String logMessageTxt;

    @Column(name = "import_time")
    private Instant importTime;

    @Column(name = "import_version_nbr", nullable = false)
    private Long importVersionNbr;

    @Column(name = "process_cd", length = 20)
    private String processCd;

    @Column(name = "source_nm", length = 200)
    private String sourceNm;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Column(name = "admin_comment", length = 300)
    private String adminComment;

}

package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ELR_activity_log")
public class ElrActivityLog {
    @EmbeddedId
    private ElrActivityLogId id;

    @Column(name = "filler_nbr", length = 100)
    private String fillerNbr;

    @Column(name = "id", length = 100)
    private String id1;

    @Column(name = "ods_observation_uid", length = 50)
    private String odsObservationUid;

    @Column(name = "status_cd", nullable = false)
    private Character statusCd;

    @Column(name = "process_time", nullable = false)
    private Instant processTime;

    @Column(name = "process_cd", nullable = false, length = 20)
    private String processCd;

    @Column(name = "subject_nm", length = 100)
    private String subjectNm;

    @Column(name = "report_fac_nm", length = 100)
    private String reportFacNm;

    @Column(name = "detail_txt", length = 1000)
    private String detailTxt;

}

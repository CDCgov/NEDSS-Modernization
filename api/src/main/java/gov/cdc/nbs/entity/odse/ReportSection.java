package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Report_Section")
public class ReportSection {
    @EmbeddedId
    private ReportSectionId id;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "comments", length = 250)
    private String comments;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "section_desc_txt", nullable = false, length = 100)
    private String sectionDescTxt;

    @Column(name = "status_cd", length = 10)
    private String statusCd;

}
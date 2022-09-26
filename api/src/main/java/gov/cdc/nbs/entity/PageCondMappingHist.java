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
@Table(name = "Page_cond_mapping_hist")
public class PageCondMappingHist {
    @Id
    @Column(name = "page_cond_mapping_hist_uid", nullable = false)
    private Long id;

    @Column(name = "page_cond_mapping_uid", nullable = false)
    private Long pageCondMappingUid;

    @Column(name = "wa_template_uid", nullable = false)
    private Long waTemplateUid;

    @Column(name = "condition_cd", nullable = false, length = 20)
    private String conditionCd;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

}
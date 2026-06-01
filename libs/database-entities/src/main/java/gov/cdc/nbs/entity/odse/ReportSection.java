package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Report_Section", catalog = "NBS_ODSE")
public class ReportSection {
  @Id
  @Column(name = "report_section_uid", nullable = false)
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  private Long id;

  @NonNull @Column(name = "section_cd", length = 50, nullable = false)
  private String sectionCd;

  @NonNull @Column(name = "section_desc_txt", length = 100, nullable = false)
  private String sectionDescTxt;

  @Column(name = "comments", length = 250)
  private String comments;

  @Column(name = "add_time")
  private LocalDateTime addTime;

  @Column(name = "add_user_id")
  private Long addUserId;

  @Column(name = "last_chg_user_id")
  private Long lastChgUserId;

  @Column(name = "last_chg_time")
  private LocalDateTime lastChgTime;

  @Column(name = "status_cd", length = 10)
  private String statusCd;
}

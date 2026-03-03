package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Report_Library", catalog = "NBS_ODSE")
public class ReportLibrary {
  @Id
  @Column(name = "library_uid")
  private Long id;

  @Column(name = "library_name", length = 50, nullable = false)
  private String libraryName;

  @Column(name = "desc_txt", length = 300, nullable = false)
  private String descTxt;

  @Column(name = "runner", length = 10, nullable = false)
  private String runner;

  @Column(name = "is_builtin_ind", length = 1, nullable = false)
  private Character isBuiltinIndex;

  @Column(name = "add_time", nullable = false)
  private LocalDateTime addTime;

  @Column(name = "add_user_id", nullable = false)
  private Long addUserId;

  @Column(name = "last_chg_time", nullable = false)
  private LocalDateTime lastChgTime;

  @Column(name = "last_chg_user_id", nullable = false)
  private Long lastChgUserId;
}

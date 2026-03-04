package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Report_Library", catalog = "NBS_ODSE")
public class ReportLibrary {
  // TODO: Add @NonNull here?
  @Id
  @Column(name = "library_uid")
  private Long id;

  @NonNull @Column(name = "library_name", length = 50, nullable = false)
  private String libraryName;

  @NonNull @Column(name = "desc_txt", length = 300, nullable = false)
  private String descTxt;

  @NonNull @Column(name = "runner", length = 10, nullable = false)
  private String runner;

  @NonNull @Column(name = "is_builtin_ind", length = 1, nullable = false)
  private Character isBuiltinIndex;

  @NonNull @Column(name = "add_time", nullable = false)
  private LocalDateTime addTime;

  @NonNull @Column(name = "add_user_id", nullable = false)
  private Long addUserId;

  @NonNull @Column(name = "last_chg_time", nullable = false)
  private LocalDateTime lastChgTime;

  @NonNull @Column(name = "last_chg_user_id", nullable = false)
  private Long lastChgUserId;
}

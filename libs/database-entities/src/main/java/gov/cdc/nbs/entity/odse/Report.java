package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
// @Table(name = "Report")
public class Report {
  @EmbeddedId private ReportId id;

  @ManyToOne(fetch = FetchType.LAZY) // TODO: leave as-is or default to EAGER?
  @JoinColumn(name = "library_uid")
  private ReportLibrary reportLibraryUid;

  @Column(name = "desc_txt", length = 300)
  private String descTxt;

  @Column(name = "effective_from_time")
  private LocalDate effectiveFromTime;

  @Column(name = "effective_to_time")
  private LocalDate effectiveToTime;

  @Column(name = "filter_mode", length = 1)
  private Character filterMode;

  // TODO: boolean?
  @Column(name = "is_modifiable_ind", length = 1)
  private Character isModifiableIndicator;

  @Column(name = "location", length = 300)
  private String location;

  @Column(name = "owner_uid")
  private Integer ownerUid;

  @Column(name = "org_access_permis", length = 2000)
  private String orgAccessPermission;

  @Column(name = "prog_area_access_permis", length = 2000)
  private String progAreaAccessPermission;

  @Column(name = "report_title", length = 100)
  private String reportTitle;

  @Column(name = "report_type_code", length = 20)
  private String reportTypeCode;

  // TODO: add convertor?
  @Column(name = "shared", length = 1)
  private Character shared;

  @Column(name = "category", length = 20)
  private String category;

  @Column(name = "section_cd", length = 5, nullable = false)
  private String sectionCd;

  @Embedded private Audit audit;

  @Embedded private Status status;
}

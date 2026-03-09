package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Added;
import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.time.EffectiveTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Report", catalog = "NBS_ODSE")
public class Report {
  @NonNull @EmbeddedId private ReportId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "data_source_uid",
      referencedColumnName = "data_source_uid",
      insertable = false,
      updatable = false)
  private DataSource dataSource;

  @NonNull @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "library_uid")
  private ReportLibrary reportLibrary;

  @Column(name = "desc_txt", length = 300)
  private String descTxt;

  @Embedded private EffectiveTime effectiveTime;

  @Column(name = "filter_mode", length = 1)
  private Character filterMode;

  @Column(name = "is_modifiable_ind", length = 1)
  private Character isModifiableIndicator;

  @Column(name = "location", length = 300)
  private String location;

  @Column(name = "owner_uid")
  private Long ownerUid;

  @Column(name = "org_access_permis", length = 2000)
  private String orgAccessPermission;

  @Column(name = "prog_area_access_permis", length = 2000)
  private String progAreaAccessPermission;

  @Column(name = "report_title", length = 100)
  private String reportTitle;

  @Column(name = "report_type_code", length = 20)
  private String reportTypeCode;

  // TODO: add convertor? // NOSONAR
  @Column(name = "shared", length = 1)
  private Character shared;

  @Column(name = "category", length = 20)
  private String category;

  @NonNull @Column(name = "section_cd", length = 5, nullable = false)
  private String sectionCd;

  @Embedded private Added added;

  @Embedded private Status status;

  protected Report() {}
}

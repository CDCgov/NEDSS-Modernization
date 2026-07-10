package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.time.EffectiveTime;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name = "Report", catalog = "NBS_ODSE")
@NamedEntityGraph(
    name = "Report.save",
    attributeNodes = {
      @NamedAttributeNode(value = "dataSource", subgraph = "dataSource.dataSourceColumns"),
      @NamedAttributeNode(value = "reportFilters", subgraph = "reportFilters.details"),
      @NamedAttributeNode("reportSortColumns"),
    },
    subgraphs = {
      @NamedSubgraph(
          name = "reportFilters.details",
          type = ReportFilter.class,
          attributeNodes = {
            @NamedAttributeNode("filterCode"),
            @NamedAttributeNode("filterValues"),
          }),
      @NamedSubgraph(
          name = "dataSource.dataSourceColumns",
          type = DataSource.class,
          attributeNodes = {
            @NamedAttributeNode("dataSourceColumns"),
          })
    })
public class Report {
  @NonNull @EmbeddedId private ReportId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "data_source_uid",
      referencedColumnName = "data_source_uid",
      insertable = false,
      updatable = false)
  private DataSource dataSource;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "library_uid")
  private ReportLibrary reportLibrary;

  // Report filters are updated/deleted when reports save/delete, so we need to
  // cascade all operations. When a report is no longer referenced, we want
  // to delete it, so set `orphanRemoval` to true
  @OneToMany(
      mappedBy = "report",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<ReportFilter> reportFilters;

  @OneToMany(
      mappedBy = "report",
      fetch = FetchType.LAZY,
      orphanRemoval = true,
      cascade = CascadeType.ALL)
  private List<DisplayColumn> displayColumns;

  // should be 1:1 in practice, but the DB implies there could be
  // more and NBS 6 fetches all then takes the first
  @OneToMany(
      mappedBy = "report",
      fetch = FetchType.LAZY,
      orphanRemoval = true,
      cascade = CascadeType.ALL)
  private List<ReportSortColumn> reportSortColumns;

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

  @Column(name = "section_cd", length = 5, nullable = false)
  private String sectionCd;

  @Column(name = "add_reason_cd", length = 20)
  private String addReasonCd;

  @Column(name = "add_time")
  private LocalDateTime addTime;

  @Column(name = "add_user_uid")
  private Long addUserUid;

  @Embedded private Status status;

  protected Report() {}
}

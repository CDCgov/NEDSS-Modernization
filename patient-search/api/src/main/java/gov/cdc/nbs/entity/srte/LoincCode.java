package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(catalog = "NBS_SRTE", name = "LOINC_code")
public class LoincCode implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loinc_cd", nullable = false, length = 20)
    private String id;

    @Column(name = "component_name", length = 200)
    private String componentName;

    @Column(name = "property", length = 10)
    private String property;

    @Column(name = "time_aspect", length = 10)
    private String timeAspect;

    @Column(name = "system_cd", length = 50)
    private String systemCd;

    @Column(name = "scale_type", length = 20)
    private String scaleType;

    @Column(name = "method_type", length = 50)
    private String methodType;

    @Column(name = "display_nm", length = 300)
    private String displayNm;

    @Column(name = "nbs_uid")
    private Long nbsUid;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "related_class_cd", length = 50)
    private String relatedClassCd;

    @Column(name = "pa_derivation_exclude_cd")
    private Character paDerivationExcludeCd;

    @OneToMany(mappedBy = "loincCd")
    @Builder.Default
    private Set<LabtestLoinc> labtestLoincs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "loincCd")
    @Builder.Default
    private Set<LoincSnomedCondition> loincSnomedConditions = new LinkedHashSet<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "loincCode")
    private LoincCondition loincCondition;

}
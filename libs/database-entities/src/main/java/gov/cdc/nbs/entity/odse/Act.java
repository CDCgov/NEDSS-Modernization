package gov.cdc.nbs.entity.odse;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@SuppressWarnings("javaarchitecture:S7027")
public class Act {
  @Id
  @Column(name = "act_uid", nullable = false)
  private Long id;

  @Column(name = "class_cd", nullable = false, length = 10)
  private String classCd;

  @Column(name = "mood_cd", nullable = false, length = 10)
  private String moodCd;

  @SuppressWarnings(
      //  Bidirectional mappings require knowledge of each other
      "javaarchitecture:S7027"
  )
  @OneToMany(mappedBy = "actUid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Participation> participations;

  @SuppressWarnings(
      //  Bidirectional mappings require knowledge of each other
      "javaarchitecture:S7027"
  )
  @OneToMany(mappedBy = "id.actUid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ActId> actIds;

  @SuppressWarnings(
      //  Bidirectional mappings require knowledge of each other
      "javaarchitecture:S7027"
  )
  @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
  private List<Observation> observations;

  @SuppressWarnings(
      //  Bidirectional mappings require knowledge of each other
      "javaarchitecture:S7027"
  )
  @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
  private List<Intervention> interventions;

  @SuppressWarnings(
      //  Bidirectional mappings require knowledge of each other
      "javaarchitecture:S7027"
  )
  @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
  private List<Treatment> treatments;

  @SuppressWarnings(
      //  Bidirectional mappings require knowledge of each other
      "javaarchitecture:S7027"
  )
  @OneToMany(mappedBy = "targetActUid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ActRelationship> targetActRelationships;

  @SuppressWarnings(
      //  Bidirectional mappings require knowledge of each other
      "javaarchitecture:S7027"
  )
  @OneToMany(mappedBy = "sourceActUid", fetch = FetchType.LAZY, cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE,
      CascadeType.REMOVE
  })
  private List<ActRelationship> actRelationships;

  public Act() {

  }

  public Act(final long identifier, final String classCd) {
    this();
    this.id = identifier;
    this.classCd = classCd;
    this.moodCd = "EVN";
  }

  public void addParticipation(final Participation participation) {
    participation.setActUid(this);
    ensureParticipation().add(participation);
  }

  private List<Participation> ensureParticipation() {
    if (this.participations == null) {
      this.participations = new ArrayList<>();
    }

    return this.participations;
  }

}

package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "ELR_XREF")
public class ElrXref {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private ElrXrefId id;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "laboratory_id", length = 20)
    private String laboratoryId;

    @Column(name = "nbs_uid")
    private Integer nbsUid;

}
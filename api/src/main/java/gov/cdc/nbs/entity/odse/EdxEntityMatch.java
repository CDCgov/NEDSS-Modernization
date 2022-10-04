package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "EDX_entity_match")
public class EdxEntityMatch {
    @Id
    @Column(name = "edx_entity_match_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_UID", nullable = false)
    private NBSEntity NBSEntityUid;

    @Column(name = "match_string", nullable = false, length = 2000)
    private String matchString;

    @Column(name = "type_cd", nullable = false, length = 100)
    private String typeCd;

    @Column(name = "match_string_hashcode", nullable = false)
    private Long matchStringHashcode;

}
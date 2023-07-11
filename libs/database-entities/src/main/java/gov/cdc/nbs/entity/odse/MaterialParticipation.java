package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import gov.cdc.nbs.entity.enums.RecordStatus;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class MaterialParticipation {
    @Column(name = "act_uid")
    private long actUid;

    @Column(name = "type_cd")
    private String typeCd;

    @Column(name = "entity_id")
    private long entityId;

    @Column(name = "subject_class_cd")
    private String subjectClassCd;

    @Column(name = "record_status")
    private String recordStatus;

    @Column(name = "type_desc_txt")
    private String typeDescTxt;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "material_cd")
    private String materialCd;

    @Column(name = "material_cd_desc_txt")
    private String materialCdDescTxt;
}
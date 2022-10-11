package gov.cdc.nbs.entity.srte;

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
@Table(catalog = "NBS_SRTE", name = "State_model")
public class StateModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private StateModelId id;

    @Column(name = "record_status_code_set_nm", nullable = false, length = 256)
    private String recordStatusCodeSetNm;

    @Column(name = "record_status_to_code", nullable = false, length = 20)
    private String recordStatusToCode;

    @Column(name = "record_status_seq_nm", nullable = false)
    private Short recordStatusSeqNm;

    @Column(name = "object_status_code_set_nm", nullable = false, length = 256)
    private String objectStatusCodeSetNm;

    @Column(name = "object_status_from_code", nullable = false, length = 20)
    private String objectStatusFromCode;

    @Column(name = "object_status_to_code", nullable = false, length = 20)
    private String objectStatusToCode;

    @Column(name = "object_status_seq_nm", nullable = false)
    private Short objectStatusSeqNm;

    @Column(name = "nbs_uid")
    private Integer nbsUid;

}
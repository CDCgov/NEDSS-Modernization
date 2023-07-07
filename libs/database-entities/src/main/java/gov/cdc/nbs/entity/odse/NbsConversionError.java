package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "NBS_conversion_error")
public class NbsConversionError {
    @Id
    @Column(name = "nbs_conversion_error_uid", nullable = false)
    private Long id;

    @Column(name = "error_cd", nullable = false, length = 250)
    private String errorCd;

    @Column(name = "error_message_txt", nullable = false, length = 4000)
    private String errorMessageTxt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nbs_conversion_master_uid")
    private NbsConversionMaster nbsConversionMasterUid;

    @Column(name = "condition_cd_group_id")
    private Long conditionCdGroupId;

    @Column(name = "nbs_conversion_mapping_uid")
    private Long nbsConversionMappingUid;

}

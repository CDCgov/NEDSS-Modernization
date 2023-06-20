package gov.cdc.nbs.questionbank.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Codeset_Group_Metadata")
public class CodeSetGroupMetadatum {
    @Id
    @Column(name = "code_set_group_id", nullable = false)
    private Long id;

    @Column(name = "code_set_nm", nullable = false, length = 256)
    private String codeSetNm;

    @Column(name = "vads_value_set_code", length = 256)
    private String vadsValueSetCode;

    @Column(name = "code_set_desc_txt", length = 2000)
    private String codeSetDescTxt;

    @Column(name = "code_set_short_desc_txt", length = 2000)
    private String codeSetShortDescTxt;

    @Column(name = "ldf_picklist_ind_cd")
    private Character ldfPicklistIndCd;

    @Column(name = "phin_std_val_ind")
    private Character phinStdValInd;

    private Set<Codeset> codesets = new LinkedHashSet<>();
}

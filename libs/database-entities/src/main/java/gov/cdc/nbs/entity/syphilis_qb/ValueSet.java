package gov.cdc.nbs.entity.syphilis_qb;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;



@Entity
@Table(name = "value_sets")
public class ValueSet {
    @Id
    @GeneratedValue
    @Column(name = "code_set_group_id", nullable = false)
    private Long id;

    @Column(name = "code_set_nm")
    private String codeSetNm;

    @Column(name = "vads_value_set_code")
    private String vadsValueSetCode;

    @Column(name = "code_set_desc_txt")
    private String codeSetDescTxt;

    @Column(name = "code_set_short_desc_txt")
    private String codeSetShortDescTxt;

    @Column(name = "ldf_picklist_ind_cd", length = 1)
    private String ldfPicklistIndCd;

    @Column(name = "phin_std_val_ind", length = 1)
    private String phinStdValInd;
}
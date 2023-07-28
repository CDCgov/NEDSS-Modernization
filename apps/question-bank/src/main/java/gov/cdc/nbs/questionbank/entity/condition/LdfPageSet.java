package gov.cdc.nbs.questionbank.entity.condition;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "LDF_page_set")
public class LdfPageSet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ldf_page_id", nullable = false, length = 20)
    private String id;

    @Column(name = "business_object_nm", length = 20)
    private String businessObjectNm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_cd")
    private ConditionCode conditionCd;

    @Column(name = "ui_display", length = 10)
    private String uiDisplay;

    @Column(name = "indent_level_nbr")
    private Short indentLevelNbr;

    @Column(name = "parent_is_cd", length = 20)
    private String parentIsCd;

    @Column(name = "code_set_nm", length = 256)
    private String codeSetNm;

    @Column(name = "seq_num")
    private Short seqNum;

    @Column(name = "code_version", length = 10)
    private String codeVersion;

    @Column(name = "nbs_uid")
    private Integer nbsUid;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "code_short_desc_txt", length = 50)
    private String codeShortDescTxt;

    @Column(name = "display_row")
    private Short displayRow;

    @Column(name = "display_column")
    private Short displayColumn;



//    public LdfPageSet(Instant effectiveFromTime, Instant effectiveToTime, Character statusCd, String codeShortDescTxt){
//        this.businessObjectNm = "PHC";
//        this.uiDisplay = "Link";
//        this.indentLevelNbr = 2;
//        this.parentIsCd = "30";
//        this.codeSetNm = "LDF_PAGE_SET";
//        this.seqNum = 1;
//        this.codeVersion = "1";
//        this.effectiveFromTime = effectiveFromTime;
//        this.effectiveToTime = effectiveToTime;
//        this.statusCd = statusCd;
//        this.codeShortDescTxt = codeShortDescTxt;
//        this.displayColumn = 2;
//    }

}

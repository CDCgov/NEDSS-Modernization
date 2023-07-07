package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "XSS_Filter_Pattern")
public class XssFilterPattern {
    @Id
    @Column(name = "XSS_Filter_Pattern_uid", nullable = false)
    private Long xssFilterPatternUid;

    @Column(name = "reg_exp", nullable = false, length = 250)
    private String regExp;

    @Column(name = "flag", nullable = false, length = 250)
    private String flag;

    @Column(name = "desc_txt", length = 200)
    private String descTxt;

    @Column(name = "status_cd", nullable = false, length = 50)
    private String statusCd;

    @Column(name = "status_time", nullable = false)
    private Instant statusTime;

}

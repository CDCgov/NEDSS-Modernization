package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Filter_code")
public class FilterCode {
    @Id
    @Column(name = "filter_uid", nullable = false)
    private Long id;

    @Column(name = "code_table", nullable = false, length = 50)
    private String codeTable;

    @Column(name = "desc_txt", length = 300)
    private String descTxt;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "filter_code", length = 20)
    private String filterCode;

    @Column(name = "filter_code_set_nm", length = 256)
    private String filterCodeSetNm;

    @Column(name = "filter_type", length = 20)
    private String filterType;

    @Column(name = "filter_name", length = 50)
    private String filterName;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}
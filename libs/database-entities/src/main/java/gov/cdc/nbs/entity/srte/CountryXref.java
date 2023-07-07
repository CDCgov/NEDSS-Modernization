package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Country_XREF")
public class CountryXref {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_xref_uid", nullable = false)
    private Long id;

    @Column(name = "from_code_set_nm", nullable = false, length = 256)
    private String fromCodeSetNm;

    @Column(name = "from_seq_num", nullable = false)
    private Short fromSeqNum;

    @Column(name = "from_code", nullable = false, length = 20)
    private String fromCode;

    @Column(name = "from_code_desc_txt", length = 100)
    private String fromCodeDescTxt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_code_set_nm", referencedColumnName = "code_set_nm", nullable = false)
    @JoinColumn(name = "to_seq_num", referencedColumnName = "seq_num", nullable = false)
    @JoinColumn(name = "to_code", referencedColumnName = "code", nullable = false)
    private CountryCodeIso countryCodeIso;

    @Column(name = "to_code_desc_txt", length = 50)
    private String toCodeDescTxt;

    @Column(name = "to_code_system_cd", length = 300)
    private String toCodeSystemCd;

    @Column(name = "status_cd", length = 1)
    private String statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "alpha2_to_code", length = 2)
    private String alpha2ToCode;

}

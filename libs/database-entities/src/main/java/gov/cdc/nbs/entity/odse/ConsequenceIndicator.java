package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Consequence_indicator")
public class ConsequenceIndicator {
    @Id
    @Column(name = "conseq_ind_uid", nullable = false)
    private Long id;

    @Column(name = "conseq_ind_code", length = 1)
    private String conseqIndCode;

    @Column(name = "conseq_ind_desc_txt", length = 100)
    private String conseqIndDescTxt;

    @Column(name = "conseq_ind_type", length = 25)
    private String conseqIndType;

}

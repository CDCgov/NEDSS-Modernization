package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "EDX_patient_match")
public class EdxPatientMatch {
    @Id
    @Column(name = "EDX_patient_match_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Patient_uid", nullable = false)
    private NBSEntity patientUid;

    @Column(name = "match_string", nullable = false, length = 2000)
    private String matchString;

    @Column(name = "type_cd", nullable = false, length = 100)
    private String typeCd;

    @Column(name = "match_string_hashcode", nullable = false)
    private Long matchStringHashcode;

}

package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class TotalIDM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TotalIDM_id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "unique_cd")
    private String uniqueCd;

    @Nationalized
    @Column(name = "SRT_reference")
    private String srtReference;

    @Nationalized
    @Column(name = "format")
    private String format;

    @Nationalized
    @Column(name = "label")
    private String label;

}

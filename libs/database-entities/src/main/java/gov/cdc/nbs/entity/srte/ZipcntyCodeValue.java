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
@Table(catalog = "NBS_SRTE", name = "Zipcnty_code_value")
public class ZipcntyCodeValue {
    @EmbeddedId
    private ZipcntyCodeValueId id;

    @MapsId("zipCode")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zip_code", nullable = false)
    private ZipCodeValue zipCode;

    @MapsId("cntyCode")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cnty_code", nullable = false)
    private StateCountyCodeValue cntyCode;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

}

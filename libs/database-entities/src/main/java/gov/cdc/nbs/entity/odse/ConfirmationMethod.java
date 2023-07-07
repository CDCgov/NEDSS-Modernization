package gov.cdc.nbs.entity.odse;

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
@Table(name = "Confirmation_method")
public class ConfirmationMethod {
    @EmbeddedId
    private ConfirmationMethodId id;

    @MapsId("publicHealthCaseUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "public_health_case_uid", nullable = false)
    private PublicHealthCase publicHealthCaseUid;

    @Column(name = "confirmation_method_desc_txt", length = 100)
    private String confirmationMethodDescTxt;

    @Column(name = "confirmation_method_time")
    private Instant confirmationMethodTime;

}

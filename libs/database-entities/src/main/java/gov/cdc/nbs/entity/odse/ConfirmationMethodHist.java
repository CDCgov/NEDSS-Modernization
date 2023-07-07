package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Confirmation_method_hist")
public class ConfirmationMethodHist {
    @EmbeddedId
    private ConfirmationMethodHistId id;

    @Column(name = "confirmation_method_desc_txt", length = 80)
    private String confirmationMethodDescTxt;

    @Column(name = "confirmation_method_time")
    private Instant confirmationMethodTime;

}

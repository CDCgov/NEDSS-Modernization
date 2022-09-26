package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
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
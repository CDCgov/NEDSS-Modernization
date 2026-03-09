package gov.cdc.nbs.time;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class EffectiveTime {

  @Column(name = "effective_from_time")
  private LocalDateTime effectiveFromTime;

  @Column(name = "effective_to_time")
  private LocalDateTime effectiveToTime;
}

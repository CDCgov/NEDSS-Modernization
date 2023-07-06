package gov.cdc.nbs.questionbank.phin.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Compose {

    private LocalDate lockedDate;
    private List<Include> include;

}

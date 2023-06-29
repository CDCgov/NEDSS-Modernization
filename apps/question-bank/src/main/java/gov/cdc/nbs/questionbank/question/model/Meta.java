package gov.cdc.nbs.questionbank.question.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Meta {

    private LocalDate lastUpdated;

}

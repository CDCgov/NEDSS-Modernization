package gov.cdc.nbs.patientlistener.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NestedPhone {
    private String telephoneNbr;
    private String extensionTxt;
}
package gov.cdc.nbs.entity.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class NestedPhone {
    private String telephoneNbr;
    private String extensionTxt;
}

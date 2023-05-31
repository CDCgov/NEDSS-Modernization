package gov.cdc.nbs.patient;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class Results<T> {
    private List<T> content;
    private int total ;
}

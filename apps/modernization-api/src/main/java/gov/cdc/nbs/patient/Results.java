package gov.cdc.nbs.patient;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
public class Results<T> {
    private List<T> content = new ArrayList<>();
    private int total = 0;
}

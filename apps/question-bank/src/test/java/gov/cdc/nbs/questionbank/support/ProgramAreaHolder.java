package gov.cdc.nbs.questionbank.support;

import java.util.List;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.programarea.model.ProgramArea;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ProgramAreaHolder {
    private List<ProgramArea> programAreas;
}

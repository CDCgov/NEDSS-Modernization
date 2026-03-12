package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.programarea.model.ProgramArea;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ProgramAreaHolder {
  private List<ProgramArea> programAreas;
}

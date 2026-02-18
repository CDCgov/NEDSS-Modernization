package gov.cdc.nbs.questionbank.programarea;

import gov.cdc.nbs.questionbank.programarea.entity.ProgramAreaCode;
import gov.cdc.nbs.questionbank.programarea.model.ProgramArea;
import gov.cdc.nbs.questionbank.programarea.repository.ProgramAreaCodeRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProgramAreaFinder {

  private final ProgramAreaCodeRepository repository;

  public ProgramAreaFinder(ProgramAreaCodeRepository repository) {
    this.repository = repository;
  }

  public List<ProgramArea> find() {
    return repository.findAll().stream().map(this::toProgramArea).toList();
  }

  ProgramArea toProgramArea(ProgramAreaCode code) {
    return new ProgramArea(
        code.getId(),
        code.getProgAreaDescTxt(),
        code.getNbsUid(),
        code.getStatusCd().equals('A') ? "Active" : "Inactive");
  }
}

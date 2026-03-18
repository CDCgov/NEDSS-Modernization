package gov.cdc.nbs.questionbank.programarea;

import gov.cdc.nbs.questionbank.programarea.model.ProgramArea;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/program-area")
public class ProgramAreaController {

  private final ProgramAreaFinder finder;

  public ProgramAreaController(ProgramAreaFinder finder) {
    this.finder = finder;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
  public List<ProgramArea> getProgramAreas() {
    return finder.find();
  }
}

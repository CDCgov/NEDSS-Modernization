package gov.cdc.nbs.questionbank.programarea;

import static org.junit.jupiter.api.Assertions.assertEquals;

import gov.cdc.nbs.questionbank.programarea.entity.ProgramAreaCode;
import gov.cdc.nbs.questionbank.programarea.model.ProgramArea;
import gov.cdc.nbs.questionbank.programarea.repository.ProgramAreaCodeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProgramAreaFinderTest {

  @Mock private ProgramAreaCodeRepository repository;

  @InjectMocks private ProgramAreaFinder finder;

  @Test
  void should_map_program_area_code_to_programArea() {
    ProgramAreaCode code = new ProgramAreaCode();
    code.setId("Test Id");
    code.setProgAreaDescTxt("Desc Txt");
    code.setNbsUid(35);
    code.setStatusCd('A');

    ProgramArea programArea = finder.toProgramArea(code);

    assertEquals(code.getId(), programArea.value());
    assertEquals(code.getProgAreaDescTxt(), programArea.display());
    assertEquals(code.getNbsUid(), programArea.nbsId());
    assertEquals("Active", programArea.status());
  }

  @Test
  void should_map_inactive_status() {
    ProgramAreaCode code = new ProgramAreaCode();
    code.setStatusCd('I');

    ProgramArea programArea = finder.toProgramArea(code);
    assertEquals("Inactive", programArea.status());
  }
}

package gov.cdc.nbs.option.person.names;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder;
import gov.cdc.nbs.configuration.nbs.Properties;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;

public class StdHivProgramAreaFinderSteps {
  private final NbsPropertiesFinder finderMock = mock(NbsPropertiesFinder.class);
  private final Properties propertiesMock = mock(Properties.class);
  private List<String> actual;

  @Given("the HIV program areas are:")
  public void set_hiv_program_areas(List<String> hivProgramAreas) {
    when(finderMock.find()).thenReturn(propertiesMock);
    when(propertiesMock.hivProgramAreas()).thenReturn(hivProgramAreas);
  }

  @Given("the STD program areas are:")
  public void set_std_program_areas(List<String> stdProgramAreas) {
    when(finderMock.find()).thenReturn(propertiesMock);
    when(propertiesMock.stdProgramAreas()).thenReturn(stdProgramAreas);
  }

  @When("I get the STD HIV program area values")
  public void get_std_hiv_program_areas() {
    actual = StdHivProgramAreaFinder.values(finderMock);
  }

  @Then("the result should contain the following values:")
  public void confirm_results(List<String> expected) {
    assertEquals(expected, actual);
  }
}

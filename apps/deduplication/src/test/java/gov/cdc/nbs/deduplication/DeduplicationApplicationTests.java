package gov.cdc.nbs.deduplication;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import gov.cdc.nbs.deduplication.dataelements.DataElementsController;

@SpringBootTest
class DeduplicationApplicationTests {

  @Autowired
  private DataElementsController controller;

  @Test
  void contextLoads() {
    assertThat(controller).isNotNull();
  }

}

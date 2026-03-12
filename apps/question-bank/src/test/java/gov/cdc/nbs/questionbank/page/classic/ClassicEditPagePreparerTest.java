package gov.cdc.nbs.questionbank.page.classic;

import static org.mockito.Mockito.inOrder;

import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicManagePagesRequester;
import gov.cdc.nbs.questionbank.page.classic.redirect.outgoing.ClassicViewPageRequester;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClassicEditPagePreparerTest {

  @Mock private ClassicManagePagesRequester managePageRequest;
  @Mock private ClassicViewPageRequester viewPageRequest;

  @InjectMocks private ClassicEditPagePreparer preparer;

  @Test
  void should_call_manage_then_view() {
    InOrder inOrder = inOrder(managePageRequest, viewPageRequest);

    // When the edit page is prepared for viewing
    preparer.prepare(1l);

    // Then the manage page request should be sent
    inOrder.verify(managePageRequest).request();

    // And the view page request should be sent
    inOrder.verify(viewPageRequest).request(1l);

    inOrder.verifyNoMoreInteractions();
  }
}

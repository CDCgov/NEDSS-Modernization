package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.mockito.Mockito.when;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;

@ExtendWith(MockitoExtension.class)
class PageStaticDeletorTest {

    @Mock
    private WaUiMetadataRepository uiMetadatumRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PageStaticDeletor pageStaticDeletor;

    @Test
    void should_delete_static_element_from_page() {
        // var request = new StaticContentRequests.DeleteElement(123L);

        // Long pageId = 321L;

        // WaUiMetadata component = new WaUiMetadata();
        // component.setOrderNbr(7);

        // when(uiMetadatumRepository.findById(request.componentId())).thenReturn(Optional.of(component));

        // pageStaticDeletor.deleteStaticElement(pageId, request);


    }
}

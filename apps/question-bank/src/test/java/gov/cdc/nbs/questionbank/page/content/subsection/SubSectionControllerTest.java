package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UnGroupSubSectionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubSectionControllerTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private SubSectionGrouper grouper;

    @Mock
    private NbsUserDetails details;

    @InjectMocks
    private SubSectionController controller;

    @Test
    void testGroupSubSection() {
        List<GroupSubSectionRequest.Batch> batches = getValidBatchList();
        long subSectionId = 100l;
        String blockName = "BlockName";
        GroupSubSectionRequest request = new GroupSubSectionRequest(subSectionId, blockName, batches);
        when(grouper.group(any(), any(), any())).thenReturn(ResponseEntity.
                ok("Subsection " + subSectionId + " is  Grouped Successfully , Block Name is " + blockName));
        ResponseEntity<String> response = controller.groupSubSection(1L, request, details);
        assertEquals("Subsection " + subSectionId + " is  Grouped Successfully , Block Name is " + blockName, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
    List<GroupSubSectionRequest.Batch> getValidBatchList() {
        List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
        batchList.add(new GroupSubSectionRequest.Batch(101l, 'Y', "header1", 25));
        batchList.add(new GroupSubSectionRequest.Batch(102l, 'Y', "header2", 75));
        return batchList;
    }

    @Test
    void testUnGroupSubSection() {
        List<Long> batches = Arrays.asList(101l,102l);
        long subSectionId = 100l;
        UnGroupSubSectionRequest request = new UnGroupSubSectionRequest(subSectionId, batches);
        when(grouper.unGroup(any(), any(), any())).thenReturn(ResponseEntity.
                ok("Subsection " + subSectionId + " is UnGrouped Successfully "));
        ResponseEntity<String> response = controller.unGroupSubSection(1L, request, details);
        assertEquals("Subsection " + subSectionId + " is UnGrouped Successfully ", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }


}

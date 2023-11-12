package gov.cdc.nbs.questionbank.template.save;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.template.TemplateController;
import gov.cdc.nbs.questionbank.template.TemplateReader;
import gov.cdc.nbs.questionbank.template.request.SaveTemplateRequest;
import gov.cdc.nbs.questionbank.template.response.Template;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemplateControllerTest {

    @Mock
    private TemplateReader reader;

    @InjectMocks
    private TemplateController controller;

    @Mock
    private UserDetailsProvider provider;

    @Test
    void saveTemplateTest() throws Exception {
        // given a valid user is logged in
        NbsUserDetails user = userDetails();
        when(provider.getCurrentUserDetails()).thenReturn(user);

        when(reader.saveTemplate( Mockito.any(SaveTemplateRequest.class),eq(user.getId())))
                .thenReturn(prepareTemplate());

        Template response = controller.saveTemplate(new SaveTemplateRequest(1234L,"",""));

        assertEquals(123L, response.id().longValue());
    }

    private NbsUserDetails userDetails() {
        return new NbsUserDetails(
                1L,
                "test",
                "test",
                "test",
                false,
                false,
                null,
                null,
                null,
                true);
    }

    private Template prepareTemplate(){
        return new Template(123L,"","","",null,123L,"",123l,"");
    }
}

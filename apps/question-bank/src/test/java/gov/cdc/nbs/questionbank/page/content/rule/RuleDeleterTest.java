package gov.cdc.nbs.questionbank.page.content.rule;

import gov.cdc.nbs.questionbank.entity.WaRuleMetadatum;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.rule.exceptions.DeleteRuleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertThrows;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RuleDeleterTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private RuleDeleter deleter;

    @Test
    void should_delete_rule() {
        WaTemplate waTemplate =new WaTemplate();
        waTemplate.setId(100L);
        when(entityManager.find(WaTemplate.class, 100L)).thenReturn(waTemplate);
        WaRuleMetadatum waRuleMetadatum= new WaRuleMetadatum();
        waRuleMetadatum.setId(200L);
        List<WaRuleMetadatum> waRuleMetadatums =new ArrayList<>();
        waRuleMetadatums.add(waRuleMetadatum);
        waTemplate.setWaRuleMetadatums(waRuleMetadatums);
        assertEquals(1,waTemplate.getWaRuleMetadatums().size());//before delete
        deleter.delete(100L, 200L, 300L);
        assertEquals(0,waTemplate.getWaRuleMetadatums().size());//after delete

    }

    @Test
    void should_not_delete_rule_no_page_found() {
        when(entityManager.find(WaTemplate.class, 100L)).thenReturn(null);
        DeleteRuleException exception = assertThrows(DeleteRuleException.class,
                () -> deleter.delete(100L, 20L, 123L));
        assertEquals(("Unable to find page with id: " + 100), exception.getMessage());
    }
    @Test
    void should_not_delete_rule_no_rul_found() {
        WaTemplate waTemplate =new WaTemplate();
        waTemplate.setId(100L);
        when(entityManager.find(WaTemplate.class, 100L)).thenReturn(waTemplate);
        WaRuleMetadatum waRuleMetadatum= new WaRuleMetadatum();
        waRuleMetadatum.setId(200L);
        List<WaRuleMetadatum> waRuleMetadatums =new ArrayList<>();
        waRuleMetadatums.add(waRuleMetadatum);
        waTemplate.setWaRuleMetadatums(waRuleMetadatums);
        PageContentModificationException exception = assertThrows(PageContentModificationException.class,
                () -> deleter.delete(100L, 500L, 123L));
        assertEquals(("Failed to find Page Rule with id: " + 500), exception.getMessage());
    }

    @Test
    void should_add_rule() {
        WaTemplate waTemplate =new WaTemplate();
        waTemplate.setId(100L);

        List<WaRuleMetadatum> waRuleMetadatums =new ArrayList<>();
        waTemplate.setWaRuleMetadatums(waRuleMetadatums);

        PageContentCommand.AddRule command =  new PageContentCommand.AddRule(
                "ruleCd",
                "errMsgTxt",
                "recordStatusCd",
                "javascriptFunction",
                "javascriptFunctionNm",
                100L,
                null
        );
        waTemplate.addRule(command);
        assertEquals(1,waTemplate.getWaRuleMetadatums().size());
    }

}

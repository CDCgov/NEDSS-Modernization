package gov.cdc.nbs.questionbank.pagerules;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WaRuleMetadataTest {
    private CreateRuleRequest ruleRequest;
    private WaRuleMetadata metadata;

    @BeforeEach
    void setUp() {
        metadata = new WaRuleMetadata();
    }

    @Test
    void testWaRuleMetadata() {
        // Set up the CreateRuleRequest


        // Call the method under test


        // Verify the expected values are set in the WaRuleMetadata instance
        assertEquals("Any Source Value", metadata.getSourceValues());
        assertEquals("Rule Description", metadata.getRuleDescText());
        assertEquals("Target Type", metadata.getTargetType());
        assertEquals("Rule Description", metadata.getRuleCd());
        assertEquals("Comparator", metadata.getLogic());
        assertEquals("Target Value", metadata.getTargetQuestionIdentifier());
        assertEquals("Source", metadata.getSourceQuestionIdentifier());
    }

    @Test
    void testWaRuleMetadataWithNullAnySourceValue() {
        // Set up the CreateRuleRequest with null anySourceValue


        // Call the method under test

        // Verify the expected values are set in the WaRuleMetadata instance
        assertNull(metadata.getSourceValues());
        assertEquals("Rule Description", metadata.getRuleDescText());
        assertEquals("Target Type", metadata.getTargetType());
        assertEquals("Rule Description", metadata.getRuleCd());
        assertEquals("Comparator", metadata.getLogic());
        assertEquals("Target Value", metadata.getTargetQuestionIdentifier());
        assertEquals("Source", metadata.getSourceQuestionIdentifier());

        }

    }
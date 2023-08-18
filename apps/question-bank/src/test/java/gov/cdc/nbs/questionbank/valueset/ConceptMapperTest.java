package gov.cdc.nbs.questionbank.valueset;

import static org.junit.Assert.assertEquals;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralId;
import gov.cdc.nbs.questionbank.valueset.response.Concept;

class ConceptMapperTest {

    private ConceptMapper conceptMapper = new ConceptMapper();

    @Test
    void should_map_concept() {
        Instant now = Instant.now();
        Concept concept = conceptMapper.toConcept(codeValueGeneral(now));

        assertEquals("code", concept.localCode());
        assertEquals("codeSetName", concept.codesetName());
        assertEquals("short desc", concept.display());
        assertEquals("descTxt", concept.description());
        assertEquals("conceptCode", concept.conceptCode());
        assertEquals("preferred name", concept.messagingConceptName());
        assertEquals("systemDescTxt", concept.codeSystem());
        assertEquals("statusCd", concept.status());
        assertEquals(now, concept.effectiveFromTime());
        assertEquals(now, concept.effectiveToTime());
    }

    private CodeValueGeneral codeValueGeneral(Instant now) {
        CodeValueGeneral codeValueGeneral = new CodeValueGeneral();
        codeValueGeneral.setId(new CodeValueGeneralId("codeSetName", "code"));
        codeValueGeneral.setCodeShortDescTxt("short desc");
        codeValueGeneral.setCodeDescTxt("descTxt");
        codeValueGeneral.setConceptCode("conceptCode");
        codeValueGeneral.setConceptPreferredNm("preferred name");
        codeValueGeneral.setCodeSystemDescTxt("systemDescTxt");
        codeValueGeneral.setConceptStatusCd("statusCd");
        codeValueGeneral.setEffectiveFromTime(now);
        codeValueGeneral.setEffectiveToTime(now);

        return codeValueGeneral;
    }
}

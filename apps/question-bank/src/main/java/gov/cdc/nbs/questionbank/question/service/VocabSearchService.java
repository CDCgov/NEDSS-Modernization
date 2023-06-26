package gov.cdc.nbs.questionbank.question.service;

import gov.cdc.nbs.questionbank.question.client.PhinVadsClient;
import gov.cdc.nbs.questionbank.question.model.Concept;
import gov.cdc.nbs.questionbank.question.model.Include;
import gov.cdc.nbs.questionbank.question.response.*;
import org.apache.kafka.common.metrics.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VocabSearchService {

    @Autowired
    PhinVadsClient phinVadsClient;

    public ValueSetByOIDResults fetchValueSetInfoByOID(String OID) {
        ValueSetByOIDResults res = null;
            PhinvadsValueSetByIDData result = phinVadsClient.getValueSetByOID(OID);
            if (null != result) {
                List<ValueSetConcept> conceptList = new ArrayList<>();
                Include include = result.getCompose().getInclude().get(1);
                List<Concept> concepts = include.getConcept();
                if (null != concepts && !concepts.isEmpty()) {
                    for (Concept c : concepts) {
                        ValueSetConcept vsc = ValueSetConcept.builder().localCode(c.getCode()).uiDisplayName(c.getDisplay())
                                .conceptCode(c.getCode()).messagingConceptName(c.getDisplay())
                                .codeSystemName(include.getSystem()).conceptCode(c.getCode()).status(result.getStatus())
                                .effectiveFrom(result.getDate()).build();
                        conceptList.add(vsc);
                    }
                }
                res = ValueSetByOIDResults.builder().valueSetType("PHIN").valueSetCode(result.getName()).
                        valueSetName(result.getTitle()).status(result.getStatus()).
                        valueSetDesc(result.getDescription().replaceAll("</?.*?>", "")).
                        valueSetConcepts(conceptList).build();
         }
        return res;
    }

}

package gov.cdc.nbs.questionbank.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import gov.cdc.nbs.questionbank.entity.repository.CodesetRepository;

@Component
public class ValueSetMother {

    @Autowired
    private CodesetRepository codesetRepository;


    public Codeset emptyCodeset() {
        CodesetId id = new CodesetId("test empty codeset classCd", "test empty codesetNm");
        final long emptyCodesetId = 999999000L;
        return codesetRepository.findById(id).orElseGet(() -> {
            Codeset codeset = new Codeset();
            CodesetGroupMetadatum metadatum = new CodesetGroupMetadatum();
            metadatum.setCodeSetNm("Test empy codeset");
            metadatum.setId(emptyCodesetId);

            codeset.setId(id);
            codeset.setCodeSetGroup(metadatum);
            return codesetRepository.save(codeset);
        });
    }

}

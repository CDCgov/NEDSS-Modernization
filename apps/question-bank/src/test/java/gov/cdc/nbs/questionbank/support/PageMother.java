package gov.cdc.nbs.questionbank.support;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;

@Component
public class PageMother {
    private static final String ASEPTIC_MENINGITIS_ID = "10010";
    private static final String BRUCELLOSIS_ID = "10020";

    @Autowired
    private WaTemplateRepository repository;

    private List<WaTemplate> allPages = new ArrayList<>();

    public void clean() {
        repository.deleteAll();
        allPages.clear();
    }

    public WaTemplate brucellosis() {
        return allPages.stream()
                .filter(t -> t.getConditionMappings()
                        .stream()
                        .anyMatch(c -> c.getConditionCd().equals(BRUCELLOSIS_ID)))
                .findFirst()
                .orElseGet(this::createBrucellosisPage);
    }

    public WaTemplate asepticMeningitis() {
        return allPages.stream()
                .filter(t -> t.getConditionMappings()
                        .stream()
                        .anyMatch(c -> c.getConditionCd().equals(ASEPTIC_MENINGITIS_ID)))
                .findFirst()
                .orElseGet(this::createAsepticMeningitisPage);
    }

    private WaTemplate createBrucellosisPage() {
        Instant now = Instant.now();
        WaTemplate page = new WaTemplate();
        page.setTemplateNm("brucellosis page");
        page.setTemplateType("Draft");
        page.setBusObjType("INV");
        page.setNndEntityIdentifier("GEN_Case_Map_v2.0");


        page.setRecordStatusCd("Active");
        page.setRecordStatusTime(now);
        page.setAddTime(now);
        page.setAddUserId(1L);
        page.setLastChgTime(now);
        page.setLastChgUserId(1L);

        PageCondMapping conditionMapping = new PageCondMapping();
        conditionMapping.setWaTemplateUid(page);
        conditionMapping.setConditionCd(BRUCELLOSIS_ID); // From test db condition_code table
        conditionMapping.setAddTime(now);
        conditionMapping.setAddUserId(1l);
        conditionMapping.setLastChgTime(now);
        conditionMapping.setLastChgUserId(1l);

        page.setConditionMappings(Collections.singleton(conditionMapping));
        page = repository.save(page);
        allPages.add(page);
        return page;
    }

    private WaTemplate createAsepticMeningitisPage() {
        Instant now = Instant.now().plusSeconds(5);
        WaTemplate page = new WaTemplate();
        page.setTemplateNm("Aseptic Meningitis");
        page.setTemplateType("Draft");
        page.setBusObjType("INV");
        page.setNndEntityIdentifier("GEN_Case_Map_v2.0");

        page.setRecordStatusCd("Active");
        page.setRecordStatusTime(now);
        page.setAddTime(now);
        page.setAddUserId(1L);
        page.setLastChgTime(now);
        page.setLastChgUserId(1L);

        PageCondMapping conditionMapping = new PageCondMapping();
        conditionMapping.setWaTemplateUid(page);
        conditionMapping.setConditionCd(ASEPTIC_MENINGITIS_ID); // From test db condition_code table
        conditionMapping.setAddTime(now);
        conditionMapping.setAddUserId(1l);
        conditionMapping.setLastChgTime(now);
        conditionMapping.setLastChgUserId(1l);

        page.setConditionMappings(Collections.singleton(conditionMapping));

        page = repository.save(page);
        allPages.add(page);
        return page;
    }
}

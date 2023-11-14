package gov.cdc.nbs.questionbank.page.util;

import gov.cdc.nbs.questionbank.entity.PageCondMapping;
import gov.cdc.nbs.questionbank.entity.WaTemplate;

import java.util.HashSet;
import java.util.Set;

public class PageUtil {

    public static Set<PageCondMapping> copyConditionMappings(Set<PageCondMapping> original, WaTemplate page) {
        if (original == null)
            return original;
        Set<PageCondMapping> copy = new HashSet<>();
        for (PageCondMapping con : original) {
            PageCondMapping aCopy = new PageCondMapping();
            aCopy.setAddTime(con.getAddTime());
            aCopy.setAddUserId(con.getAddUserId());
            aCopy.setConditionCd(con.getConditionCd());
            aCopy.setLastChgTime(con.getLastChgTime());
            aCopy.setLastChgUserId(con.getLastChgUserId());
            aCopy.setWaTemplateUid(page);
            copy.add(aCopy);

        }
        return copy;
    }
}

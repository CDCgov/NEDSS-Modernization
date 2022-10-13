package gov.cdc.nbs.support.util;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import gov.cdc.nbs.entity.odse.NBSEntity;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleLocator;
import lombok.Data;

public class PersonUtil {
    private static final Long CREATED_BY_ID = 999999L;
    private static Long currentId = 40000000L;

    private static Long getId() {
        currentId++;
        return currentId;
    }

    public static LocatorEntries createTeleLocatorEntry(NBSEntity entity) {
        // Tele locator entry
        var locatorId = getId();
        var teleLocator = new TeleLocator();
        teleLocator.setId(locatorId);
        teleLocator.setAddTime(Instant.now());
        teleLocator.setAddUserId(CREATED_BY_ID);
        teleLocator.setPhoneNbrTxt(RandomUtil.getRandomPhoneNumber());
        teleLocator.setRecordStatusCd("ACTIVE");

        EntityLocatorParticipation teleElp = new EntityLocatorParticipation();
        teleElp.setEntityUid(entity);
        teleElp.setId(new EntityLocatorParticipationId(entity.getId(), locatorId));
        teleElp.setCd("PH");
        teleElp.setUseCd("H");
        teleElp.setClassCd("TELE");
        teleElp.setRecordStatusCd("ACTIVE");
        teleElp.setStatusCd('A');
        teleElp.setVersionCtrlNbr((short) 1);

        // Postal locator entry
        locatorId = getId();
        var postalLocator = new PostalLocator();
        postalLocator.setId(locatorId);
        postalLocator.setStreetAddr1(RandomUtil.getRandomString());
        postalLocator.setCntryCd(RandomUtil.getRandomString(3));
        postalLocator.setCityCd(RandomUtil.getRandomString(8));
        postalLocator.setStateCd(RandomUtil.getRandomState());
        postalLocator.setZipCd(RandomUtil.getRandomNumericString(5));
        postalLocator.setRecordStatusCd("ACTIVE");

        var postalElp = new EntityLocatorParticipation();
        postalElp.setEntityUid(entity);
        postalElp.setId(new EntityLocatorParticipationId(entity.getId(), locatorId));
        postalElp.setCd("H"); // Home
        postalElp.setUseCd("H");
        postalElp.setClassCd("PST"); // Postal
        postalElp.setRecordStatusCd("ACTIVE");
        postalElp.setStatusCd('A');
        postalElp.setVersionCtrlNbr((short) 1);

        entity.setEntityLocatorParticipations(Arrays.asList(teleElp, postalElp));
        return new LocatorEntries(Arrays.asList(teleElp, postalElp), teleLocator, postalLocator);
    }

    @Data
    public static class LocatorEntries {
        private final List<EntityLocatorParticipation> entityLocatorParticipations;
        private final TeleLocator teleLocator;
        private final PostalLocator postalLocator;
    }

}

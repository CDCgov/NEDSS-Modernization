package gov.cdc.nbs.support;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.NBSEntity;
import gov.cdc.nbs.entity.odse.Organization;
import gov.cdc.nbs.entity.odse.Person;

public class ProviderMother {

    public static Person johnXerogeanes() {
        final long id = 19100000L;
        var person = new Person();
        person.setId(id);
        person.setCd("PRV");
        person.setFirstNm("John");
        person.setLastNm("Xerogeanes");
        person.setLocalId("PSN10064000GA01");
        person.setStatusCd('A');
        person.setRecordStatusCd(RecordStatus.ACTIVE);
        person.setNBSEntity(new NBSEntity(id, "PSN"));
        person.setVersionCtrlNbr((short) 1);
        return person;
    }

    public static Organization piedmontHospital() {
        final long id = 19200000L;
        var org = new Organization();
        org.setId(id);
        org.setNBSEntity(new NBSEntity(id, "ORG"));
        org.setLocalId("ORG10000000GA01");
        org.setRecordStatusCd(RecordStatus.ACTIVE);
        org.setStandardIndustryClassCd("62");
        org.setStatusCd('A');
        org.setDisplayNm("Piedmont Hospital");
        org.setVersionCtrlNbr((short) 1);
        return org;
    }
}

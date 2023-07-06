package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
class PatientProfileDeletableResolver {

    @Autowired
    private final PatientAssociationCountFinder finder;

    PatientProfileDeletableResolver(final PatientAssociationCountFinder finder) {
        this.finder = finder;
    }

    @SchemaMapping("deletable")
    boolean resolve(final PatientProfile profile) {
        return !profile.recordStatusCd().equals(RecordStatus.LOG_DEL.toString()) && finder.count(profile.id()) == 0;
    }
}

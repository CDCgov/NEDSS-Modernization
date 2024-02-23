package gov.cdc.nbs.patient.profile.general.change;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.search.indexing.PatientSearchIndexer;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.Instant;

@Controller
class PatientGeneralInformationController {

    private final Clock clock;
    private final PatientGeneralInformationChangeService service;

    private final PatientSearchIndexer indexer;

    PatientGeneralInformationController(
        final Clock clock,
        final PatientGeneralInformationChangeService service,
        final PatientSearchIndexer indexer
    ) {
        this.clock = clock;
        this.service = service;
        this.indexer = indexer;
    }

    @MutationMapping("updatePatientGeneralInfo")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('EDIT-PATIENT')")
    public PatientGeneralChangeResult update(@Argument UpdateGeneralInformation input) {

        NbsUserDetails user = SecurityUtil.getUserDetails();

        RequestContext context = new RequestContext(user.getId(), Instant.now(this.clock));

        service.update(context, input);

        indexer.index(input.patient());

        return new PatientGeneralChangeResult(input.patient());
    }
}

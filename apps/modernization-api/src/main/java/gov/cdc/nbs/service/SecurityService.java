package gov.cdc.nbs.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import gov.cdc.nbs.config.security.NbsAuthority;
import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.repository.JurisdictionCodeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private static final String ALL = "ALL";
    private final JurisdictionCodeRepository jurisdictionCodeRepository;

    /**
     * Returns a Set of Ids that are a combination of ProgramAreas and Jurisdictions the user has access to. These are
     * created in the {@link SecurityService#generateOid(Integer, Integer)} method
     */
    public Set<Long> getProgramAreaJurisdictionOids(NbsUserDetails userDetails) {
        var jurisdictionCodes = jurisdictionCodeRepository.findAll();
        var oidsUserHasAccessTo = new HashSet<Long>();
        userDetails.getAuthorities().forEach(e -> {
            if (e.getJurisdiction().equals(ALL)) {
                // for each existing jurisdiction, create an Oid with the program area
                oidsUserHasAccessTo
                        .addAll(jurisdictionCodes.stream().map(jc -> generateOid(jc.getNbsUid(), e.getProgramAreaUid()))
                                .collect(Collectors.toSet()));
            } else {
                // generate an Oid for the jurisdiction and program area
                jurisdictionCodes.stream()
                        .filter(jc -> jc.getId().equals(e.getJurisdiction()))
                        .findFirst()
                        .map(jc -> generateOid(jc.getNbsUid(), e.getProgramAreaUid()))
                        .ifPresent(oidsUserHasAccessTo::add);
            }
        });

        return oidsUserHasAccessTo;
    }

    /**
     * Generates an Oid from a Jurisdiction Uid and a ProgramArea Id
     * <ol>
     * <li>Multiply the Jurisdiction nbs uid by 100,000
     * <li>Add the Program Area Id to the result
     * <p>
     *
     * Functionality copied from legacy NBS: ProgramAreaJurisdictionUtil.getPAJHash
     */
    private Long generateOid(Integer jurisdictionNbsUid, Integer programAreaId) {
        return (jurisdictionNbsUid * 100_000L) + programAreaId;
    }

    /**
     * Create a set of program area codes the user has access to
     */
    public Set<String> getProgramAreaCodes(NbsUserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(NbsAuthority::getProgramArea)
                .collect(Collectors.toSet());
    }
}

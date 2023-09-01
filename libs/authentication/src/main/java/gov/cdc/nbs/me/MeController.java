package gov.cdc.nbs.me;

import gov.cdc.nbs.authentication.NbsAuthority;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authorization.permission.PermissionFinder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MeController {


    private final PermissionFinder finder;

    public MeController(final PermissionFinder finder) {
        this.finder = finder;
    }

    @GetMapping("nbs/api/me")
    Me me(@AuthenticationPrincipal final NbsUserDetails details) {

        List<String> authorities =
            this.finder.find(details.getId())
                .stream()
                .map(p -> p.operation() + '-' + p.object())
                .toList();

        List<String> permissions = details.getAuthorities()
            .stream()
            .map(NbsAuthority::getAuthority)
            .distinct()
            .toList();

        return new Me(
            details.getId(),
            details.getFirstName(),
            details.getLastName(),
            permissions
        );
    }

}

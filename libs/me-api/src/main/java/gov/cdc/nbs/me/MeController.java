package gov.cdc.nbs.me;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authorization.permission.PermissionFinder;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
class MeController {

    private final PermissionFinder finder;

    public MeController(final PermissionFinder finder) {
        this.finder = finder;
    }

    @ApiOperation(
        value = "Me",
        notes = "Provides details about the user associated with the request.",
        tags = "User"
    )
    @ApiImplicitParam(
        name = "Authorization",
        required = true,
        paramType = "header",
        dataTypeClass = String.class
    )
    @GetMapping("nbs/api/me")
    Me me(@ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {

        List<String> permissions =
            this.finder.find(details.getId())
                .stream()
                .map(p -> p.operation() + '-' + p.object())
                .toList();

        return new Me(
            details.getId(),
            details.getFirstName(),
            details.getLastName(),
            permissions
        );
    }

}

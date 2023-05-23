package gov.cdc.nbs.authentication;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public class NbsAuthorities {

    public static Predicate<NbsAuthority> allows(final String permission) {
        return authority -> Objects.equals(authority.getAuthority(), permission);
    }

    public static Predicate<NbsAuthority> allowsAny(final String... permissions) {
        return Arrays.stream(permissions)
            .map(NbsAuthorities::allows)
            .reduce(ignored -> false, Predicate::or);
    }

}

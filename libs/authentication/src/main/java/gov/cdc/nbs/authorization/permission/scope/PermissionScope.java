package gov.cdc.nbs.authorization.permission.scope;

import java.util.Collection;
import java.util.List;

/**
 * Defines the data that a user is able to act upon.
 *
 * @param any    A {@code Collection} of "oids" built from program-area and jurisdiction.
 * @param shared A {@code Collection} of "oids" built from program-area and jurisdiction.
 */
public record PermissionScope(Collection<Long> any, Collection<Long> shared) {

    public static PermissionScope none() {
        return new PermissionScope(List.of(), List.of());
    }

    public static PermissionScope any(final long area) {
        return new PermissionScope(List.of(area), List.of());
    }

    public static PermissionScope shared(final long area) {
        return new PermissionScope(List.of(), List.of(area));
    }

    public boolean allowed() {
        return !any().isEmpty() || !shared().isEmpty();
    }

}

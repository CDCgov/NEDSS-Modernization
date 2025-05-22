package gov.cdc.nbs.authorization.permission.scope;

import java.util.Collection;
import java.util.List;

/**
 * Defines the data that a user is able to act upon using hashes that represent access to a program-area within a
 * jurisdiction.  The hashes are split into two areas;
 *
 * <ul>
 *     <li>{@code any} - Has access to any data within the program-area / jurisdiction</li>
 *     <li>{@code shared} - Has access to only shared data within the program-area / jurisdiction</li>
 * </ul>
 *
 * @param any    A {@code Collection} of "oids" built from program-area and jurisdiction.
 * @param shared A {@code Collection} of "oids" built from program-area and jurisdiction.
 */
public record PermissionScope(Collection<Long> any, Collection<Long> shared) {

    private static final PermissionScope NONE = new PermissionScope(List.of(), List.of());

    public static PermissionScope none() {
        return NONE;
    }

    public static PermissionScope any(final long area) {
        return new PermissionScope(List.of(area), List.of());
    }

    public static PermissionScope shared(final long area) {
        return new PermissionScope(List.of(), List.of(area));
    }

    public boolean allowed() {
        return !any.isEmpty() || !shared.isEmpty();
    }

}

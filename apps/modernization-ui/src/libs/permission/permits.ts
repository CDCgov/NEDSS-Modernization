import { equalsIgnoreCase, Predicate } from 'utils/predicate';

/**
 * Returns a {@link Predicate} that evaluates to true when a permission is permitted.
 * A permission is considered permitted if it exists (regardless of case) within the
 * permissions passed.
 *
 * @param {string} permission The name of the permission to test for
 * @return {Predicate<string[]>}
 */
const permits =
    (permission: string): Predicate<string[]> =>
    (permissions: string[]) =>
        permissions.find(equalsIgnoreCase(permission)) !== undefined;

export { permits };

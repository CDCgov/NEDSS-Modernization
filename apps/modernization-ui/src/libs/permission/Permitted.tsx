import { ReactNode, useMemo } from 'react';

import { Shown } from 'conditional-render';
import { Predicate } from 'utils';

import { usePermissions } from './usePermissions';

export type PermittedProps = {
    /** The name of the permission required or a predicate that resolves the permission */
    permission: string | Predicate<string[]>;
    /** The children to render if permissions are satisfied */
    children: ReactNode | ReactNode[];
    /** The fallback to render if permissions are not satisfied */
    fallback?: ReactNode | ReactNode[];
};

/**
 * Renders the children of this component only if the permission is allowed.  If specified,
 * the fallback component is displayed when the permission fails.
 *
 * A permission of 'string' is equivalent to using {@link permits}.
 */
const Permitted = ({ permission, children, fallback }: PermittedProps) => {
    const { allows, permissions } = usePermissions();

    const allowed = useMemo(() => {
        if (typeof permission === 'string') {
            return allows(permission);
        } else if (permission) {
            return permission(permissions);
        } else {
            return false;
        }
    }, [permission, allows, ...permissions]);

    return (
        <Shown fallback={fallback} when={allowed}>
            {children}
        </Shown>
    );
};

export { Permitted };

import { useCallback, useMemo } from 'react';
import { useUser } from 'user';
import { Predicate } from 'utils';

type Interaction = {
    permissions: string[];
    allows: Predicate<string>;
};

const usePermissions = (): Interaction => {
    const {
        state: { user }
    } = useUser();

    const permissions = useMemo(() => user?.permissions ?? [], [user?.identifier]);
    const allows = useCallback(
        (permission: string) =>
            permissions.find((granted) => granted.toUpperCase() == permission.toUpperCase()) !== undefined,
        [user?.identifier]
    );

    return { permissions, allows };
};

export { usePermissions };

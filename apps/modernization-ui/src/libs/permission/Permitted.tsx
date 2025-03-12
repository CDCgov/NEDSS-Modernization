import { ReactNode } from 'react';
import { Shown } from 'conditional-render';
import { usePermissions } from './usePermissions';

type PermittedProps = {
    /** @deprecated Use include or exclude properties */
    permission?: string;
    /** One or more permissions that MUST be present in the user's permission */
    include?: string[];
    /** One or more permissions that MUST NOT be present in the user's permissions */
    exclude?: string[];
    /** Whether all or any permissions are included or excluded */
    mode?: 'all' | 'any';
    /** The children to render if permissions are satisfied */
    children: ReactNode | ReactNode[];
    /** The fallback to render if permissions are satisfied */
    fallback?: ReactNode | String;
};

const Permitted = ({ permission, include, exclude, mode, children, fallback }: PermittedProps) => {
    const { allows } = usePermissions();
    const permissionList = permission ? [permission] : undefined;
    include = include ?? permissionList;
    const checkIncluded = !include || (mode === 'any' ? include.some(allows) : include.every(allows));
    const checkExcluded = !exclude || (mode === 'any' ? !exclude.some(allows) : !exclude.every(allows));
    const allowed = checkIncluded && checkExcluded;

    return (
        <Shown fallback={fallback} when={allowed}>
            {children}
        </Shown>
    );
};

export { Permitted };

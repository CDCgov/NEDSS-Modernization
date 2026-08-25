import { Permitted } from 'libs/permission';
import { Predicate } from 'utils';

import styles from './NavBar.module.scss';

type NavLinkProps = {
    url: string;
    name: string;
    includeSeparator?: boolean;
};

export const NavBarLink = ({ url, name, includeSeparator = false }: NavLinkProps) => {
    return (
        <td className={styles.navLink}>
            {includeSeparator && <span> | </span>}
            <a href={url}>{name}</a>
        </td>
    );
};

export const PermittedNavBarLink = ({
    permission,
    ...props
}: NavLinkProps & {
    permission: string | Predicate<string[]>;
}) => {
    return (
        <Permitted permission={permission}>
            <NavBarLink {...props} />
        </Permitted>
    );
};

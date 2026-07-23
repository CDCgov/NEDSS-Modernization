import styles from './NavBar.module.scss';

type NavLinkProps = {
    url: string;
    name: string;
    includeSeparator?: boolean;
};

export const NavLink = ({ url, name, includeSeparator = false }: NavLinkProps) => {
    return (
        <td className={styles.navLink}>
            {includeSeparator && <span> | </span> }
            <a href={url}>{ name }</a>
        </td>
    );
};

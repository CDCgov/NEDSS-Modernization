import { Link } from 'react-router-dom';
import styles from './side-nav.module.scss';
import { Heading } from 'components/heading';

export type ActiveEntry = {
    name: string;
};

export type NavLink = {
    name: string;
    href: string;
    external?: boolean;
};
export type NavEntry = ActiveEntry | NavLink;
type Props = {
    title: string;
    entries: NavEntry[];
};

const asLink = (link: NavLink) => {
    return link.external ? <a href={link.href}>{link.name}</a> : <Link to={link.href}>{link.name}</Link>;
};

const asActive = (navEntry: NavEntry) => {
    return <span className={styles.active}>{navEntry.name}</span>;
};

const resolveLinkType = (navEntry: NavEntry) => {
    return 'href' in navEntry ? asLink(navEntry) : asActive(navEntry);
};
export const SideNav = ({ title, entries }: Props) => {
    return (
        <div className={styles.sideNav}>
            <Heading level={2}>{title}</Heading>
            <nav className={styles.navEntries}>
                <ol>
                    {entries.map((e, index) => (
                        <li key={index}>{resolveLinkType(e)}</li>
                    ))}
                </ol>
            </nav>
        </div>
    );
};

import { Link } from 'react-router-dom';
import styles from './side-nav.module.scss';
import { Heading } from 'components/heading';
import { Children, ReactElement, ReactNode } from 'react';
import classNames from 'classnames';

type ActiveNavEntryProps = {
    name: string;
    active: boolean;
};

type InternalNavEntryProps = {
    name: string;
    href: string;
};

type ExternalNavEntryProps = {
    name: string;
    href: string;
    external: true;
};

const asLink = (entry: InternalNavEntryProps | ExternalNavEntryProps) => {
    return 'external' in entry ? (
        <a href={entry.href}>{entry.name}</a>
    ) : (
        <Link to={entry.href ?? ''}>{entry.name}</Link>
    );
};

const asActive = (navEntry: ActiveNavEntryProps) => {
    return <span className={styles.active}>{navEntry.name}</span>;
};

type NavEntryProps = ActiveNavEntryProps | InternalNavEntryProps | ExternalNavEntryProps;
export const NavEntry = (props: NavEntryProps) => {
    return 'active' in props ? asActive(props) : asLink(props);
};

type Props = {
    title: string;
    className?: string;
    children: ReactElement<NavEntryProps> | ReactElement<NavEntryProps>[];
};
/**
 * Accepts 1 or more children of type {@link NavEntry}. If a child is marked `active`
 * only the name property will be displayed in the list and no `onClick` action will be performed.
 *
 * Marking a {@link NavEntry} `external` will generate an anchor tag instead of a {@link Link}
 *
 * @param {NavEntry} 1 or more NavEntry
 * @return {ReactNode} rendered element with supplied children in an unordered list
 */
export const SideNavigation = ({ title, className, children }: Props): ReactNode => {
    return (
        <div className={classNames(styles.sideNav, className)}>
            <Heading level={1}>{title}</Heading>
            <nav className={styles.navEntries}>
                <ul>
                    {Children.map(children, (child, key) => (
                        <li key={key}>{child}</li>
                    ))}
                </ul>
            </nav>
        </div>
    );
};

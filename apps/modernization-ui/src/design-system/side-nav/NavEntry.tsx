import { Link } from 'react-router';
import styles from './side-nav.module.scss';
import classNames from 'classnames';

type Entry = {
    name: string;
    active?: boolean | (() => boolean);
};

type InternalEntry = Entry & {
    path: string;
};

type ExternalEntry = Entry & {
    href: string;
};

const isInternal = (entry: NavEntryProps): entry is InternalEntry => 'path' in entry;

const isExternal = (entry: NavEntryProps): entry is ExternalEntry => 'href' in entry;

const isActive = (entry: NavEntryProps): boolean =>
    typeof entry.active === 'function' ? entry.active() : Boolean(entry.active);

const render = (entry: NavEntryProps) => {
    if (isActive(entry)) {
        return (
            <span className={classNames({ [styles.active]: entry.active })} aria-current="page">
                {entry.name}
            </span>
        );
    } else if (isExternal(entry)) {
        return <a href={entry.href}>{entry.name}</a>;
    } else if (isInternal(entry)) {
        return <Link to={entry.path}>{entry.name}</Link>;
    }
};

type NavEntryProps = Entry | InternalEntry | ExternalEntry;

const NavEntry = (props: NavEntryProps) => <li>{render(props)}</li>;

export { NavEntry };

export type { NavEntryProps };

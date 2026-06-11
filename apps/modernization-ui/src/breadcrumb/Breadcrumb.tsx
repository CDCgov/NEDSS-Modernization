import { NavLink } from 'react-router';
import { Icon } from '@trussworks/react-uswds';

import styles from './breadcrumb.module.scss';
import { ReactNode } from 'react';

type Crumb = {
    name: string;
    to: string;
    position: number;
};

type Props = {
    start: string;
    children: ReactNode;
    currentPage?: string;
    crumbs?: Crumb[];
};

const Breadcrumb = ({ start, children, currentPage, crumbs }: Props) => {
    const sortedCrumbs = crumbs?.sort((a, b) => a.position - b.position);

    return (
        <nav className={styles.breadcrumb}>
            <ol>
                <li>
                    <NavLink to={start}>
                        <Icon.ArrowBack size={3} aria-label="Back arrow" />
                        {children}
                    </NavLink>
                </li>
                {sortedCrumbs &&
                    sortedCrumbs.map((crumb) => (
                        <li key={crumb.name}>
                            <NavLink to={crumb.to}>{crumb.name}</NavLink>
                        </li>
                    ))}
                {currentPage && <li>{currentPage}</li>}
            </ol>
        </nav>
    );
};

// Same as above but for navigating "out" of mod and back to NBS 6
const Nbs6Breadcrumb = ({ start, children, currentPage, crumbs }: Props) => {
    const sortedCrumbs = crumbs?.sort((a, b) => a.position - b.position);

    return (
        <nav className={styles.breadcrumb}>
            <ol>
                <li>
                    <a href={start}>
                        <Icon.ArrowBack size={3} aria-label="Back arrow" />
                        {children}
                    </a>
                </li>
                {sortedCrumbs &&
                    sortedCrumbs.map((crumb) => (
                        <li key={crumb.name}>
                            <a href={crumb.to}>{crumb.name}</a>
                        </li>
                    ))}
                {currentPage && <li>{currentPage}</li>}
            </ol>
        </nav>
    );
};

export { Breadcrumb, Nbs6Breadcrumb };

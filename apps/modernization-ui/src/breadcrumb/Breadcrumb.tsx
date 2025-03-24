import { NavLink } from 'react-router';
import { Icon } from '@trussworks/react-uswds';

import styles from './breadcrumb.module.scss';

type Crumb = {
    name: string;
    to: string;
    position: number;
};

type Props = {
    start: string;
    children: string;
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
                        <Icon.ArrowBack size={3} />
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

export { Breadcrumb };

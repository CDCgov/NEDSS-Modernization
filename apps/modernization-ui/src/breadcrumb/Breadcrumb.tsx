import { NavLink } from 'react-router-dom';
import { Icon } from '@trussworks/react-uswds';

import styles from './breadcrumb.module.scss';

type Props = {
    start: string;
    children: string;
    currentPage?: string;
};

const Breadcrumb = ({ start, children, currentPage }: Props) => {
    return (
        <nav className={styles.breadcrumb}>
            <ol>
                <li>
                    <NavLink to={start}>
                        <Icon.ArrowBack size={3} />
                        {children}
                    </NavLink>
                </li>
                {currentPage && <li>{currentPage}</li>}
            </ol>
        </nav>
    );
};

export { Breadcrumb };

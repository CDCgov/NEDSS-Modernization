import { ReactNode } from 'react';
import classNames from 'classnames';

import styles from './patient-search-header.module.scss';
import { Link } from '@trussworks/react-uswds';
import { Icon } from 'design-system/icon';

type ActionsRenderer = () => ReactNode;

type Props = {
    className?: string;
    actions?: ActionsRenderer;
};

const PatientSearchHeader = ({ className, actions }: Props) => {
    return (
        <nav className={classNames(styles.navigation, className)}>
            <h1>Patient Search 7 Beta</h1>
            <div className={styles.links}>
                <div className={styles.linkContainer}>
                    <Link href="/nbs/LoadFindPatient1.do?ContextAction=GlobalPatient">Go to classic search</Link>
                    <Icon name="info_outline" color="#265e9d" />
                </div>
            </div>

            {actions && actions()}
        </nav>
    );
};

export { PatientSearchHeader };

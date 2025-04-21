import { Link } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { Hint } from 'design-system/hint';
import { ReactNode } from 'react';
import styles from './patient-search-header.module.scss';

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
                    <Link
                        href="/nbs/LoadFindPatient1.do?ContextAction=GlobalPatient"
                        aria-describedby="classic-search-hint">
                        Go to classic search
                    </Link>
                    <div className={styles.infoIconContainer}>
                        <Hint id="classic-search-hint">
                            <span>
                                <b>We are modernizing search</b>
                                <br />
                                To perform an event search or save a new custom queue, continue using classic search.
                            </span>
                        </Hint>
                    </div>
                </div>
            </div>

            {actions?.()}
        </nav>
    );
};

export { PatientSearchHeader };

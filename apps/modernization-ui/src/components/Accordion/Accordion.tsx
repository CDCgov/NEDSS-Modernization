import { ReactNode } from 'react';

import sprite from '@uswds/uswds/img/sprite.svg';
import styles from './accordion.module.scss';

type Props = {
    title?: string;
    children: ReactNode;
    open?: boolean;
};

export const Accordion = ({ title, children, open = false }: Props) => {
    return (
        <details className={styles.accordian} open={open}>
            <summary>
                <h3>{title}</h3>
                <svg role="img" aria-label={`Expand ${title}`} className={styles.closed}>
                    <use xlinkHref={`${sprite}#add`} />
                </svg>
                <svg role="img" aria-label={`Collapse ${title}`} className={styles.opened}>
                    <use xlinkHref={`${sprite}#remove`} />
                </svg>
            </summary>
            {children}
        </details>
    );
};

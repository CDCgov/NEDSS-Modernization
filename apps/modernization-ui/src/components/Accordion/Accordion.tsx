import { ReactNode } from 'react';

import styles from './accordion.module.scss';
import { Icon } from 'design-system/icon';

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
                    <Icon name={'expand_more'} />
                </svg>
                <svg role="img" aria-label={`Collapse ${title}`} className={styles.opened}>
                    <Icon name={'expand_less'} />
                </svg>
            </summary>
            {children}
        </details>
    );
};

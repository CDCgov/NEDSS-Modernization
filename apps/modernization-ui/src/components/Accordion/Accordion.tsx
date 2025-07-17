import { ReactNode } from 'react';

import styles from './accordion.module.scss';
import { Icon } from 'design-system/icon';

type Props = {
    title?: string;
    children: ReactNode;
    open?: boolean;
    id?: string;
};

export const Accordion = ({ title, children, open = false, id }: Props) => {
    return (
        <details className={styles.accordian} open={open} id={id} role="region">
            <summary>
                <span>{title}</span>
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

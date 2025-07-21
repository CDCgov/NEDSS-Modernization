import { ReactNode, useId, useState } from 'react';

import styles from './accordion.module.scss';
import { Icon } from 'design-system/icon';

type Props = {
    title?: string;
    children: ReactNode;
    open?: boolean;
    id?: string;
};

export const Accordion = ({ title, children, open = false, id }: Props) => {
    const [isExpanded, setIsExpanded] = useState(open);
    const accordionId = id || useId();
    const panelId = `panel-${accordionId}`;

    const handleToggle = () => setIsExpanded((prev) => !prev);

    return (
        <div className={styles.accordion}>
            <h2>
                <button
                    type="button"
                    aria-expanded={isExpanded}
                    className={styles.accordionTrigger}
                    aria-controls={panelId}
                    id={accordionId}
                    onClick={handleToggle}>
                    <span className={styles.accordionTitle}>
                        {title}
                        <Icon name={isExpanded ? 'expand_less' : 'expand_more'} />
                    </span>
                </button>
            </h2>
            <div
                id={panelId}
                role="region"
                aria-label={title}
                aria-labelledby={accordionId}
                className={styles.accordionPanel}
                hidden={!isExpanded}>
                <div>{children}</div>
            </div>
        </div>
    );
};

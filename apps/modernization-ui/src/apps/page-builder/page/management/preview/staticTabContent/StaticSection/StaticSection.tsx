import { ReactNode, useState } from 'react';
import { Icon } from '@trussworks/react-uswds';
import styles from './static-section.module.scss';

type Props = {
    title: string;
    children?: ReactNode | ReactNode[];
};
export const StaticSection = ({ title, children }: Props) => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);

    return (
        <div className={styles.section}>
            <div className={styles.header}>
                <h3>{title}</h3>
                <div className={styles.collapseIcon}>
                    {isExpanded ? (
                        <Icon.ExpandLess size={4} onClick={() => setIsExpanded(false)} />
                    ) : (
                        <Icon.ExpandMore size={4} onClick={() => setIsExpanded(true)} />
                    )}
                </div>
            </div>
            {isExpanded && <div className={styles.sectionContent}>{children}</div>}
        </div>
    );
};

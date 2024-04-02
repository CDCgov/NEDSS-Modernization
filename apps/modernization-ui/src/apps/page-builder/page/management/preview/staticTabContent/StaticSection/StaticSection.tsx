import { ReactNode, useState } from 'react';
import { Heading } from 'components/heading';
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
                <Heading level={2}>{title}</Heading>
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

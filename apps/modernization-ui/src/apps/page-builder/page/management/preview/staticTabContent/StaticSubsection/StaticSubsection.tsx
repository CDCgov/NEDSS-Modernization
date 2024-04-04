import { ReactNode, useState } from 'react';
import styles from './static-subsection.module.scss';
import { Heading } from 'components/heading';
import { Icon } from '@trussworks/react-uswds';

type Props = {
    title: string;
    children?: ReactNode | ReactNode[];
};
export const StaticSubsection = ({ title, children }: Props) => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);

    return (
        <div className={styles.subsection}>
            <div className={styles.subsectionHeader}>
                <Heading level={3}>{title}</Heading>
                <div className={styles.collapseIcon}>
                    {isExpanded ? (
                        <Icon.ExpandLess size={4} onClick={() => setIsExpanded(false)} />
                    ) : (
                        <Icon.ExpandMore size={4} onClick={() => setIsExpanded(true)} />
                    )}
                </div>
            </div>
            {isExpanded && children}
        </div>
    );
};

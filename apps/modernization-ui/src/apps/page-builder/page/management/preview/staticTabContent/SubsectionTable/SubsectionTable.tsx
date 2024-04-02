import { Heading } from 'components/heading';
import { useState } from 'react';
import { Icon } from '@trussworks/react-uswds';
import styles from './subsection-table.module.scss';

type Props = {
    title: string;
    description: string;
    columns: string[];
};
export const SubsectionTable = ({ title, columns, description }: Props) => {
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
            {isExpanded && (
                <>
                    <div className={styles.description}>{description}</div>
                    <div className={styles.tableColumns}>
                        {columns.map((c, k) => (
                            <div className={styles.column} key={k}>
                                {c}
                            </div>
                        ))}
                    </div>
                    <div className={styles.tableText}>Nothing found to display</div>
                </>
            )}
        </div>
    );
};

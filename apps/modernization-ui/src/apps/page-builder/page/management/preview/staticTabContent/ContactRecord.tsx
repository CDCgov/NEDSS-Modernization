import { useState } from 'react';
import styles from './static-tab.module.scss';
import { Icon } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { SubsectionTable } from './SubsectionTable/SubsectionTable';

export const ContactRecord = () => {
    const [isExpanded, setIsExpanded] = useState<boolean>(true);

    return (
        <div className={styles.section}>
            <div className={styles.header}>
                <Heading level={2}>Contact records</Heading>
                <div className={styles.collapseIcon}>
                    {isExpanded ? (
                        <Icon.ExpandLess size={4} onClick={() => setIsExpanded(false)} />
                    ) : (
                        <Icon.ExpandMore size={4} onClick={() => setIsExpanded(true)} />
                    )}
                </div>
            </div>
            {isExpanded && (
                <div className={styles.sectionContent}>
                    <SubsectionTable
                        title="Contact Named by Patient"
                        description="The following contacts were named within the patient's investigation: "
                        columns={[
                            'Date named',
                            'Contact record ID',
                            'Name',
                            'Priority',
                            'Disposition',
                            'Investigation ID'
                        ]}
                    />

                    <SubsectionTable
                        title="Patient Named By Contacts"
                        description="The following contacts named within their investigation and have been associated to the patient's investigation: "
                        columns={[
                            'Date named',
                            'Contact record ID',
                            'Name',
                            'Priority',
                            'Disposition',
                            'Investigation ID'
                        ]}
                    />
                </div>
            )}
        </div>
    );
};

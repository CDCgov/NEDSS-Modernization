import { StaticSection } from './StaticSection/StaticSection';
import { StaticSubsection } from './StaticSubsection/StaticSubsection';
import { SubsectionTable } from './SubsectionTable/SubsectionTable';
import styles from './static-tab.module.scss';

export const SupplementalInfo = () => {
    return (
        <div className={styles.content}>
            <StaticSection title="Associations">
                <SubsectionTable
                    title="Associated Lab Reports"
                    description="The following contacts were named within the patient's investigation:"
                    columns={['Ordered Test', 'Resulted Test(s)']}
                />
                <SubsectionTable
                    title="Associated Morbidity Reports"
                    description="The following contacts named within their investigation and have been associated to the patient's investigation:"
                    columns={['Date Received', 'Condition', 'Report Date', 'Type', 'Observation ID']}
                />
                <SubsectionTable title="Associated Treatments" columns={['Date', 'Treatment', 'Treament ID']} />
                <SubsectionTable
                    title="Associated Vaccinations"
                    columns={['Date Administered', 'Vaccine Administered', 'Vaccination ID']}
                />
                <SubsectionTable
                    title="Associated Documents"
                    columns={['Date Received', 'Type', 'Purpose', 'Description', 'Document ID']}
                />
            </StaticSection>
            <StaticSection title="Notes and Attachments">
                <SubsectionTable title="Notes" description="" columns={['Date added', 'Added by', 'Note', 'Private']} />
                <SubsectionTable title="Attachments" columns={['Date added', 'Added by', 'File Name', 'Description']} />
            </StaticSection>
            <StaticSection title="History">
                <StaticSubsection title="Investigation History">
                    <div className={styles.subsectionText}>No history found to display</div>
                </StaticSubsection>
                <StaticSubsection title="Notification History">
                    <div className={styles.subsectionText}>No notification history found</div>
                </StaticSubsection>
            </StaticSection>
        </div>
    );
};

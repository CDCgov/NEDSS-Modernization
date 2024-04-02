import { StaticSection } from './StaticSection/StaticSection';
import { SubsectionTable } from './SubsectionTable/SubsectionTable';
import styles from './static-tab.module.scss';

export const ContactRecord = () => {
    return (
        <div className={styles.content}>
            <StaticSection title="Contact records">
                <SubsectionTable
                    title="Contact Named by Patient"
                    description="The following contacts were named within the patient's investigation: "
                    columns={['Date named', 'Contact record ID', 'Name', 'Priority', 'Disposition', 'Investigation ID']}
                />
                <SubsectionTable
                    title="Patient Named By Contacts"
                    description="The following contacts named within their investigation and have been associated to the patient's investigation: "
                    columns={['Date named', 'Contact record ID', 'Name', 'Priority', 'Disposition', 'Investigation ID']}
                />
            </StaticSection>
        </div>
    );
};

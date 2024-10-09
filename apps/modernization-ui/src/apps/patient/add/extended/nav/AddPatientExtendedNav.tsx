import { NavSection, InPageNavigation } from 'design-system/inPageNavigation/InPageNavigation';
import styles from './in-page-nav.module.scss';

export const AddPatientExtendedInPageNav = () => {
    const sections: NavSection[] = [
        { id: 'administrative', label: 'Administrative' },
        { id: 'names', label: 'Name' },
        { id: 'addresses', label: 'Address' },
        { id: 'phoneEmails', label: 'Phone & email' },
        { id: 'identifications', label: 'Identification' },
        { id: 'races', label: 'Race' },
        { id: 'ethnicity', label: 'Ethnicity' },
        { id: 'sexAndBirth', label: 'Sex & birth' },
        { id: 'mortality', label: 'Mortality' },
        { id: 'generalInformation', label: 'General patient information' }
    ];

    return (
        <aside className={styles.aside}>
            <InPageNavigation title="On this page" sections={sections} />
        </aside>
    );
};

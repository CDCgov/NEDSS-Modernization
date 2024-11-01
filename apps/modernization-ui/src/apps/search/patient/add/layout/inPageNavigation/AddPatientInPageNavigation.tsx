import { NavSection, InPageNavigation } from 'design-system/inPageNavigation/InPageNavigation';
import styles from './in-page-nav.module.scss';

type AddPatientInPageNavigationProps = {
    sections: NavSection[];
    title: string;
};

export const AddPatientInPageNavigation = ({ title, sections }: AddPatientInPageNavigationProps) => {
    return (
        <aside className={styles.aside}>
            <InPageNavigation title={title} sections={sections} />
        </aside>
    );
};

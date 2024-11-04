import { NavSection, InPageNavigation } from 'design-system/inPageNavigation/InPageNavigation';
import styles from './in-page-nav.module.scss';

type AddPatientInPageNavigationProps = {
    sections: NavSection[];
};

export const AddPatientInPageNavigation = ({ sections }: AddPatientInPageNavigationProps) => {
    return (
        <aside className={styles.aside}>
            <InPageNavigation title="On this page" sections={sections} />
        </aside>
    );
};

import GeneralSearchFields from './GeneralSearchFields';
import styles from './InvestigationSearchForm.module.scss';
import CriteriaSearchFields from './CriteriaSearchFields';
import { Accordion } from 'components/Accordion/Accordion';

const InvestigationSearchForm = () => {
    return (
        <div className={styles.investigationSearchContainer}>
            <Accordion title="General search">
                <GeneralSearchFields />
            </Accordion>
            <Accordion title="Investigation criteria">
                <CriteriaSearchFields />
            </Accordion>
        </div>
    );
};

export default InvestigationSearchForm;

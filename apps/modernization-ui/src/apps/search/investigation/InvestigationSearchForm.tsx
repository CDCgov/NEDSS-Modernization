import GeneralSearchFields from './GeneralSearchFields';
import styles from './InvestigationSearchForm.module.scss';
import CriteriaSearchFields from './CriteriaSearchFields';

const InvestigationSearchForm = () => {
    return (
        <div className={styles.investigationSearchContainer}>
            <GeneralSearchFields />
            <CriteriaSearchFields />
        </div>
    );
};

export default InvestigationSearchForm;

import styles from './patient-match-body.module.scss';
import { BlockingCriteria } from './BlockingCriteria/BlockingCriteria';
import { MatchingCriteria } from './MatchingCriteria/MatchingCriteria';
import { MatchingBounds } from './MatchingBounds/MatchingBounds';
const PatientMatchBody = () => {
    return (
        <div className={styles.configurationDetails}>
            <BlockingCriteria />
            <MatchingCriteria />
            <MatchingBounds />
        </div>
    );
};

export default PatientMatchBody;

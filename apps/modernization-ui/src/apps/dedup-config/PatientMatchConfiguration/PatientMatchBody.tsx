import { BlockingCriteria } from './BlockingCriteria/BlockingCriteria';
import { MatchingCriteria } from './MatchingCriteria/MatchingCriteria';
import { MatchingBounds } from './MatchingBounds/MatchingBounds';

const PatientMatchBody = () => {
    return (
        <>
            <BlockingCriteria />
            <MatchingCriteria />
            <MatchingBounds />
        </>
    );
};

export default PatientMatchBody;

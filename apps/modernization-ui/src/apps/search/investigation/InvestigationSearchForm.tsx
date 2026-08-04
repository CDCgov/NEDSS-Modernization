import { Accordion } from 'components/Accordion/Accordion';

import CriteriaSearchFields from './CriteriaSearchFields';
import GeneralSearchFields from './GeneralSearchFields';

const InvestigationSearchForm = () => {
    return (
        <>
            <Accordion title="General search" open={true}>
                <GeneralSearchFields />
            </Accordion>
            <Accordion title="Investigation criteria">
                <CriteriaSearchFields />
            </Accordion>
        </>
    );
};

export { InvestigationSearchForm };

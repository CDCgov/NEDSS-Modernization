import GeneralSearchFields from './GeneralSearchFields';
import CriteriaSearchFields from './CriteriaSearchFields';
import { Accordion } from 'components/Accordion/Accordion';

const InvestigationSearchForm = () => {
    return (
        <div>
            <Accordion title="General search" open>
                <GeneralSearchFields />
            </Accordion>
            <Accordion title="Investigation criteria">
                <CriteriaSearchFields />
            </Accordion>
        </div>
    );
};

export { InvestigationSearchForm };

import { Accordion } from 'components/Accordion';
import { CriteriaFields } from './CriteriaFields';
import { GeneralFields } from './GeneralFields';

const LaboratoryReportSearchCriteria = () => (
    <>
        <Accordion title="General search" open>
            <GeneralFields />
        </Accordion>
        <Accordion title="Lab report criteria">
            <CriteriaFields />
        </Accordion>
    </>
);

export { LaboratoryReportSearchCriteria };

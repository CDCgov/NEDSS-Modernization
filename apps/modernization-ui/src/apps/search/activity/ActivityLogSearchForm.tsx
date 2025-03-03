import GeneralSearchFields from './GeneralSearchFields';
import { Accordion } from 'components/Accordion/Accordion';
import { Sizing } from '../../../design-system/field';
import ElrCriteriaSearchFields from './result/elr-result/CriteriaSearchFields';

type ActivityCriteriaProps = {
    sizing?: Sizing;
};

const ActivityLogSearchForm = ({ sizing }: ActivityCriteriaProps) => {
    return (
        <>
            <Accordion title="Activity Search" open>
                <GeneralSearchFields sizing={sizing} />
            </Accordion>
            <Accordion title="Additional Filters" open>
                <ElrCriteriaSearchFields sizing={sizing} />
            </Accordion>
        </>
    );
};

export { ActivityLogSearchForm };

import GeneralSearchFields from './GeneralSearchFields';
import styles from './InvestigationSearchForm.module.scss';
import CriteriaSearchFields from './CriteriaSearchFields';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { Accordion } from '@trussworks/react-uswds';

const InvestigationSearchForm = () => {
    const items: AccordionItemProps[] = [
        {
            title: 'General search',
            content: <GeneralSearchFields />,
            expanded: true,
            id: 'lab-general-section',
            headingLevel: 'h3',
            className: 'accordian-item'
        },
        {
            title: 'Investigation criteria',
            content: <CriteriaSearchFields />,
            expanded: false,
            id: 'lab-criteria-section',
            headingLevel: 'h3',
            className: 'accordian-item'
        }
    ];

    return (
        <div className={styles.investigationSearchContainer}>
            <Accordion items={items} multiselectable={true} />
        </div>
    );
};

export default InvestigationSearchForm;

import { Accordion } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { Control, FieldValues } from 'react-hook-form';
import { GeneralSearch } from './GeneralSearch';
import { SearchCriteria } from './SearchCriteria';

type InvestigationAccordionProps = {
    formControl: Control<FieldValues, any>;
};
export const InvestigationAccordion = ({ formControl }: InvestigationAccordionProps) => {
    const investigationSearchFilteredItem: AccordionItemProps[] = [
        {
            title: 'General search',
            content: <GeneralSearch control={formControl} />,
            expanded: true,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Investigation criteria',
            content: <SearchCriteria control={formControl} />,
            expanded: false,
            id: '3',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];
    return <Accordion items={investigationSearchFilteredItem} multiselectable={true} />;
};

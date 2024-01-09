import { Accordion } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { InvestigationFilter } from 'generated/graphql/schema';
import { UseFormReturn } from 'react-hook-form';
import { InvestigationGeneralFields } from './InvestigationGeneralFields';
import { InvestigationCriteria } from './InvestigationCriteria';

type InvestigationAccordionProps = {
    form: UseFormReturn<InvestigationFilter>;
};
export const InvestigationAccordion = ({ form }: InvestigationAccordionProps) => {
    const items: AccordionItemProps[] = [
        {
            title: 'General search',
            content: <InvestigationGeneralFields form={form} />,
            expanded: true,
            id: 'investigation-general-section',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Investigation criteria',
            content: <InvestigationCriteria form={form} />,
            expanded: false,
            id: 'investigation-criteria-section',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];
    return <Accordion items={items} multiselectable={true} />;
};

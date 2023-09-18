import { Accordion } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { LabReportFilter } from 'generated/graphql/schema';
import { UseFormReturn } from 'react-hook-form';
import { LabReportGeneralFields } from './LabReportGeneralFields';
import { LabReportCriteria } from './LabReportCriteria';

type LabReportAccordionProps = {
    form: UseFormReturn<LabReportFilter>;
};
export const LabReportAccordion = ({ form }: LabReportAccordionProps) => {
    const items: AccordionItemProps[] = [
        {
            title: 'General search',
            content: <LabReportGeneralFields form={form} />,
            expanded: true,
            id: 'lab-general-section',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Lab report criteria',
            content: <LabReportCriteria form={form} />,
            expanded: false,
            id: 'lab-criteria-section',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];
    return <Accordion items={items} multiselectable={true} />;
};

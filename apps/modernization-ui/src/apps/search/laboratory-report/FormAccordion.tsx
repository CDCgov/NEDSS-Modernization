import { Accordion } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { UseFormReturn } from 'react-hook-form';
// import { GeneralFields } from './GeneralFields';
import { CriteriaFields } from './CriteriaFields';
import { LabReportFilterEntry } from './labReportFormTypes';
import { GeneralFields } from './GeneralFields';

type LabReportAccordionProps = {
    form: UseFormReturn<LabReportFilterEntry>;
};
export const FormAccordion = ({ form }: LabReportAccordionProps) => {
    const items: AccordionItemProps[] = [
        {
            title: 'General search',
            content: <GeneralFields form={form} />,
            expanded: true,
            id: 'lab-general-section',
            headingLevel: 'h3',
            className: 'accordian-item'
        },
        {
            title: 'Lab report criteria',
            content: <CriteriaFields form={form} />,
            expanded: false,
            id: 'lab-criteria-section',
            headingLevel: 'h3',
            className: 'accordian-item'
        }
    ];
    return <Accordion items={items} multiselectable={true} />;
};

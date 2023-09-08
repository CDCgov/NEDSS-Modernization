import { Accordion } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { LabReportFilter } from 'generated/graphql/schema';
import { UseFormReturn } from 'react-hook-form';
import { LabReportGeneralFields } from './LabReportGeneralFields';

type LabReportAccordionProps = {
    form: UseFormReturn<LabReportFilter>;
};
export const LabReportAccordion = ({ form }: LabReportAccordionProps) => {
    const items: AccordionItemProps[] = [
        {
            title: 'General search',
            content: <LabReportGeneralFields form={form} />, // <LabGeneralSearch control={control} filter={labReportFilter} />
            expanded: true,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Lab report criteria',
            content: (
                <></>
                // <LabSearchCriteria
                //     resultsTestOptions={resultData}
                //     codedResults={codedResults}
                //     codedResultsChange={handleCodedResultChange}
                //     resultChanges={handleResultChange}
                //     control={control}
                //     filter={labReportFilter}
                // />
            ),
            expanded: false,
            id: '3',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];
    return <Accordion items={items} multiselectable={true} />;
};

import { Accordion } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { SelectInput } from '../../../../../components/FormInputs/SelectInput';

type EventTypeAccordionProps = {
    setSearchType: Function;
    searchType: SearchType;
};
export type SearchType = 'investigation' | 'labReport' | undefined;
export const EventTypeAccordion = ({ searchType, setSearchType }: EventTypeAccordionProps) => {
    const eventTypeAccordion: AccordionItemProps[] = [
        {
            title: 'Event type',
            content: (
                <SelectInput
                    onChange={(e) => setSearchType(e.target.value as SearchType)}
                    label="Event type"
                    placeholder="- Select -"
                    value={searchType}
                    options={[
                        { name: 'Investigation', value: 'investigation' },
                        { name: 'Laboratory report', value: 'labReport' }
                    ]}
                />
            ),
            expanded: true,
            id: 'event-type-section',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];
    return <Accordion items={eventTypeAccordion} multiselectable={true} />;
};

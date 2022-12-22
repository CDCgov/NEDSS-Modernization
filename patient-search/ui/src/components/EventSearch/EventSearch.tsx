import { Accordion, Button, Form, Grid } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { EventFilter, EventType } from '../../generated/graphql/schema';
import { EventTypes } from './EventType';
import { GeneralSearch } from './generalSearch';

type EventSearchProps = {
    onSearch: (filter: EventFilter) => void;
};

export const EventSearch = ({ onSearch }: EventSearchProps) => {
    const [eventSearchType, setEventSearchType] = useState<any>('');

    const methods = useForm();

    const { handleSubmit, control, reset } = methods;

    const eventSearchItems: AccordionItemProps[] = [
        {
            title: 'Event Type',
            content: (
                <EventTypes
                    onChangeMethod={(e) => {
                        setEventSearchType(e);
                    }}
                    control={control}
                    name="eventType"
                />
            ),
            expanded: true,
            id: '1',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];

    const eventSearchFilteredItem: AccordionItemProps[] = [
        {
            title: 'General Search',
            content: <GeneralSearch searchType={eventSearchType} control={control} />,
            expanded: true,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];

    const onSubmit: any = (body: any) => {
        const filterData: EventFilter = {
            eventType: eventSearchType
        };
        if (eventSearchType === EventType.Investigation) {
            filterData.eventType = EventType.Investigation;
            filterData.investigationFilter = {
                conditions: body.condition ? [body.condition] : undefined,
                jurisdictions: body.jurisdiction ? [body.jurisdiction] : undefined,
                pregnancyStatus: body.pregnancyTest ? body.pregnancyTest : undefined,
                programAreas: body.programArea ? [body.programArea] : undefined
            };
        } else if (eventSearchType === EventType.LaboratoryReport) {
            filterData.eventType = EventType.LaboratoryReport;
            filterData.laboratoryReportFilter = {
                programAreas: body.programArea ? [body.programArea] : undefined,
                jurisdictions: body.jurisdiction ? [body.jurisdiction] : undefined,
                pregnancyStatus: body.pregnancyTest ? body.pregnancyTest : undefined
            };
        } else {
            // no search type selected
            return;
        }
        onSearch(filterData);
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)} className="width-full maxw-full">
            <Accordion items={eventSearchItems} multiselectable={true} />
            {eventSearchType && eventSearchType !== '- Select -' && (
                <Accordion items={eventSearchFilteredItem} multiselectable={true} />
            )}
            <Grid col={12} className="margin-top-5 padding-x-3">
                <Button className="width-full" type={'submit'}>
                    Search
                </Button>
            </Grid>
            <Grid col={12} className="padding-x-3">
                <Button className="width-full" type={'button'} onClick={() => reset({})} outline>
                    Clear
                </Button>
            </Grid>
        </Form>
    );
};

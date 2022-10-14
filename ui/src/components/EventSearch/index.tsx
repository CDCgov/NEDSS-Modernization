import { Accordion, Button, Form, Grid } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { EventFilter, EventType, useFindPatientsByEventLazyQuery } from '../../generated/graphql/schema';
import { EventTypes } from './EventType';
import { GeneralSearch } from './generalSearch';

export const EventSearch = () => {
    // const [getFilteredData] = useFindPatientsByFilterLazyQuery();
    const [getEventSearch] = useFindPatientsByEventLazyQuery();

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
        console.log('body:', body);
        const filterData: EventFilter = {
            eventType: eventSearchType
        };
        if (eventSearchType === EventType.Investigation) {
            filterData.investigationFilter = {
                // TODO inject actual values
                conditions: ['Bacterial Vaginosis']
            };
        } else if (eventSearchType === EventType.LaboratoryReport) {
            filterData.laboratoryReportFilter = {
                // TODO inject actual values
                resultedTest: 'Acid-Fast Stain'
            };
        } else {
            // no search type selected
            return;
        }

        // getFilteredData({
        //     variables: {
        //         filter: rowData
        //     }
        // });
        getEventSearch({
            variables: {
                filter: filterData,
                page: {
                    pageNumber: 0,
                    pageSize: 50
                }
            }
        }).then((re) => {
            console.log('re:', re);
        });
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
                <Button
                    className="width-full"
                    type={'button'}
                    onClick={() =>
                        reset({
                            lastName: '',
                            firstName: '',
                            city: '',
                            state: '',
                            zip: '',
                            patientId: ''
                        })
                    }
                    outline>
                    Clear
                </Button>
            </Grid>
        </Form>
    );
};

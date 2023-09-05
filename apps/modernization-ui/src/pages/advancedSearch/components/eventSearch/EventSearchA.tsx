import { Button, Form, Grid } from '@trussworks/react-uswds';
import { InvestigationFilter } from 'generated/graphql/schema';
import { useEffect, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { EventTypeAccordion, SearchType } from './EventTypeAccordion';
import { InvestigationAccordion } from './InvestigationAccordion';

type EventSearchProps = {
    investigationFilter?: InvestigationFilter;
};
export const EventSearchA = ({ investigationFilter }: EventSearchProps) => {
    const investigationForm = useForm<InvestigationFilter, any>({ defaultValues: investigationFilter });
    const [searchType, setSearchType] = useState<SearchType>();

    // on page load, if an event search was performed set the selected event type
    useEffect(() => {
        if (investigationFilter) {
            setSearchType('investigation');
            investigationForm.reset();
        }
    }, [investigationFilter]);

    const handleClearAll = () => {
        investigationForm.reset();
        setSearchType(undefined);
    };

    const handleSubmit = () => {};

    return (
        <>
            <div style={{ height: `calc(100vh - 405px)`, overflowY: 'auto' }}>
                <EventTypeAccordion searchType={searchType} setSearchType={setSearchType} />
                {searchType === 'investigation' ? (
                    <>
                        <FormProvider {...investigationForm}>
                            <Form
                                onSubmit={investigationForm.handleSubmit(handleSubmit)}
                                className="width-full maxw-full">
                                <InvestigationAccordion formControl={investigationForm.control} />
                            </Form>
                        </FormProvider>
                    </>
                ) : null}
                {searchType === 'labReport' ? <></> : null}
                <Grid row className="bottom-search">
                    <Grid col={12} className="padding-x-2">
                        <Button className="width-full clear-btn" type={'submit'}>
                            Search
                        </Button>
                    </Grid>
                    <Grid col={12} className="padding-x-2">
                        <Button className="width-full clear-btn" type={'button'} onClick={handleClearAll} outline>
                            Clear all
                        </Button>
                    </Grid>
                </Grid>
            </div>
        </>
    );
};

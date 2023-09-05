import { Button, Form, Grid } from '@trussworks/react-uswds';
import { InvestigationFilter, LabReportFilter } from 'generated/graphql/schema';
import { useEffect, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { EventTypeAccordion, SearchType } from './EventTypeAccordion';
import { InvestigationAccordion } from './InvestigationAccordion';
import { SEARCH_TYPE } from 'pages/advancedSearch/AdvancedSearch';

type EventSearchProps = {
    onSearch: (filter: InvestigationFilter | LabReportFilter, type: SEARCH_TYPE) => void;
    investigationFilter?: InvestigationFilter;
};
export const EventSearchA = ({ investigationFilter, onSearch }: EventSearchProps) => {
    const investigationForm = useForm<InvestigationFilter>({ defaultValues: investigationFilter });
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

    const handleSubmit = (investigationFilter: InvestigationFilter) => {
        console.log('onSubmit', investigationFilter);
        console.log('onSubmit', investigationForm);
        onSearch(investigationFilter, SEARCH_TYPE.INVESTIGATION);
    };

    return (
        <>
            <div style={{ height: `calc(100vh - 405px)`, overflowY: 'auto' }}>
                <EventTypeAccordion searchType={searchType} setSearchType={setSearchType} />
                <FormProvider {...investigationForm}>
                    <Form
                        onSubmit={
                            searchType === 'investigation'
                                ? investigationForm.handleSubmit(handleSubmit)
                                : investigationForm.handleSubmit(handleSubmit)
                        }
                        className="width-full maxw-full">
                        {searchType === 'investigation' ? (
                            <InvestigationAccordion formControl={investigationForm.control} />
                        ) : null}
                        {searchType === 'labReport' ? <></> : null}
                        <Grid row className="bottom-search">
                            <Grid col={12} className="padding-x-2">
                                <Button className="width-full clear-btn" type={'submit'}>
                                    Search
                                </Button>
                            </Grid>
                            <Grid col={12} className="padding-x-2">
                                <Button
                                    className="width-full clear-btn"
                                    type={'button'}
                                    onClick={handleClearAll}
                                    outline>
                                    Clear all
                                </Button>
                            </Grid>
                        </Grid>
                    </Form>
                </FormProvider>
            </div>
        </>
    );
};

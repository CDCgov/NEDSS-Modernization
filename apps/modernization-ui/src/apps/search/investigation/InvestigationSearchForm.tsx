import { EventId, InvestigationStatus, ProviderFacilitySearch } from 'generated/graphql/schema';
import React from 'react';
import { useForm } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { Form } from '@trussworks/react-uswds';
import GeneralSearchFields from './GeneralSearchFields';

const InvestigationSearchForm = () => {
    const defaultValues: InvestigationFilterEntry = {
        createdBy: { name: '', value: '' },
        lastUpdatedBy: { name: '', value: '' },
        investigatorId: { name: '', value: '' },
        pregnancyStatus: { name: '', value: '' },
        eventId: {} as EventId,
        investigationStatus: {} as InvestigationStatus,
        patientId: null,
        providerFacilitySearch: {} as ProviderFacilitySearch,
        jurisdictions: [{ name: '', value: '' }],
        conditions: [{ name: '', value: '' }],
        caseStatuses: [{ name: '', value: '' }],
        notificationStatues: [{ name: '', value: '' }],
        outbreakNames: [{ name: '', value: '' }],
        processingStatus: { name: '', value: '' },
        programAreas: [{ name: '', value: '' }]
    };

    const form = useForm<InvestigationFilterEntry>({ defaultValues });

    return (
        <div>
            <Form onSubmit={() => console.log('test')}>
                <GeneralSearchFields form={form} />
            </Form>
        </div>
    );
};

export default InvestigationSearchForm;

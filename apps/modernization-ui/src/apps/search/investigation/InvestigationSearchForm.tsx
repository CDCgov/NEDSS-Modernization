import React from 'react';
import { Form } from '@trussworks/react-uswds';
import GeneralSearchFields from './GeneralSearchFields';
import { UseFormReturn } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';

type Props = {
    form: UseFormReturn<InvestigationFilterEntry>;
};

const InvestigationSearchForm = ({ form }: Props) => {
    return (
        <div>
            <Form onSubmit={() => console.log('test')}>
                <GeneralSearchFields form={form} />
            </Form>
        </div>
    );
};

export default InvestigationSearchForm;

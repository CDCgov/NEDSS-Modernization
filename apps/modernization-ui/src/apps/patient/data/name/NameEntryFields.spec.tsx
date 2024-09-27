import { NameEntry } from '../entry';
import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';

import { NameEntryFields } from './NameEntryFields';

const mockPatientNameCodedValues = {
    types: [{ name: 'Adopted name', value: 'AN' }],
    prefixes: [{ name: 'Miss', value: 'MS' }],
    suffixes: [{ name: 'Sr.', value: 'SR' }],
    degrees: [{ name: 'BA', value: 'BA' }]
};

jest.mock('apps/patient/profile/names/usePatientNameCodedValues', () => ({
    usePatientNameCodedValues: () => mockPatientNameCodedValues
}));

const Fixture = () => {
    const form = useForm<NameEntry>({
        mode: 'onBlur',
        defaultValues: {
            asOf: undefined,
            type: undefined,
            prefix: undefined,
            first: '',
            middle: '',
            last: '',
            secondMiddle: '',
            secondLast: '',
            suffix: undefined,
            degree: undefined
        }
    });

    return (
        <FormProvider {...form}>
            <NameEntryFields />
        </FormProvider>
    );
};

describe('Name entry fields', () => {
    it('should render the proper labels', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Name as of')).toBeInTheDocument();
        expect(getByLabelText('Type')).toBeInTheDocument();
        expect(getByLabelText('Prefix')).toBeInTheDocument();
        expect(getByLabelText('First')).toBeInTheDocument();
        expect(getByLabelText('Middle')).toBeInTheDocument();
        expect(getByLabelText('Last')).toBeInTheDocument();
        expect(getByLabelText('Second middle')).toBeInTheDocument();
        expect(getByLabelText('Second last')).toBeInTheDocument();
        expect(getByLabelText('Suffix')).toBeInTheDocument();
        expect(getByLabelText('Degree')).toBeInTheDocument();
    });
});

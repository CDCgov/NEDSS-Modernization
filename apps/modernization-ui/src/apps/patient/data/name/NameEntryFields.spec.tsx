import { renderHook } from '@testing-library/react-hooks';
import { NameEntry } from '../entry';
import { FormProvider, useForm } from 'react-hook-form';
import { act, fireEvent, render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
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

    it('should require as of', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const asOf = getByLabelText('Name as of');
        act(() => {
            userEvent.click(asOf);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('As of date is required.')).toBeInTheDocument();
        });
    });

    it('should require type', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const type = getByLabelText('Type');
        act(() => {
            userEvent.click(type);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('Type is required.')).toBeInTheDocument();
        });
    });

    it('should be valid with as of, race', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('Name as of');
        const type = getByLabelText('Type');
        act(() => {
            userEvent.paste(asOf, '01/20/2020');
            userEvent.tab();
            userEvent.selectOptions(type, 'AN');
            userEvent.tab();
        });

        await waitFor(() => {
            expect(queryByText('As of date is required.')).not.toBeInTheDocument();
            expect(queryByText('Type is required.')).not.toBeInTheDocument();
        });
    });
});

import { render, waitFor, act } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FormProvider, useForm } from 'react-hook-form';
import { EthnicityCodedValues } from './useEthnicityCodedValues';
import { EthnicityEntry } from './entry';
import { EthnicityEntryFields } from './EthnicityEntryFields';

const mockEthnicityValues: EthnicityCodedValues = {
    ethnicGroups: [
        { name: 'Hispanic or Latino', value: '2135-2' },
        { name: 'Unknown', value: 'UNK' }
    ],
    ethnicityUnknownReasons: [{ name: 'Not asked', value: '6' }],
    detailedEthnicities: [{ name: 'Central American', value: '2155-0' }]
};

jest.mock('./useEthnicityCodedValues', () => ({
    useEthnicityCodedValues: () => mockEthnicityValues
}));

const Fixture = () => {
    const form = useForm<EthnicityEntry>({ mode: 'onBlur' });
    return (
        <FormProvider {...form}>
            <EthnicityEntryFields />
        </FormProvider>
    );
};

describe('when entering patient ethnicity demographics', () => {
    it('should render the proper labels', () => {
        const { getByLabelText, queryByLabelText, getByText } = render(<Fixture />);

        expect(getByLabelText('Ethnicity information as of')).toBeInTheDocument();
        expect(getByText('Ethnicity information as of')).toHaveClass('required');
        expect(getByLabelText('Ethnicity')).toBeInTheDocument();
        expect(getByText('Ethnicity')).not.toHaveClass('required');
        expect(queryByLabelText('Spanish origin')).not.toBeInTheDocument();
        expect(queryByLabelText('Reason unknown')).not.toBeInTheDocument();
    });

    it('should require as of date', async () => {
        const { getByLabelText, findByText } = render(<Fixture />);
        const asOf = getByLabelText('Ethnicity information as of');
        act(() => {
            userEvent.click(asOf);
            userEvent.tab();
        });

        expect(await findByText('The Ethnicity information as of is required.')).toBeInTheDocument();
    });

    it('should display spanish origin when hispanic or latino selected', async () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        act(() => {
            userEvent.selectOptions(getByLabelText('Ethnicity'), '2135-2');
        });

        await waitFor(() => {
            expect(getByLabelText('Spanish origin')).toBeInTheDocument();
        });
        expect(queryByLabelText('Reason unknown')).not.toBeInTheDocument();
    });

    it('should display unknown reason when unknown selected', async () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        act(() => {
            userEvent.selectOptions(getByLabelText('Ethnicity'), 'UNK');
        });

        await waitFor(() => {
            expect(getByLabelText('Reason unknown')).toBeInTheDocument();
        });
        expect(queryByLabelText('Spanish origin')).not.toBeInTheDocument();
    });
});

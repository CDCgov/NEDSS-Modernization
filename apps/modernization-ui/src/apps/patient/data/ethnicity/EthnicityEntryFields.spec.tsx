import { vi } from 'vitest';
import { render } from '@testing-library/react';
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

vi.mock('./useEthnicityCodedValues', () => ({
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
        const { getByLabelText, getByText } = render(<Fixture />);
        const asOf = getByLabelText('Ethnicity information as of');

        const user = userEvent.setup();

        await user.click(asOf).then(() => user.tab());

        expect(getByText('The Ethnicity information as of is required.')).toBeInTheDocument();
    });

    it('should display spanish origin when hispanic or latino selected', async () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        const user = userEvent.setup();
        await user.selectOptions(getByLabelText('Ethnicity'), '2135-2');

        expect(getByLabelText('Spanish origin')).toBeInTheDocument();

        expect(queryByLabelText('Reason unknown')).not.toBeInTheDocument();
    });

    it('should display unknown reason when unknown selected', async () => {
        const { getByLabelText, queryByLabelText } = render(<Fixture />);

        const user = userEvent.setup();
        await user.selectOptions(getByLabelText('Ethnicity'), 'UNK');

        expect(getByLabelText('Reason unknown')).toBeInTheDocument();

        expect(queryByLabelText('Spanish origin')).not.toBeInTheDocument();
    });
    it('should have accessibility description for the as of date field', () => {
        const { getByLabelText } = render(<Fixture />);
        const dateInput = getByLabelText('Ethnicity information as of');
        expect(dateInput).toHaveAttribute(
            'aria-description',
            "This field defaults to today's date and can be changed if needed."
        );
    });
});

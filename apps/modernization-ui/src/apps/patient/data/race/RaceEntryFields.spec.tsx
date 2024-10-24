import { FormProvider, useForm } from 'react-hook-form';
import { act, render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { CodedValue } from 'coded/CodedValue';
import { RaceEntry } from './entry';
import { categoryRequiredValidator } from './categoryRequiredValidator';
import { RaceEntryFields, RaceEntryFieldsProps } from './RaceEntryFields';

const mockDetailedOptions: CodedValue[] = [
    { value: '2', name: 'detailed race1' },
    { value: '3', name: 'detailed race2' }
];

jest.mock('coded/race/useDetailedRaceCodedValues', () => ({
    useDetailedRaceCodedValues: () => mockDetailedOptions
}));

type Props = Partial<RaceEntryFieldsProps>;

const Fixture = ({
    categories = [{ value: '1', name: 'race name' }],
    categoryValidator = jest.fn().mockResolvedValue(true)
}: Props) => {
    const form = useForm<RaceEntry>({
        mode: 'onBlur',
        defaultValues: {
            id: 19,
            asOf: undefined,
            race: undefined,
            detailed: undefined
        }
    });

    return (
        <FormProvider {...form}>
            <RaceEntryFields categories={categories} categoryValidator={categoryValidator} />
        </FormProvider>
    );
};

describe('Race entry fields', () => {
    it('should render the proper labels', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Race as of')).toBeInTheDocument();
        expect(getByLabelText('Race')).toBeInTheDocument();
    });

    it('detailed race should render once race is chosen', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const race = getByLabelText('Race');

        act(() => {
            userEvent.type(race, '1');
        });

        await waitFor(() => {
            expect(getByText('Detailed race')).toBeInTheDocument();
        });
    });

    it('should require as of', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const asOf = getByLabelText('Race as of');
        act(() => {
            userEvent.click(asOf);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('As of date is required.')).toBeInTheDocument();
        });
    });

    it('should require race category', async () => {
        const { getByLabelText, getByText } = render(<Fixture categoryValidator={categoryRequiredValidator} />);

        const raceInput = getByLabelText('Race');

        act(() => {
            userEvent.click(raceInput);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('Race is required.')).toBeInTheDocument();
        });
    });

    it('should require race category to pass validation', async () => {
        const validator = jest.fn();
        validator.mockResolvedValue('category not valid');

        const { getByLabelText, getByText } = render(<Fixture categoryValidator={validator} />);

        const category = getByLabelText('Race');
        act(() => {
            userEvent.selectOptions(category, '1');
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('category not valid')).toBeInTheDocument();
        });

        expect(validator).toBeCalledWith(19, expect.objectContaining({ value: '1' }));
    });

    it('should be valid with as of, race', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('Race as of');
        const race = getByLabelText('Race');
        act(() => {
            userEvent.paste(asOf, '01/20/2020');
            userEvent.tab();
            userEvent.selectOptions(race, '1');
            userEvent.tab();
        });

        await waitFor(() => {
            expect(queryByText('As of date is required')).not.toBeInTheDocument();
            expect(queryByText('Race is required')).not.toBeInTheDocument();
        });
    });
});

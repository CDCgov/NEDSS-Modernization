import { renderHook } from '@testing-library/react-hooks';
import { CodedValue } from 'coded/CodedValue';
import { RaceEntry } from '../entry';
import { FormProvider, useForm } from 'react-hook-form';
import { act, fireEvent, render, waitFor } from '@testing-library/react';
import { RaceEntryFields } from './RaceEntryFields';
import userEvent from '@testing-library/user-event';

const mockRaceCodedValues: CodedValue[] = [{ value: '1', name: 'race name' }];

jest.mock('coded/race/useRaceCodedValues', () => ({
    useRaceCodedValues: () => mockRaceCodedValues
}));
const mockDetailedOptions: CodedValue[] = [
    { value: '2', name: 'detailed race1' },
    { value: '3', name: 'detailed race2' }
];

jest.mock('coded/race/useDetailedRaceCodedValues', () => ({
    useDetailedRaceCodedValues: () => mockDetailedOptions
}));

const Fixture = () => {
    const form = useForm<RaceEntry>({
        mode: 'onBlur',
        defaultValues: {
            asOf: undefined,
            race: undefined,
            detailed: undefined
        }
    });

    return (
        <FormProvider {...form}>
            <RaceEntryFields />
        </FormProvider>
    );
};

describe('Race entry fields', () => {
    it('should render the proper labels', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('As of')).toBeInTheDocument();
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

        const asOf = getByLabelText('As of');
        act(() => {
            userEvent.click(asOf);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('As of date is required.')).toBeInTheDocument();
        });
    });

    it('should require race', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const raceInput = getByLabelText('Race');
        act(() => {
            userEvent.click(raceInput);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('Race is required.')).toBeInTheDocument();
        });
    });

    it('should be valid with as of, race', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('As of');
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
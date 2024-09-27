import { CodedValue } from 'coded/CodedValue';
import { RaceEntry } from '../entry';
import { FormProvider, useForm } from 'react-hook-form';
import { act, render, waitFor } from '@testing-library/react';
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
});

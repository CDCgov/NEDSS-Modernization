import { render, waitFor } from '@testing-library/react';
import { CodedValue } from 'coded';
import { internalizeDate } from 'date';
import { RaceMultiEntry } from './RaceMultiEntry';
import { useMultiValueEntryState } from 'design-system/entry/multi-value/useMultiValueEntryState';
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

const onChange = jest.fn();
const isDirty = jest.fn();

describe('RaceMultiEntry', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole, getByLabelText } = render(<RaceMultiEntry onChange={onChange} isDirty={isDirty} />);

        const race = getByLabelText('Race');
        const buttons = getAllByRole('button');

        await waitFor(async () => {
            userEvent.selectOptions(race, '1');
            // warning says no effect, but it lies
            userEvent.click(buttons[1]);
        });

        await waitFor(async () => {
            const headers = getAllByRole('columnheader');

            expect(headers[0]).toHaveTextContent('As of');
            expect(headers[1]).toHaveTextContent('Race');
            expect(headers[2]).toHaveTextContent('Detailed race');
        });
    });

    it('should display proper defaults', async () => {
        const { getByLabelText, getAllByRole, getAllByText } = render(
            <RaceMultiEntry onChange={onChange} isDirty={isDirty} />
        );

        const dateInput = getByLabelText('As of');
        expect(dateInput).toHaveValue(internalizeDate(new Date()));

        const race = getByLabelText('Race');
        expect(race).toHaveValue('');

        const detailedRace = getAllByRole('combobox')[0];
        expect(detailedRace).toHaveValue('');
    });
});

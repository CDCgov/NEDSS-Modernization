import { render } from '@testing-library/react';
import { CodedValue } from 'coded';
import { internalizeDate } from 'date';
import { RaceRepeatingBlock } from './RaceRepeatingBlock';
import { Selectable } from 'options';

const mockRaceCodedValues: Selectable[] = [{ value: '1', name: 'race name' }];

jest.mock('options/concepts', () => ({
    useConceptOptions: () => ({ options: mockRaceCodedValues })
}));

const mockDetailedOptions: CodedValue[] = [
    { value: '2', name: 'detailed race1' },
    { value: '3', name: 'detailed race2' }
];

jest.mock('coded/race/useDetailedRaceCodedValues', () => ({
    useDetailedRaceCodedValues: () => mockDetailedOptions
}));

const mockEntry = {
    state: {
        data: [
            {
                asOf: internalizeDate(new Date()),
                race: '1'
            }
        ]
    }
};

jest.mock('design-system/entry/multi-value/useMultiValueEntryState', () => ({
    useMultiValueEntryState: () => mockEntry
}));

const onChange = jest.fn();
const isDirty = jest.fn();

describe('RaceRepeatingBlock', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(<RaceRepeatingBlock id="testing" onChange={onChange} isDirty={isDirty} />);

        const headers = getAllByRole('columnheader');
        expect(headers[0]).toHaveTextContent('As of');
        expect(headers[1]).toHaveTextContent('Race');
        expect(headers[2]).toHaveTextContent('Detailed race');
    });

    it('should display proper defaults', async () => {
        const { getByLabelText, getAllByRole } = render(
            <RaceRepeatingBlock id="testing" onChange={onChange} isDirty={isDirty} />
        );

        const dateInput = getByLabelText('Race as of');
        expect(dateInput).toHaveValue(internalizeDate(new Date()));

        const race = getByLabelText('Race');
        expect(race).toHaveValue('');

        const detailedRace = getAllByRole('combobox')[0];
        expect(detailedRace).toHaveValue('');
    });
});

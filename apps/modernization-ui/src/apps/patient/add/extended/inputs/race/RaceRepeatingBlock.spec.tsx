import { act, render, waitFor } from '@testing-library/react';
import { CodedValue } from 'coded';
import { internalizeDate } from 'date';
import { RaceRepeatingBlock } from './RaceRepeatingBlock';
import { Selectable } from 'options';
import userEvent from '@testing-library/user-event';

const mockRaceCodedValues: Selectable[] = [
    { value: '1', name: 'race one name' },
    { value: '2', name: 'race two name' }
];

jest.mock('options/race/useRaceCategoryOptions', () => ({
    useRaceCategoryOptions: () => ({ categories: mockRaceCodedValues })
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

describe('RaceRepeatingBlock', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(
            <RaceRepeatingBlock
                id="testing"
                values={[
                    {
                        id: 3,
                        asOf: '06/05/2024',
                        race: { value: '1', name: 'race one name' },
                        detailed: []
                    }
                ]}
                onChange={onChange}
                isDirty={isDirty}
            />
        );

        const headers = getAllByRole('columnheader');
        expect(headers[0]).toHaveTextContent('As of');
        expect(headers[1]).toHaveTextContent('Race');
        expect(headers[2]).toHaveTextContent('Detailed race');
    });

    it('should display proper defaults', async () => {
        const { getByLabelText, getByRole } = render(
            <RaceRepeatingBlock id="testing" onChange={onChange} isDirty={isDirty} />
        );

        const dateInput = getByLabelText('Race as of');
        expect(dateInput).toHaveValue(internalizeDate(new Date()));

        const race = getByLabelText('Race');
        expect(race).toHaveValue('');

        const detailedRace = getByRole('combobox');
        expect(detailedRace).toHaveValue('');
    });

    it('should mark repeating block as dirty when input changes', () => {
        const { getByLabelText } = render(<RaceRepeatingBlock id="testing" onChange={onChange} isDirty={isDirty} />);

        const category = getByLabelText('Race');

        act(() => {
            userEvent.selectOptions(category, '1');
            userEvent.tab();
        });

        expect(isDirty).toHaveBeenCalledWith(true);
    });

    it('should not allow adding the same race more than once', async () => {
        const { getByLabelText, getByRole, getByText } = render(
            <RaceRepeatingBlock
                id="testing"
                values={[
                    {
                        id: 3,
                        asOf: '06/05/2024',
                        race: { value: '1', name: 'race one name' },
                        detailed: []
                    }
                ]}
                onChange={onChange}
                isDirty={isDirty}
            />
        );

        const category = getByLabelText('Race');

        const add = getByRole('button', { name: 'Add race' });

        act(() => {
            userEvent.selectOptions(category, '1');
            userEvent.tab();

            userEvent.click(add);
        });

        await waitFor(() => {
            expect(getByRole('listitem')).toHaveTextContent(
                /Race race one name has already been added to the repeating block/
            );
        });
    });
});
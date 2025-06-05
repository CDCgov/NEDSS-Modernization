import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { internalizeDate } from 'date';
import { RaceRepeatingBlock } from './RaceRepeatingBlock';
import { Selectable } from 'options';

const mockRaceCategories: Selectable[] = [
    { value: '1', name: 'race one name' },
    { value: '2', name: 'race two name' }
];

const mockDetailedRaces: Selectable[] = [
    { value: '2', name: 'detailed race1' },
    { value: '3', name: 'detailed race2' }
];

jest.mock('options/race', () => ({
    useRaceCategoryOptions: () => mockRaceCategories,
    useDetailedRaceOptions: () => mockDetailedRaces
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
        const { getByLabelText } = render(<RaceRepeatingBlock id="testing" onChange={onChange} isDirty={isDirty} />);

        const dateInput = getByLabelText('Race as of');
        expect(dateInput).toHaveValue(internalizeDate(new Date()));

        const race = screen.getByRole('combobox', { name: 'Race' });
        expect(race).toHaveValue('');

        const detailedRace = getByLabelText('Detailed race');
        expect(detailedRace).toHaveValue('');
    });

    it('should mark repeating block as dirty when input changes', async () => {
        render(<RaceRepeatingBlock id="testing" onChange={onChange} isDirty={isDirty} />);

        const category = screen.getByRole('combobox', { name: 'Race' });

        const user = userEvent.setup();
        await user.selectOptions(category, '1');

        expect(isDirty).toHaveBeenCalledWith(true);
    });

    it('should not allow adding the same race more than once', async () => {
        const { getByRole, getAllByRole } = render(
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

        const category = getByRole('combobox', { name: 'Race' });

        const add = getByRole('button', { name: 'Add race' });

        const user = userEvent.setup();
        await user.selectOptions(category, '1').then(() => user.click(add));
        const errorAlert = getAllByRole('alert')[0];
        expect(errorAlert).toHaveTextContent(/Race race one name has already been added to the repeating block/);

        expect(getByRole('listitem')).toHaveTextContent(
            /Race race one name has already been added to the repeating block/
        );
    });
});

import { render } from '@testing-library/react';
import { RaceEntryView } from './RaceEntryView';
import { RaceEntry } from 'apps/patient/data/race';
import { asSelectable } from 'options';

const entry: RaceEntry = {
    id: 331,
    asOf: '12/25/2020',
    race: asSelectable('1', 'test'),
    detailed: [asSelectable('2', 'test 2'), asSelectable('3', 'test 3')]
};

describe('RaceEntryView', () => {
    it('should render label with value', () => {
        const { getByText } = render(<RaceEntryView entry={entry} />);
        const asOf = getByText('As of');
        expect(asOf.parentElement).toContainElement(getByText('12/25/2020'));

        const race = getByText('Race');
        expect(race.parentElement).toContainElement(getByText('test'));

        const detailedRace = getByText('Detailed race');
        expect(detailedRace.parentElement).toContainElement(getByText('test 2, test 3'));
    });

    it('should render label with value for empty detailed race', () => {
        const { getByText } = render(<RaceEntryView entry={{ ...entry, detailed: [] }} />);
        const asOf = getByText('As of');
        expect(asOf.parentElement).toContainElement(getByText('12/25/2020'));

        const race = getByText('Race');
        expect(race.parentElement).toContainElement(getByText('test'));

        const detailedRace = getByText('Detailed race');
        expect(detailedRace.parentElement).toHaveTextContent(' ');
    });
});

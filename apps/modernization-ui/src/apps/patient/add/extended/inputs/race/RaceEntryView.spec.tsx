import { render } from '@testing-library/react';
import { RaceEntry } from 'apps/patient/profile/race/RaceEntry';
import { CodedValue } from 'coded';
import { RaceEntryView } from './RaceEntryView';

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

const entry: RaceEntry = {
    asOf: '12/25/2020',
    category: '1',
    detailed: ['2', '3']
};

describe('RaceEntryView', () => {
    it('should render label with value', () => {
        const { getByText } = render(<RaceEntryView entry={entry} />);
        const asOf = getByText('As of');
        expect(asOf.parentElement?.children[1]).toHaveTextContent('12/25/2020');

        const type = getByText('Race');
        expect(type.parentElement?.children[1]).toHaveTextContent('race name');

        const use = getByText('Detailed race');
        expect(use.parentElement?.children[1]).toHaveTextContent('detailed race1, detailed race2');
    });

    it('should render label with value for empty detailed race', () => {
        const { getByText } = render(<RaceEntryView entry={{ ...entry, detailed: [] }} />);
        const asOf = getByText('As of');
        expect(asOf.parentElement?.children[1]).toHaveTextContent('12/25/2020');

        const type = getByText('Race');
        expect(type.parentElement?.children[1]).toHaveTextContent('race name');

        const use = getByText('Detailed race');
        expect(use.parentElement?.children[1]).toHaveTextContent('');
    });
});

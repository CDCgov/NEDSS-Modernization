import { render, within } from '@testing-library/react';
import { MergeRace } from 'apps/deduplication/api/model/MergePatient';
import { RaceDetails } from './RaceDetails';

const defaultRace: MergeRace = {
    personUid: '1',
    raceCode: '2106-3',
    asOf: '2014-03-12T00:00:00.000',
    race: 'White',
    detailedRaces: 'European'
};

const Fixture = ({ race = defaultRace }: { race?: MergeRace }) => {
    return <RaceDetails race={race} />;
};

describe('RaceDetails', () => {
    it('should render the proper labels', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('As of date')).toBeInTheDocument();
        expect(getByText('Race')).toBeInTheDocument();
        expect(getByText('Detailed race')).toBeInTheDocument();
    });

    it('should render "---" for missing optional fields', () => {
        const sparseRace: MergeRace = {
            personUid: '1',
            raceCode: '2106-3',
            asOf: '2014-03-11T00:00:00.000',
            race: 'White'
        };
        const { getByText } = render(<Fixture race={sparseRace} />);
        expect(within(getByText('Detailed race').parentElement!).getByText('---')).toBeInTheDocument();
    });

    it('should display as of date in proper format', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('03/12/2014')).toBeInTheDocument();
    });
});

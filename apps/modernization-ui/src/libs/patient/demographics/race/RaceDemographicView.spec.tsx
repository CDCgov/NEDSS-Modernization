import { today } from 'date';
import { asSelectable } from 'options';
import { render, screen } from '@testing-library/react';
import { RaceDemographic } from './race';
import { RaceDemographicView } from './RaceDemographicView';

const entry: RaceDemographic = {
    asOf: '2000-01-01',
    race: asSelectable('A', 'Asian'),
    detailed: [asSelectable('IN', 'Indian')]
};

describe('phone email demographic', () => {
    it('display as of', () => {
        render(<RaceDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Race as of' });

        expect(actual).toHaveTextContent('01/01/2000');
    });

    it('display race', () => {
        render(<RaceDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Race' });

        expect(actual).toHaveTextContent('Asian');
    });

    it('display detailed race', () => {
        render(<RaceDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Detailed race' });

        expect(actual).toHaveTextContent('Indian');
    });
});

import { asSelectable } from 'options';
import { IdentificationDemographic } from './identifications';
import { render, screen } from '@testing-library/react';
import { IdentificationDemographicView } from './IdentificationDemographicView';

const entry: IdentificationDemographic = {
    asOf: '2000-01-01',
    type: asSelectable('DL', 'Driver license'),
    issuer: asSelectable('DR', 'Dr. Keble'),
    value: '12345'
};

describe('Identification demographic view', () => {
    it('should display as of date', () => {
        render(<IdentificationDemographicView entry={entry} />);
        const actual = screen.getByRole('definition', { name: 'Identification as of' });

        expect(actual).toHaveTextContent('01/01/2000');
    });

    it('should display type', () => {
        render(<IdentificationDemographicView entry={entry} />);
        const actual = screen.getByRole('definition', { name: 'Type' });

        expect(actual).toHaveTextContent('Driver license');
    });
    it('should display assigning authority', () => {
        render(<IdentificationDemographicView entry={entry} />);
        const actual = screen.getByRole('definition', { name: 'Assigning authority' });

        expect(actual).toHaveTextContent('Dr. Keble');
    });
    it('should display id value', () => {
        render(<IdentificationDemographicView entry={entry} />);
        const actual = screen.getByRole('definition', { name: 'ID value' });

        expect(actual).toHaveTextContent('12345');
    });
});

import { render } from '@testing-library/react';
import { MatchConfiguration } from './MatchConfiguration';

let mockReturnValue: string | undefined = 'value';
jest.mock('apps/deduplication/api/useDataElements', () => ({
    useDataElements: () => {
        return { dataElements: mockReturnValue };
    }
}));

const Fixture = () => {
    return <MatchConfiguration />;
};

describe('MatchConfiguration', () => {
    // Display
    it('should show Heading', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Person match configuration')).toBeInTheDocument();
    });

    it('should display algorithm not configured message if no data elements are present', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Select a pass configuration')).toBeInTheDocument();
    });

    it('should display algorithm not configured message if no data elements are present', () => {
        mockReturnValue = undefined;
        const { getByText } = render(<Fixture />);

        expect(getByText('Algorithm not configured')).toBeInTheDocument();
    });
});

import { render } from '@testing-library/react';
import { MatchConfiguration } from './MatchConfiguration';
import { MemoryRouter } from 'react-router-dom';

let mockReturnValue: string | undefined = 'value';
jest.mock('apps/deduplication/api/useDataElements', () => ({
    useDataElements: () => {
        return { configuration: mockReturnValue };
    }
}));

const Fixture = () => {
    return (
        <MemoryRouter>
            <MatchConfiguration />
        </MemoryRouter>
    );
};

describe('MatchConfiguration', () => {
    // Display
    it('should show Heading', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Person match configuration')).toBeInTheDocument();
    });

    it('should display select pass message if no data elements are present', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('No pass configurations have been created')).toBeInTheDocument();
    });

    it('should display algorithm not configured message if no data elements are present', () => {
        mockReturnValue = undefined;
        const { getByText } = render(<Fixture />);

        expect(getByText('Algorithm not configured')).toBeInTheDocument();
    });
});

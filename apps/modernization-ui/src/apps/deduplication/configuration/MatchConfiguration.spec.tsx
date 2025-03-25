import { render } from '@testing-library/react';
import { MatchConfiguration } from './MatchConfiguration';
import { MemoryRouter } from 'react-router';
import { AlertProvider } from 'alert';

let mockReturnValue: string | undefined = 'value';
jest.mock('apps/deduplication/api/useDataElements', () => ({
    useDataElements: () => {
        return { dataElements: mockReturnValue };
    }
}));

const Fixture = () => {
    return (
        <MemoryRouter>
            <AlertProvider>
                <MatchConfiguration />
            </AlertProvider>
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

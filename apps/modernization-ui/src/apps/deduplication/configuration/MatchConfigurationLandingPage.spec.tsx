import { render } from '@testing-library/react';
import { MatchConfigurationLandingPage } from './MatchConfigurationLandingPage';
import { MemoryRouter } from 'react-router';
import { AlertProvider } from 'alert';
import { DataElements } from '../api/model/DataElement';

let mockReturnValue: DataElements | undefined = { firstName: { active: true } };
jest.mock('apps/deduplication/api/useDataElements', () => ({
    useDataElements: () => {
        return { dataElements: mockReturnValue };
    }
}));
jest.mock('apps/deduplication/api/useMatchConfiguration', () => ({
    useMatchConfiguration: () => {
        return { passes: [] };
    }
}));

const Fixture = () => {
    return (
        <MemoryRouter>
            <AlertProvider>
                <MatchConfigurationLandingPage />
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

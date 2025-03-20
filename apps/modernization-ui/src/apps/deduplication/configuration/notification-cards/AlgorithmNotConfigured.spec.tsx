import { render } from '@testing-library/react';
import { AlgorithmNotConfigured } from './AlgorithmNotConfigured';
import { MemoryRouter } from 'react-router';

const Fixture = () => (
    <MemoryRouter>
        <AlgorithmNotConfigured />
    </MemoryRouter>
);
describe('AlgorithmNotConfigured', () => {
    // Pass count > 0
    it('should display proper heading text', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Algorithm not configured')).toBeInTheDocument();
    });

    it('should display proper body text', () => {
        const { getByText } = render(<Fixture />);

        expect(
            getByText(
                (content) =>
                    content ===
                    'To configure the algorithm, first configure the data elements used in the algorithm.Get started by manually configuring the data elements or importing a configuration file.'
            )
        ).toBeInTheDocument();
    });
});

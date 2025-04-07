import { render } from '@testing-library/react';
import { AlgorithmNotConfigured } from './AlgorithmNotConfigured';
import { MemoryRouter } from 'react-router';
import userEvent from '@testing-library/user-event';

const onImportClick = jest.fn();

const Fixture = () => (
    <MemoryRouter>
        <AlgorithmNotConfigured onImportClick={onImportClick} />
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

    it('should trigger import click when import button is clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);
        const importButton = getAllByRole('button')[1];

        expect(importButton).toHaveTextContent('Import configuration file');

        await user.click(importButton);

        expect(onImportClick).toHaveBeenCalledTimes(1);
    });
});

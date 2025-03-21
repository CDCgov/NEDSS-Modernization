import { render } from '@testing-library/react';
import { SelectPass } from './SelectPass';

describe('SelectPass', () => {
    // Pass count > 0
    it('should display proper heading text when passes are present', () => {
        const { getByText } = render(<SelectPass passCount={1} />);

        expect(getByText('Select a pass configuration')).toBeInTheDocument();
    });

    it('should display proper body text when passes are present', () => {
        const { getByText } = render(<SelectPass passCount={1} />);

        expect(
            getByText(
                'To get started, select a pass configuration from the left to edit or click "Add pass configuration" to create a new pass.'
            )
        ).toBeInTheDocument();
    });

    // No passes
    it('should display proper heading text when there are no passes', () => {
        const { getByText } = render(<SelectPass passCount={0} />);

        expect(getByText('No pass configurations have been created')).toBeInTheDocument();
    });

    it('should display proper body text when there are no passes', () => {
        const { getAllByText } = render(<SelectPass passCount={0} />);
        expect(
            getAllByText(
                (content, element) =>
                    element?.textContent === 'To get started, select "Add pass configuration" from the left panel.'
            )[0]
        ).toBeInTheDocument();
    });
});

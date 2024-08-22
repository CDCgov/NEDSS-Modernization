import { render } from '@testing-library/react';
import { About } from './About';

describe('when About renders', () => {
    it('should display headings', () => {
        const { queryAllByRole } = render(<About />);
        const headings = queryAllByRole('heading');

        expect(headings[0]).toHaveTextContent('Documentation');
        expect(headings[1]).toHaveTextContent('Feedback');
        expect(headings[2]).toHaveTextContent('Technical Support');
    });

    it('should contain a mailto link to the nbs@cdc.gov email address', () => {
        const { queryAllByRole } = render(<About />);

        const emails = queryAllByRole('link', { name: 'nbs@cdc.gov' });

        emails.forEach((email) => expect(email).toHaveAttribute('href', 'mailto:nbs@cdc.gov'));
    });
});

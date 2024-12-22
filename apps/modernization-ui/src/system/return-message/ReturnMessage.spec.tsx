import { render } from '@testing-library/react';
import { ReturnMessage } from './ReturnMessage';

describe('when displaying a ReturnMessage', () => {
    it('should render the title', () => {
        const { getByRole } = render(<ReturnMessage title="The return title">message</ReturnMessage>);

        const title = getByRole('heading', { name: 'The return title' });

        expect(title).toBeInTheDocument();
    });

    it('should render the message', () => {
        const { getByText } = render(<ReturnMessage title="title">The return message</ReturnMessage>);

        const message = getByText('The return message');

        expect(message).toBeInTheDocument();
    });

    it('should contain a "Return to NBS" link', () => {
        const { getByRole } = render(<ReturnMessage title="title">message</ReturnMessage>);

        const returnLink = getByRole('link', { name: 'Return to NBS' });

        expect(returnLink).toBeInTheDocument();
        expect(returnLink).toHaveAttribute('href', '/nbs/login');
    });
});

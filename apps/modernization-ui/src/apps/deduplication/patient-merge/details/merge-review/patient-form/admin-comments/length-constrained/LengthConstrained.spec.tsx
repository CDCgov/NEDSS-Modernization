import { render } from '@testing-library/react';
import { LengthConstrained } from './LengthConstrained';
import userEvent from '@testing-library/user-event';

const Fixture = ({ limit = 10 }) => {
    return <LengthConstrained content="This is my test text content that is 56 characters long." limit={limit} />;
};
describe('LengthConstrained', () => {
    it('should limit text to specified limit', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('This is my')).toBeInTheDocument();
    });

    it('should not limit text shorter than specified limit', () => {
        const { getByText } = render(<Fixture limit={56} />);

        expect(getByText('This is my test text content that is 56 characters long.')).toBeInTheDocument();
    });

    it('should show the remaining text when show more is clicked', async () => {
        const user = userEvent.setup();
        const { getByText, getByRole } = render(<Fixture />);

        expect(getByText('This is my')).toBeInTheDocument();

        const showMore = getByRole('button');
        expect(showMore).toHaveTextContent('[show more]');

        await user.click(showMore);
        expect(getByText('This is my test text content that is 56 characters long.')).toBeInTheDocument();
    });

    it('should hide text over the limit when show less is clicked', async () => {
        const user = userEvent.setup();
        const { getByText, getByRole } = render(<Fixture />);

        await user.click(getByRole('button'));
        expect(getByText('This is my test text content that is 56 characters long.')).toBeInTheDocument();

        const showLess = getByRole('button');
        expect(showLess).toHaveTextContent('[show less]');
        await user.click(showLess);

        expect(getByText('This is my')).toBeInTheDocument();
    });
});

import { render } from '@testing-library/react';
import { NotificationCard } from './NotificationCard';

const Fixture = () => {
    return <NotificationCard heading={'Heading text'} body={'body text'} buttons={<button>Button text</button>} />;
};
describe('NotificationCard', () => {
    it('should display heading', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Heading text')).toBeInTheDocument();
    });

    it('should display body', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('body text')).toBeInTheDocument();
    });

    it('should display buttons', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Button text')).toBeInTheDocument();
    });
});

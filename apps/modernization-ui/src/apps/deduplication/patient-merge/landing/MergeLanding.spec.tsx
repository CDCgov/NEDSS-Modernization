import { render, within } from '@testing-library/react';
import { MergeLanding } from './MergeLanding';
import { MemoryRouter } from 'react-router';

const Fixture = () => {
    return (
        <MemoryRouter>
            <MergeLanding />
        </MemoryRouter>
    );
};
describe('MergeLanding', () => {
    it('should have the proper heading', () => {
        const { getByRole } = render(<Fixture />);

        expect(getByRole('heading')).toHaveTextContent('Matches requiring review');
    });

    it('should have two buttons in the header', () => {
        const { getByRole } = render(<Fixture />);
        const buttons = within(getByRole('heading').parentElement!).getAllByRole('button');
        expect(buttons).toHaveLength(2);

        expect(buttons[0].children[0].children[0]).toHaveAttribute('xlink:href', 'undefined#print');
        expect(buttons[1].children[0].children[0]).toHaveAttribute('xlink:href', 'undefined#file_download');
    });
});

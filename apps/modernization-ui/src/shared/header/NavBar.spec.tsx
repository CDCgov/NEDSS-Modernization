import { usePage } from 'page';
import { NavBar } from './NavBar';
import { render } from '@testing-library/react';

jest.mock('page', () => ({
    usePage: jest.fn()
}));

describe('NavBar component tests', () => {
    it('should render navigation bar', () => {
        (usePage as jest.Mock).mockReturnValue({ title: 'Test page' });
        const { getByText } = render(<NavBar />);
        expect(getByText('Test page')).toBeInTheDocument();
    });
});

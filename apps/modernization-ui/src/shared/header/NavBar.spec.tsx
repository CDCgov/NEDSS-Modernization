import { vi } from 'vitest';
import { render } from '@testing-library/react';
import NavBar from './NavBar';
import { usePage } from 'page';

vi.mock('page', () => ({
    usePage: vi.fn()
}));

vi.mock('react-router', () => ({
    useLocation: vi.fn()
}));

describe('NavBar component tests', () => {
    it('should render navigation bar', () => {
        (usePage as ReturnType<typeof vi.fn>).mockReturnValue({ title: 'Test page' });
        const { getByText } = render(<NavBar />);
        expect(getByText('Test page')).toBeInTheDocument();
    });
});

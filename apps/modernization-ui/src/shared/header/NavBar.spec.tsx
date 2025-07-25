import { vi } from 'vitest';
import { render } from '@testing-library/react';
import { usePage } from 'page';
import { NavBar } from './NavBar';

const mockPermissions = ['ADMINISTRATE-SYSTEM', 'ADMINISTRATE-SECURITY'];
const mockAllows = (permission: string) => mockPermissions.includes(permission);
const mockAllowFn = jest.fn(mockAllows);

vi.mock('page', () => ({
    usePage: vi.fn()
}));

vi.mock('react-router', () => ({
    useLocation: vi.fn()
}));

vi.mock('../../libs/permission/usePermissions', () => ({
    usePermissions: () => ({
        permissions: mockPermissions,
        allows: mockAllowFn
    })
}));

describe('NavBar component tests', () => {
    beforeEach(() => {
        mockAllowFn.mockImplementation(mockAllows);
        (usePage as jest.Mock).mockReturnValue({ title: 'Test page' });
    });

    it('should render navigation bar', () => {
        const { getByText } = render(<NavBar />);
        expect(getByText('Test page')).toBeInTheDocument();
    });

    it('should display System Management for users with Permissions', () => {
        const { getByText } = render(<NavBar />);
        expect(getByText('System Management')).toBeInTheDocument();
    });
});

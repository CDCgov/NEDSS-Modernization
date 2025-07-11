import { usePage } from 'page';
import { NavBar } from './NavBar';
import { render } from '@testing-library/react';

const mockPermissions = ['ADMINISTRATE-SYSTEM', 'ADMINISTRATE-SECURITY'];
const mockAllows = (permission: string) => mockPermissions.includes(permission);
const mockAllowFn = jest.fn(mockAllows);

jest.mock('../../libs/permission/usePermissions', () => ({
    usePermissions: () => ({
        permissions: mockPermissions,
        allows: mockAllowFn,
    }),
}));

jest.mock('page', () => ({
    usePage: jest.fn()
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

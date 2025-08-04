import { usePage } from 'page';
import { NavBar } from './NavBar';
import { render } from '@testing-library/react';
import { permissions} from 'libs/permission';

let mockPermissions: string[] = [];
const mockAllowFn = jest.fn((permission: string) => mockPermissions.includes(permission));

jest.mock('../../libs/permission/usePermissions', () => ({
    usePermissions: () => ({
        permissions: mockPermissions,
        allows: mockAllowFn,
    }),
}));

jest.mock('page', () => ({
    usePage: jest.fn(),
}));

const renderNavBarWithPermissions = (permissions: string[]) => {
    mockPermissions = permissions;
    return render(<NavBar />);
};

describe('NavBar component tests', () => {
    beforeEach(() => {
        mockAllowFn.mockClear();
        (usePage as jest.Mock).mockReturnValue({ title: 'Test page' });
    });

    it('should render navigation bar', () => {
        const { getByText } = renderNavBarWithPermissions([]);
        expect(getByText('Test page')).toBeInTheDocument();
    });

    describe('System Management section', () => {
        it('should display System Management with appropriate permission', () => {
            const { getByText } = renderNavBarWithPermissions(['ADMINISTRATE-SYSTEM']);
            expect(getByText('System Management')).toBeInTheDocument();
        });
    });

    describe('Reports section', () => {
        it.each([
            [permissions.viewReports.template],
            [permissions.viewReports.public],
            [permissions.viewReports.private],
            [permissions.viewReports.reportingFacility],
        ])('should show Reports with permission: %s', (permission) => {
            const { getByText } = renderNavBarWithPermissions([permission]);
            expect(getByText('Reports')).toBeInTheDocument();
        });

        it('should show Reports with multiple report permissions', () => {
            const { getByText } = renderNavBarWithPermissions([
                permissions.viewReports.template,
                permissions.viewReports.private,
            ]);
            expect(getByText('Reports')).toBeInTheDocument();
        });

        it('should NOT show Reports without report permissions', () => {
            const { queryByText } = renderNavBarWithPermissions(['ADMINISTRATE-SYSTEM']);
            expect(queryByText('Reports')).not.toBeInTheDocument();
        });
    });
});

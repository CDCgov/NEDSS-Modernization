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

    describe('Data Entry section', ()=>{
        it.each([
            [permissions.morbidityReport.add],
            [permissions.labReport.add],
            [permissions.summaryReports.view],
            [permissions.patient.search]
        ])('should show Data Entry with permission: %s', (permission) => {
            const { getByText } = renderNavBarWithPermissions([permission]);
            expect(getByText('Data Entry')).toBeInTheDocument();
        });

        it('should NOT show Data Entry without permissions', () => {
            const { queryByText } = renderNavBarWithPermissions(['ADMINISTRATE-SYSTEM']);
            expect(queryByText('Data Entry')).not.toBeInTheDocument();
        });
    });

    describe('Merge Patients Section', ()=> {
        it.each([
            [permissions.patient.merge]
        ])('should show Merge Patients with permission: %s', (permission) => {
            const { getByText } = renderNavBarWithPermissions([permission]);
            expect(getByText('Merge Patients')).toBeInTheDocument();
        });

        it('should NOT show Merge Patients without merge permissions', () => {
            const { queryByText } = renderNavBarWithPermissions(['ADMINISTRATE-SYSTEM']);
            expect(queryByText('Merge Patients')).not.toBeInTheDocument();
        });
    });

    describe('Reports section', () => {
        it.each([
            [permissions.reports.template.view],
            [permissions.reports.public.view],
            [permissions.reports.private.view],
            [permissions.reports.reportingFacility.view],
        ])('should show Reports with permission: %s', (permission) => {
            const { getByText } = renderNavBarWithPermissions([permission]);
            expect(getByText('Reports')).toBeInTheDocument();
        });

        it('should show Reports with multiple report permissions', () => {
            const { getByText } = renderNavBarWithPermissions([
                permissions.reports.template.view,
                permissions.reports.private.view,
            ]);
            expect(getByText('Reports')).toBeInTheDocument();
        });

        it('should NOT show Reports without report permissions', () => {
            const { queryByText } = renderNavBarWithPermissions(['ADMINISTRATE-SYSTEM']);
            expect(queryByText('Reports')).not.toBeInTheDocument();
        });
    });
});

import { Mock } from 'vitest';
import { render } from '@testing-library/react';
import { usePage } from 'page';
import { NavBar } from './NavBar';
import { permissions } from 'libs/permission';

let mockPermissions: string[] = [];
const mockAllowFn = vi.fn((permission: string) => mockPermissions.includes(permission));

let mockFeatures: any = { deduplication: { enabled: false } };

vi.mock('page', () => ({
    usePage: vi.fn(),
}));

vi.mock('react-router', () => ({
    useLocation: vi.fn(),
}));

vi.mock('../../libs/permission/usePermissions', () => ({
    usePermissions: () => ({
        permissions: mockPermissions,
        allows: mockAllowFn,
    }),
}));

vi.mock('../../feature', () => ({
    FeatureToggle: ({ guard, fallback, children }: any) => {
        return guard(mockFeatures) ? children : fallback;
    },
}));

const renderNavBarWithPermissions = (permissions: string[], featuresConfig = mockFeatures) => {
    mockPermissions = permissions;
    mockFeatures = featuresConfig;
    return render(<NavBar />);
};

describe('NavBar component tests', () => {
    beforeEach(() => {
        mockAllowFn.mockClear();
        (usePage as Mock).mockReturnValue({ title: 'Test page' });
    });

    it('should render navigation bar', () => {
        const { getByText } = renderNavBarWithPermissions([]);
        expect(getByText('Test page')).toBeInTheDocument();
    });

    describe('System Management section', () => {
        const basePermissions = ['ADMINISTRATE-SYSTEM'];

        describe('When deduplication is disabled', () => {
            const config = { deduplication: { enabled: false } };

            it('should display System Management with permissions', () => {
                const { getByText } = renderNavBarWithPermissions(basePermissions, config);
                expect(getByText('System Management')).toBeInTheDocument();
            });

            it('should not display System Management with only MERGE-PATIENT permission', () => {
                const { queryByText } = renderNavBarWithPermissions(['MERGE-PATIENT'], config);
                expect(queryByText('System Management')).not.toBeInTheDocument();
            });
        });

        describe('When deduplication is enabled', () => {
            const config = { deduplication: { enabled: true } };

            it('should display System Management with permissions', () => {
                const { getByText } = renderNavBarWithPermissions([...basePermissions, 'MERGE-PATIENT'], config);
                expect(getByText('System Management')).toBeInTheDocument();
            });

            it('should display System Management with only MERGE-PATIENT permission', () => {
                const { getByText } = renderNavBarWithPermissions(['MERGE-PATIENT'], config);
                expect(getByText('System Management')).toBeInTheDocument();
            });
        });
    });

    describe('Data Entry section', () => {
        it.each([
            [permissions.morbidityReport.add],
            [permissions.labReport.add],
            [permissions.summaryReports.view],
            [permissions.patient.search],
        ])('should show Data Entry with permission: %s', (permission) => {
            const { getByText } = renderNavBarWithPermissions([permission]);
            expect(getByText('Data Entry')).toBeInTheDocument();
        });

        it('should NOT show Data Entry without permissions', () => {
            const { queryByText } = renderNavBarWithPermissions(['ADMINISTRATE-SYSTEM']);
            expect(queryByText('Data Entry')).not.toBeInTheDocument();
        });
    });

    describe('Merge Patients Section', () => {
        it.each([[permissions.patient.merge]])('should show Merge Patients with permission: %s', (permission) => {
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

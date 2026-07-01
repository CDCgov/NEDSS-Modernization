import { render, screen, waitFor } from '@testing-library/react';
import * as generated from 'generated';
import userEvent from '@testing-library/user-event';
import { Layout } from 'layout';
import { createMemoryRouter, RouterProvider, useLoaderData } from 'react-router';
import { ReactNode } from 'react';
import { LoadingBlock } from 'libs/loading/block';
import { ReportResultPage } from './ReportResultPage.tsx';
import { axe } from 'jest-axe';
import { ReportConfiguration } from 'generated';

vi.mock('react-router', async () => {
    const actual = await vi.importActual<typeof import('react-router')>('react-router');
    return {
        ...actual,
        default: actual,
        useLoaderData: vi.fn(),
        useParams: vi.fn(() => ({ reportUid: '2', dataSourceUid: '1' })), // Mock useParams to return a default value
    };
});

vi.mock('generated', async (importOriginal) => {
    const actual = await importOriginal<typeof import('generated')>();
    return {
        ...actual,
        ReportControllerService: {
            saveReport: vi.fn(),
            saveAsReport: vi.fn(),
        },
    };
});

// mock identifier to display "Save" button
vi.mock('user', () => ({
    useUser: () => ({
        state: {
            user: {
                identifier: 0,
                name: {
                    display: 'User Name',
                },
            },
        },
    }),
}));

vi.mock('libs/permission', async () => {
    const actual = await vi.importActual<typeof import('libs/permission')>('libs/permission');
    return {
        ...actual,
        Permitted: vi.fn(({ children }: { children: ReactNode }) => <>{children}</>),
        permitsAll: vi.fn(() => () => true),
    };
});

vi.mock('../utils/getUserReportCreatePermissions.ts', () => ({
    getUserReportCreatePermissionsOptions: vi.fn(() => [
        { name: 'Private', value: 'PRIVATE' },
        { name: 'Public', value: 'PUBLIC' },
    ]),
}));

vi.mock('options/report', () => ({
    useReportSections: () => [{ label: 'Section 1', value: '1000', name: 'Section 1' }],
}));

// mock location to see if redirect works
const locationMock = {
    href: '',
} as Location;

let originalWindow: Location;
beforeEach((): void => {
    locationMock.href = '';
    originalWindow = window.location;
    (window as any).location = locationMock;

    vi.mocked(useLoaderData).mockReturnValue(MOCK_CONFIG);
});

afterAll((): void => {
    (window as any).location = originalWindow;
});

afterEach(() => {
    vi.restoreAllMocks();
});

const MOCK_CONFIG: ReportConfiguration = {
    title: 'Test Report',
    ownerUid: 0,
    group: ReportConfiguration.group.PUBLIC,
    sectionCd: '1000',
    dataSource: {
        id: 1,
        name: 'nbs_ods.data_source',
        hasJurisdictionSecurity: false,
        hasFacilitySecurity: false,
    },
    library: {
        id: 2,
        runner: 'python',
        name: 'nbs_sr_99',
        description: 'a library',
        isBuiltin: true,
        allowColumnSelection: false,
    },
    columns: [
        {
            id: 2001,
            name: 'FULL_NAME',
            title: 'Full Name',
            sourceTypeCode: 'STRING',
            isDisplayable: true,
            isFilterable: true,
        },
    ],
    basicFilters: [],
    advancedFilter: undefined,
};

const MOCK_SAVE_RESULT: generated.ReportId = {
    reportUid: 1,
    dataSourceUid: 2,
};

const renderWithRouter = (props?: Partial<React.ComponentProps<typeof ReportResultPage>>) => {
    const routes = [
        {
            path: '/:reportUid/:dataSourceUid',
            element: <Layout />,
            HydrateFallback: LoadingBlock,
            children: [{ index: true, element: <ReportResultPage {...props} /> }],
        },
    ];

    const router = createMemoryRouter(routes, { initialEntries: ['/2/1'] });
    return render(<RouterProvider router={router} />);
};

describe('report result page', () => {
    const createMockProps = () => ({
        config: MOCK_CONFIG,
        wasExported: false,
        resultLoading: false,
        handleRefineReport: vi.fn(),
        executionRequest: {
            reportUid: 2,
            dataSourceUid: 1,
            isExport: false,
            basicFilters: [],
        },
    });

    it('should render with no accessibility violations', async () => {
        const props = createMockProps();
        const { container } = renderWithRouter(props);
        expect(await axe(container)).toHaveNoViolations();
    });

    it('calls handleRefineReport when "Refine Report" button is clicked', async () => {
        const user = userEvent.setup();
        const props = createMockProps();
        const { getByRole } = renderWithRouter(props);

        const refineButton = getByRole('button', { name: 'Refine Report' });
        await user.click(refineButton);

        expect(props.handleRefineReport).toHaveBeenCalledTimes(1);
    });

    describe('save modal', () => {
        it('saves the report and redirects if successful', async () => {
            const user = userEvent.setup();
            const mockSaveApi = vi
                .mocked(generated.ReportControllerService.saveReport)
                .mockResolvedValue(MOCK_SAVE_RESULT);

            const props = createMockProps();
            const { findByText, getAllByRole } = renderWithRouter(props);

            const saveButtons = await getAllByRole('button', { name: 'Save' });
            const openModalButton = saveButtons[0];
            await user.click(openModalButton);

            expect(await findByText('Overwrite saved report?')).toBeVisible();
            expect(
                await findByText(
                    'This will replace the saved criteria with your current criteria. This action cannot be undone.'
                )
            ).toBeVisible();

            const confirmSaveButton = saveButtons[1];
            await user.click(confirmSaveButton);

            expect(mockSaveApi).toHaveBeenCalledWith({
                dataSourceUid: 1,
                reportUid: 2,
                requestBody: expect.objectContaining({
                    dataSourceUid: 1,
                    reportUid: 2,
                    isExport: false,
                }),
            });

            await waitFor(() => {
                expect(window.location.href).toBe('/nbs/ManageReports.do');
            });
        });

        it('shows an error when saving fails', async () => {
            const user = userEvent.setup();
            vi.mocked(generated.ReportControllerService.saveReport).mockRejectedValue(
                new Error('Issue with saving report')
            );

            const props = createMockProps();
            const { getAllByRole, findByText } = renderWithRouter(props);

            const saveButtons = await getAllByRole('button', { name: 'Save' });
            const openModalButton = saveButtons[0];
            await user.click(openModalButton);

            const confirmSaveButton = saveButtons[1];
            await user.click(confirmSaveButton);

            expect(await findByText('Issue with saving report')).toBeVisible();
            expect(await findByText(/There was an error saving your report/)).toBeVisible();
            expect(window.location.href).not.toBe('/nbs/ManageReports.do');
        });
    });

    describe('save as modal', () => {
        it('saves the report as new and redirects if successful', async () => {
            const user = userEvent.setup();
            const mockSaveAsApi = vi
                .mocked(generated.ReportControllerService.saveAsReport)
                .mockResolvedValue(MOCK_SAVE_RESULT);

            const props = createMockProps();
            const { findByLabelText, findByText, getByRole, getByTestId } = renderWithRouter(props);

            const openModalButton = await getByRole('button', { name: 'Save As' });
            await user.click(openModalButton);

            expect(await findByText('Save as a new report')).toBeVisible();

            const nameInput = await findByLabelText('Report name');
            await user.type(nameInput, 'Test report');

            const descInput = await findByLabelText('Description');
            await user.type(descInput, 'Test report description');

            const sectionInput = await findByLabelText('Report section');
            await user.selectOptions(sectionInput, '1000');

            const privateRadio = screen.getByRole('radio', { name: 'Private' });
            expect(privateRadio).toBeChecked();

            const confirmSaveAsButton = await getByTestId('report-save-as-btn');
            await user.click(confirmSaveAsButton);

            expect(mockSaveAsApi).toHaveBeenCalledWith({
                dataSourceUid: 1,
                reportUid: 2,
                requestBody: expect.objectContaining({
                    description: 'Test report description',
                    executionRequest: {
                        dataSourceUid: 1,
                        reportUid: 2,
                        isExport: false,
                        basicFilters: [],
                    },
                    group: 'PRIVATE',
                    reportTitle: 'Test report',
                    sectionCode: '1000',
                }),
            });

            await waitFor(() => {
                expect(window.location.href).toBe('/nbs/ManageReports.do');
            });
        });

        it('shows an error when save as fails', async () => {
            const user = userEvent.setup();
            vi.mocked(generated.ReportControllerService.saveAsReport).mockRejectedValue(
                new Error('Issue with saving report as new')
            );

            const props = createMockProps();
            const { getByRole, findByText, findByLabelText, getByTestId } = renderWithRouter(props);

            const openModalButton = await getByRole('button', { name: 'Save As' });
            await user.click(openModalButton);

            const nameInput = await findByLabelText('Report name');
            await user.type(nameInput, 'Test report');

            const descInput = await findByLabelText('Description');
            await user.type(descInput, 'Test report description');

            const sectionInput = await findByLabelText('Report section');
            await user.selectOptions(sectionInput, '1000');

            const privateRadio = screen.getByRole('radio', { name: 'Private' });
            expect(privateRadio).toBeChecked();

            const confirmSaveAsButton = await getByTestId('report-save-as-btn');
            await user.click(confirmSaveAsButton);

            expect(await findByText('Issue with saving report as new')).toBeVisible();
            expect(await findByText(/There was an error saving your report/)).toBeVisible();
            expect(window.location.href).not.toBe('/nbs/ManageReports.do');
        });
    });
});

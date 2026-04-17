import { render } from '@testing-library/react';
import { ReportRunPage } from './ReportRunPage';
import * as generated from 'generated';
import userEvent from '@testing-library/user-event';
import { ReportConfiguration } from 'generated';
import { Layout } from 'layout';
import { createMemoryRouter, RouterProvider } from 'react-router';
import { ReactNode } from 'react';
import fileDownload from 'js-file-download';

vi.mock('react-router', async () => {
    const actual = await vi.importActual<typeof import('react-router')>('react-router');
    return {
        ...actual,
        default: actual,
        useParams: vi.fn(() => ({ reportUid: '2', dataSourceUid: '1' })), // Mock useParams to return a default value
    };
});
vi.mock('js-file-download', { spy: true });

vi.mock('generated');

vi.mock('libs/permission', async () => {
    const actual = await vi.importActual<typeof import('libs/permission')>('libs/permission');
    return {
        ...actual,
        Permitted: vi.fn(({ children }: { children: ReactNode }) => <>{children}</>),
    };
});

const MOCK_CONFIG: ReportConfiguration = {
    runner: 'python',
    reportTitle: 'Test Report',
    dataSource: {
        name: 'nbs_ods.data_source',
    },
    reportLibrary: {
        runner: 'python',
        libraryName: 'nbs_sr_99',
        isBuiltin: true,
    },
    filters: [],
};

const MOCK_RESULT: generated.ReportResult = {
    header: 'Title',
    content: 'I am the result',
};

const renderWithRouter = () => {
    const routes = [
        {
            path: '/',
            element: <Layout />,
            children: [{ index: true, element: <ReportRunPage /> }],
        },
    ];

    const router = createMemoryRouter(routes, { initialEntries: ['/'] });
    return render(<RouterProvider router={router} />);
};

describe('when given valid params', () => {
    it('renders the config', async () => {
        const mockApi = vi
            .mocked(generated.ReportControllerService.getReportConfiguration)
            .mockResolvedValue(MOCK_CONFIG);
        const { getByRole, findByText } = renderWithRouter();

        expect(getByRole('status')).toHaveTextContent('Loading');

        expect(mockApi).toHaveBeenCalled();

        expect(await findByText(/python/)).toBeVisible();
    });

    it('run button submits config and opens in new tab', async () => {
        const user = userEvent.setup();
        vi.mocked(generated.ReportControllerService.getReportConfiguration).mockResolvedValue(MOCK_CONFIG);
        const mockApi = vi.mocked(generated.ReportControllerService.runReport).mockResolvedValue(MOCK_RESULT);
        const windowOpen = vi.spyOn(window, 'open');

        const { findByRole, findByText } = renderWithRouter();

        const runButton = await findByRole('button', { name: 'Run' });
        await user.click(runButton);
        expect(mockApi).toHaveBeenCalledWith({ requestBody: expect.objectContaining({ isExport: false }) });

        expect(await findByText('Your report has run')).toBeVisible();
        expect(windowOpen).toHaveBeenCalled();
        expect(fileDownload).not.toHaveBeenCalled();
    });

    it('export button submits config and downloads', async () => {
        const user = userEvent.setup();
        vi.mocked(generated.ReportControllerService.getReportConfiguration).mockResolvedValue(MOCK_CONFIG);
        const mockApi = vi.mocked(generated.ReportControllerService.exportReport).mockResolvedValue(MOCK_RESULT);
        const windowOpen = vi.spyOn(window, 'open');

        const { findByRole, findByText } = renderWithRouter();

        const runButton = await findByRole('button', { name: 'Export' });
        await user.click(runButton);
        expect(mockApi).toHaveBeenCalledWith({ requestBody: expect.objectContaining({ isExport: true }) });

        expect(await findByText('Your report has run')).toBeVisible();
        expect(windowOpen).not.toHaveBeenCalled();
        expect(fileDownload).toHaveBeenCalled();
    });
});

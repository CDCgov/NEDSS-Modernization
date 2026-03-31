import { render } from '@testing-library/react';
import { ReportRun } from './ReportRun';
import * as generated from 'generated';
import userEvent from '@testing-library/user-event';

vi.mock('react-router', async () => {
    const actual = await vi.importActual<typeof import('react-router')>('react-router');
    return {
        ...actual,
        default: actual,
        useParams: vi.fn(() => ({ reportUid: '2', dataSourceUid: '1' })), // Mock useParams to return a default value
    };
});

vi.mock('generated');

describe('when given valid params', () => {
    it('renders the config', async () => {
        const mockApi = vi.mocked(generated.ReportControllerService.getReportConfiguration).mockResolvedValue({
            runner: 'python',
        });
        const { getByText, findByText } = render(<ReportRun />);

        expect(getByText('loading')).toBeVisible();

        expect(mockApi).toHaveBeenCalled();

        expect(await findByText(/python/)).toBeVisible();
    });

    it('run button submits config', async () => {
        const user = userEvent.setup();
        vi.mocked(generated.ReportControllerService.getReportConfiguration).mockResolvedValue({
            runner: 'python',
        });
        const mockApi = vi.mocked(generated.ReportControllerService.executeReport).mockResolvedValue('I am the result');

        const { getByRole, findByText } = render(<ReportRun />);

        const runButton = getByRole('button', { name: 'Run' });
        await user.click(runButton);
        expect(mockApi).toHaveBeenCalledWith({ requestBody: expect.objectContaining({ isExport: false }) });

        expect(await findByText('"I am the result"')).toBeVisible();
    });

    it('export button submits config', async () => {
        const user = userEvent.setup();
        vi.mocked(generated.ReportControllerService.getReportConfiguration).mockResolvedValue({
            runner: 'python',
        });
        const mockApi = vi.mocked(generated.ReportControllerService.executeReport).mockResolvedValue('I am the result');

        const { getByRole, findByText } = render(<ReportRun />);

        const runButton = getByRole('button', { name: 'Export' });
        await user.click(runButton);
        expect(mockApi).toHaveBeenCalledWith({ requestBody: expect.objectContaining({ isExport: true }) });

        expect(await findByText('"I am the result"')).toBeVisible();
    });
});

import { render } from '@testing-library/react';
import { ReportRun } from './ReportRun';
import * as generated from 'generated';

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
            reportUid: 1,
            dataSourceUid: 2,
            runner: 'python',
        });
        const { getByText, findByText } = render(<ReportRun />);

        expect(getByText('loading')).toBeVisible();

        expect(mockApi).toHaveBeenCalled();

        expect(await findByText(/2/)).toBeVisible();
    });
});

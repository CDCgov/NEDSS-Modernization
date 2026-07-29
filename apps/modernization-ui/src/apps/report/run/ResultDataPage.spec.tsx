import { getByText, queryByRole, render } from '@testing-library/react';
import { ReportExecutionResult } from 'generated';
import { ResultDataPage } from './ResultDataPage';
import { LoadingBlock } from 'libs/loading/block';
import { ErrorPage } from 'pages/error';
import { createMemoryRouter, RouterProvider } from 'react-router';

vi.mock('react-router', async () => {
    const actual = await vi.importActual<typeof import('react-router')>('react-router');
    return {
        ...actual,
        default: actual,
        useParams: vi.fn(() => ({ resultId: '2' })), // Mock useParams to return a default value
    };
});

const renderWithRouter = () => {
    const routes = [
        {
            path: '/:resultId',
            element: <ResultDataPage />,
            HydrateFallback: LoadingBlock,
            ErrorBoundary: ErrorPage,
        },
    ];

    const router = createMemoryRouter(routes, { initialEntries: [`/2}`] });
    return render(<RouterProvider router={router} />);
};

describe('ResultDataPage', () => {
    it('renders bare bones report result', () => {
        const result: ReportExecutionResult = {
            result: {
                content: 'a,b,c',
            },
            query: 'SELECT * FROM [NBS_ODSE].[dbo].[PHC_Demographic]',
            timestamp: '2026-06-17T19:11:35.595501658',
        };

        window.localStorage.setItem(
            `reportResult.2`,
            JSON.stringify({ result, title: 'My report', dataSourceName: 'nbs_db.My_Table' })
        );
        const { getByRole, getByText } = renderWithRouter();

        expect(getByRole('table')).toBeVisible();
        expect(getByRole('cell', { name: 'No data found.' })).toBeVisible();
        expect(getByRole('columnheader', { name: 'c' })).toBeVisible();
        expect(getByRole('heading', { name: 'My report' })).toBeVisible();
        expect(getByRole('definition', { name: 'Data source' })).toHaveTextContent('nbs_db.My_Table');
        expect(getByRole('definition', { name: 'Description' })).toHaveTextContent('---');
        expect(getByRole('definition', { name: 'Report run date' })).toHaveTextContent('6/17/2026 7:11 PM');
        expect(getByRole('definition', { name: 'Base SQL query' })).toHaveTextContent(
            'SELECT * FROM [NBS_ODSE].[dbo].[PHC_Demographic]'
        );
        expect(getByText('(0 rows)')).toBeVisible();
    });

    it('renders full report result', () => {
        const result: ReportExecutionResult = {
            result: {
                content: 'a,b,c\n1,2,3',
                context_header: 'Georgia | Pertussis, Measles',
                description: '**bold text**\n\n* a list item',
            },
            query: 'SELECT * FROM [NBS_ODSE].[dbo].[PHC_Demographic]',
            timestamp: '2026-06-17T19:11:35.595501658',
        };

        window.localStorage.setItem(
            `reportResult.2`,
            JSON.stringify({ result, title: 'My report', dataSourceName: 'nbs_db.My_Table' })
        );
        const { getByRole, container } = renderWithRouter();

        expect(getByRole('table')).toBeVisible();
        expect(getByRole('cell', { name: '1' })).toBeVisible();
        expect(getByRole('columnheader', { name: 'c' })).toBeVisible();
        expect(getByRole('heading', { name: 'My report' })).toBeVisible();
        expect(getByRole('definition', { name: 'Data source' })).toHaveTextContent('nbs_db.My_Table');
        const description = getByRole('definition', { name: 'Description' });
        expect(getByText(description, 'bold text')).toBeVisible();
        expect(queryByRole(description, 'listitem')).toHaveTextContent('a list item');
        expect(getByRole('definition', { name: 'Report run date' })).toHaveTextContent('6/17/2026 7:11 PM');
        expect(getByRole('definition', { name: 'Base SQL query' })).toHaveTextContent(
            'SELECT * FROM [NBS_ODSE].[dbo].[PHC_Demographic]'
        );
        expect(getByText(container, '(1 row)')).toBeVisible();
    });
});

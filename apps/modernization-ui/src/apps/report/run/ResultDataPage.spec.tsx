import { getByText, queryByRole, render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { LoaderFunctionArgs, useLoaderData } from 'react-router';

import { ReportExecutionResult } from 'generated';

import { loadReportResult, ResultDataPage } from './ResultDataPage';

vi.mock('react-router', async () => {
    const actual = await vi.importActual<typeof import('react-router')>('react-router');
    return {
        ...actual,
        default: actual,
        useLoaderData: vi.fn(),
    };
});

describe('ResultDataPage', () => {
    it('renders bare bones report result', async () => {
        const result: ReportExecutionResult = {
            result: {
                content: 'a,b,c',
            },
            query: 'SELECT * FROM [NBS_ODSE].[dbo].[PHC_Demographic]',
            timestamp: '2026-06-17T19:11:35.595501658',
        };

        vi.mocked(useLoaderData).mockReturnValue({ result, title: 'My report', dataSourceName: 'nbs_db.My_Table' });
        const { getByRole, getByText, container } = render(<ResultDataPage />);

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

        expect(await axe(container)).toHaveNoViolations();
    });

    it('renders full report result', async () => {
        const result: ReportExecutionResult = {
            result: {
                content: 'a,b,c\n1,2,3',
                context_header: 'Georgia | Pertussis, Measles',
                description: '**bold text**\n\n* a list item',
            },
            query: 'SELECT * FROM [NBS_ODSE].[dbo].[PHC_Demographic]',
            timestamp: '2026-06-17T19:11:35.595501658',
        };

        vi.mocked(useLoaderData).mockReturnValue({ result, title: 'My report', dataSourceName: 'nbs_db.My_Table' });
        const { getByRole, container } = render(<ResultDataPage />);

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

        expect(await axe(container)).toHaveNoViolations();
    });

    it('renders warning when no result', async () => {
        vi.mocked(useLoaderData).mockReturnValue(null);
        const { getByRole, container } = render(<ResultDataPage />);

        expect(getByRole('heading', { name: 'No result found' })).toBeVisible();

        expect(await axe(container)).toHaveNoViolations();
    });

    describe('loadReportResult', () => {
        it('reads data from storage and parses when available', async () => {
            window.localStorage.setItem('reportResult.1', '{"hi": 2}');
            const res = await loadReportResult({ params: { resultId: '1' } } as unknown as LoaderFunctionArgs<any>);
            expect(res).toEqual({ hi: 2 });
            expect(window.localStorage.getItem('reportResult.1')).toBeNull();
        });
        it('returns null when unavailable', async () => {
            window.localStorage.setItem('reportResult.1', '{"hi": 2}');
            const res = await loadReportResult({ params: { resultId: '2' } } as unknown as LoaderFunctionArgs<any>);
            expect(res).toBeNull();
            expect(window.localStorage.getItem('reportResult.1')).not.toBeNull();
        });
    });
});

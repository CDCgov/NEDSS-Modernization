import { getByText, queryByRole, render } from '@testing-library/react';
import { LibraryExecutionResult, ReportExecutionResult } from 'generated';
import { ResultDataPage } from './ResultDataPage';

describe('ResultDataPage', () => {
    it('renders bare bones report result', () => {
        const result: ReportExecutionResult = {
            result: {
                content_type: LibraryExecutionResult.content_type.TABLE,
                content: 'a,b,c',
            },
            query: 'SELECT * FROM [NBS_ODSE].[dbo].[PHC_Demographic]',
            timestamp: '2026-06-17T19:11:35.595501658',
        };

        const { getByRole } = render(
            <ResultDataPage result={result} title="My report" dataSourceName="nbs_db.My_Table" />
        );

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
    });

    it('renders full report result', () => {
        const result: ReportExecutionResult = {
            result: {
                content_type: LibraryExecutionResult.content_type.TABLE,
                content: 'a,b,c\n1,2,3',
                subheader: 'Georgia | Pertussis, Measles',
                description: '**bold text**\n\n* a list item',
            },
            query: 'SELECT * FROM [NBS_ODSE].[dbo].[PHC_Demographic]',
            timestamp: '2026-06-17T19:11:35.595501658',
        };

        const { getByRole } = render(
            <ResultDataPage result={result} title="My report" dataSourceName="nbs_db.My_Table" />
        );

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
    });
});

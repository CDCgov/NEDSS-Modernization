import { render } from '@testing-library/react';
import { ReportResult } from 'generated';
import { ResultDataPage } from './ResultDataPage';

describe('ResultDataPage', () => {
    it('renders report result', () => {
        const result: ReportResult = {
            content_type: ReportResult.content_type.TABLE,
            content: 'a,b,c\n1,2,3',
        };

        const { getByRole } = render(<ResultDataPage result={result} />);

        expect(getByRole('table')).toBeVisible();
        expect(getByRole('cell', { name: '1' })).toBeVisible();
        expect(getByRole('columnheader', { name: 'c' })).toBeVisible();
    });
});

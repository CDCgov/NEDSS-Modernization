import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { AlertProvider } from '../../../../alert';
import { BusinessRulesLibraryTable } from './BusinessRulesLibraryTable';

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const dataSummary: any = {};
        const summaries = [dataSummary];
        const { container } = render(
            <BrowserRouter>
                <AlertProvider>
                    <BusinessRulesLibraryTable summaries={summaries} />
                </AlertProvider>
            </BrowserRouter>
        );

        const tableHeads = container.getElementsByClassName('table-head');

        expect(tableHeads[0]?.textContent).toBe('Source Fields');
        expect(tableHeads[1]?.textContent).toBe('Logic');
        expect(tableHeads[2].textContent).toBe('Values');
        expect(tableHeads[3].textContent).toBe('Function');
    });
});

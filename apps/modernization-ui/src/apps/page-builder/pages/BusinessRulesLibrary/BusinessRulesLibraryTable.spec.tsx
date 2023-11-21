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
        expect(tableHeads[4].textContent).toBe('Target Fields');
        expect(tableHeads[5].textContent).toBe('ID');
    });
});
describe('when at least one summary is available', () => {
    const rulesSummary: any = {
        ruleId: 6376,
        templateUid: 1000272,
        ruleFunction: 'Enable',
        ruleDescription: null,
        sourceIdentifier: 'ARB001',
        sourceValue: ['Dengue virus'],
        comparator: '=',
        targetType: 'QUESTION',
        errorMsgText: 'Type of Arbovirus = must be ( Dengue virus ) Dengue (DENV) Serotype',
        targetValueIdentifier: ['404400']
    };
    const summaries = [rulesSummary];

    it('should display the Business rules summaries', async () => {
        const { container } = render(
            <BrowserRouter>
                <AlertProvider>
                    <BusinessRulesLibraryTable summaries={summaries} />
                </AlertProvider>
            </BrowserRouter>
        );

        const tableData = container.getElementsByClassName('table-data');
        expect(tableData[0]).toHaveTextContent('ARB001');
        expect(tableData[1]).toHaveTextContent('Equal to');
        expect(tableData[2]).toHaveTextContent('Dengue virus');
        expect(tableData[3]).toHaveTextContent('Enable');
        expect(tableData[4]).toHaveTextContent('404400');
        expect(tableData[5]).toHaveTextContent('6376');
    });
});

import { render } from '@testing-library/react';
import { BusinessRulesLibraryTable } from './BusinessRulesLibraryTable';
import { BrowserRouter } from 'react-router-dom';

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const dataSummary: any = {};
        const summaries = [dataSummary];
        const { getAllByRole } = render(
            <BrowserRouter>
                <BusinessRulesLibraryTable summaries={summaries} />
            </BrowserRouter>
        );

        const tableHeads = getAllByRole('columnheader');

        expect(tableHeads[0]).toHaveTextContent('Source Fields');
        expect(tableHeads[1]).toHaveTextContent('Logic');
        expect(tableHeads[2]).toHaveTextContent('Values');
        expect(tableHeads[3]).toHaveTextContent('Function');
        expect(tableHeads[4]).toHaveTextContent('Target Fields');
        expect(tableHeads[5]).toHaveTextContent('ID');
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
        const { findAllByRole } = render(
            <BrowserRouter>
                <BusinessRulesLibraryTable summaries={summaries} />
            </BrowserRouter>
        );

        const tableData = await findAllByRole('cell');
        expect(tableData[0]).toHaveTextContent('ARB001');
        expect(tableData[1]).toHaveTextContent('Equal to');
        expect(tableData[2]).toHaveTextContent('Dengue virus');
        expect(tableData[3]).toHaveTextContent('Enable');
        expect(tableData[4]).toHaveTextContent('404400');
        expect(tableData[5]).toHaveTextContent('6376');
    });
});

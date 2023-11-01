import ConditionLibraryTable from './ConditionLibraryTable';
import { render } from '@testing-library/react';
import { Condition, PageSummary } from 'apps/page-builder/generated';
import { BrowserRouter } from 'react-router-dom';

describe('ConditionLibraryTabel', () => {
    describe('when rendered', () => {
        it('should display sentence cased headers', async () => {
            const { container } = render(
                <BrowserRouter>
                    <ConditionLibraryTable
                        conditions={[]}
                        currentPage={1}
                        pageSize={10}
                        totalElements={50}></ConditionLibraryTable>
                </BrowserRouter>
            );

            const tableHeader = container.getElementsByClassName('table-header');
            expect(tableHeader[0].innerHTML).toBe('Condition Library');

            const tableHeads = container.getElementsByClassName('head-name');

            expect(tableHeads[0].innerHTML).toBe('Condition');
            expect(tableHeads[1].innerHTML).toBe('Code');
            expect(tableHeads[2].innerHTML).toBe('Program area');
            expect(tableHeads[3].innerHTML).toBe('Condition family');
            expect(tableHeads[4].innerHTML).toBe('Coinfection group');
            expect(tableHeads[5].innerHTML).toBe('NND');
            expect(tableHeads[6].innerHTML).toBe('Investigation page');
            expect(tableHeads[7].innerHTML).toBe('Status');
        });
    });

    describe('when at least one summary is available', () => {
        const condition: Condition = {
            coinfectionGrpCd: 'CCDD',
            conditionShortNm: 'test condition',
            familyCd: 'FCD',
            id: '11234',
            investigationFormCd: 'IFCD',
            nndInd: 'NNDID',
            progAreaCd: 'PACD',
            statusCd: 'A'
        };

        const conditions = [condition];

        it('should display the page conditions', async () => {
            const { container } = render(
                <BrowserRouter>
                    <ConditionLibraryTable
                        conditions={conditions}
                        currentPage={1}
                        pageSize={10}
                        totalElements={50}></ConditionLibraryTable>
                </BrowserRouter>
            );

            const tableData = container.getElementsByClassName('table-data');

            expect(tableData[0]).toHaveTextContent('test condition');
            expect(tableData[1]).toHaveTextContent('11234');
            expect(tableData[2]).toHaveTextContent('PACD');
            expect(tableData[3]).toHaveTextContent('FCD');
            expect(tableData[4]).toHaveTextContent('CCDD');
            expect(tableData[5]).toHaveTextContent('NNDID');
            expect(tableData[6]).toHaveTextContent('IFCD');
            expect(tableData[7]).toHaveTextContent('Active');
        });

        it('should display "Inactive" when status is I', async () => {
            conditions[0].statusCd = 'I';
            const { container } = render(
                <BrowserRouter>
                    <ConditionLibraryTable
                        conditions={conditions}
                        currentPage={1}
                        pageSize={10}
                        totalElements={50}></ConditionLibraryTable>
                </BrowserRouter>
            );

            const tableData = container.getElementsByClassName('table-data');

            expect(tableData[0]).toHaveTextContent('test condition');
            expect(tableData[1]).toHaveTextContent('11234');
            expect(tableData[2]).toHaveTextContent('PACD');
            expect(tableData[3]).toHaveTextContent('FCD');
            expect(tableData[4]).toHaveTextContent('CCDD');
            expect(tableData[5]).toHaveTextContent('NNDID');
            expect(tableData[6]).toHaveTextContent('IFCD');
            expect(tableData[7]).toHaveTextContent('Inactive');
        });
    });
});

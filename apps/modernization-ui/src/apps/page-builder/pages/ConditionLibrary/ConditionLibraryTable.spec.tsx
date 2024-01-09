import ConditionLibraryTable from './ConditionLibraryTable';
import { render } from '@testing-library/react';
import { Condition } from 'apps/page-builder/generated';
import { MemoryRouter } from 'react-router-dom';

describe('ConditionLibraryTable', () => {
    describe('when rendered', () => {
        it('should display sentence cased headers', async () => {
            const { getByRole, getAllByRole } = render(
                <MemoryRouter>
                    <ConditionLibraryTable
                        conditions={[]}
                        currentPage={1}
                        pageSize={10}
                        totalElements={50}></ConditionLibraryTable>
                </MemoryRouter>
            );

            expect(getByRole('heading', { name: 'Condition Library' })).toBeInTheDocument();

            const tableHeads = getAllByRole('columnheader');

            expect(tableHeads[0]).toHaveTextContent('Condition');
            expect(tableHeads[1]).toHaveTextContent('Code');
            expect(tableHeads[2]).toHaveTextContent('Program area');
            expect(tableHeads[3]).toHaveTextContent('Condition family');
            expect(tableHeads[4]).toHaveTextContent('Coinfection group');
            expect(tableHeads[5]).toHaveTextContent('NND');
            expect(tableHeads[6]).toHaveTextContent('Investigation page');
            expect(tableHeads[7]).toHaveTextContent('Status');
        });
    });

    describe('when at least one summary is available', () => {
        it('should display the page conditions', async () => {
            const conditions: Condition[] = [
                {
                    coinfectionGroup: 'CCDD',
                    name: 'test condition',
                    conditionFamily: 'FCD',
                    id: '11234',
                    page: 'IFCD',
                    nndInd: 'NNDID',
                    programArea: 'PACD',
                    status: 'A'
                }
            ];

            const { findAllByRole } = render(
                <MemoryRouter>
                    <ConditionLibraryTable
                        conditions={conditions}
                        currentPage={1}
                        pageSize={10}
                        totalElements={50}></ConditionLibraryTable>
                </MemoryRouter>
            );

            const tableData = await findAllByRole('cell');

            expect(tableData[0]).toHaveTextContent('test condition');
            expect(tableData[1]).toHaveTextContent('11234');
            expect(tableData[2]).toHaveTextContent('PACD');
            expect(tableData[3]).toHaveTextContent('FCD');
            expect(tableData[4]).toHaveTextContent('CCDD');
            expect(tableData[5]).toHaveTextContent('NNDID');
            expect(tableData[6]).toHaveTextContent('IFCD');
            expect(tableData[7]).toHaveTextContent('Active');
        });

        it('should display "Inactive" when inactive', async () => {
            const conditions: Condition[] = [
                {
                    coinfectionGroup: 'CCDD',
                    name: 'test condition',
                    conditionFamily: 'FCD',
                    id: '11234',
                    page: 'IFCD',
                    nndInd: 'NNDID',
                    programArea: 'PACD',
                    status: 'I'
                }
            ];

            const { findAllByRole } = render(
                <MemoryRouter>
                    <ConditionLibraryTable
                        conditions={conditions}
                        currentPage={1}
                        pageSize={10}
                        totalElements={50}></ConditionLibraryTable>
                </MemoryRouter>
            );

            const tableData = await findAllByRole('cell');

            expect(tableData[7]).toHaveTextContent('Inactive');
        });
    });
});

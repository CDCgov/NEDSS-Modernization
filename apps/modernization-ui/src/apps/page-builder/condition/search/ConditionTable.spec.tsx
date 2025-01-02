import { render } from '@testing-library/react';
import { Condition } from 'apps/page-builder/generated';
import { WithinTableProvider } from 'components/Table/testing';
import { ConditionTable } from './ConditionTable';
import userEvent from '@testing-library/user-event';

const conditions: Condition[] = [
    {
        id: '1',
        coinfectionGroup: 'coinfectionGrpCd',
        name: 'condition name',
        conditionFamily: 'family cd',
        page: 'investigation',
        nndInd: 'nnd',
        programArea: 'prog area',
        status: 'A'
    }
];
describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const { getAllByRole } = render(
            <WithinTableProvider>
                <ConditionTable isLoading={false} conditions={[]} onSelectionChange={jest.fn()} onSort={jest.fn()} />
            </WithinTableProvider>
        );

        const tableHeads = getAllByRole('columnheader');

        expect(tableHeads[1]).toHaveTextContent('Condition');
        expect(tableHeads[2]).toHaveTextContent('Code');
        expect(tableHeads[3]).toHaveTextContent('Program area');
        expect(tableHeads[4]).toHaveTextContent('Condition family');
        expect(tableHeads[5]).toHaveTextContent('Coinfection group');
        expect(tableHeads[6]).toHaveTextContent('NND');
        expect(tableHeads[7]).toHaveTextContent('Investigation page');
        expect(tableHeads[8]).toHaveTextContent('Status');
    });
});

describe('when at least one condition is available', () => {
    it('should display the condition', async () => {
        const { findAllByRole } = render(
            <WithinTableProvider>
                <ConditionTable
                    isLoading={false}
                    conditions={conditions}
                    onSelectionChange={jest.fn()}
                    onSort={jest.fn()}
                />
            </WithinTableProvider>
        );

        const tableData = await findAllByRole('cell');

        expect(tableData[0].children[0]).toHaveClass('usa-checkbox');
        expect(tableData[1]).toHaveTextContent('condition name');
        expect(tableData[2]).toHaveTextContent('1');
        expect(tableData[3]).toHaveTextContent('prog area');
        expect(tableData[4]).toHaveTextContent('family cd');
        expect(tableData[5]).toHaveTextContent('coinfectionGrpCd');
        expect(tableData[6]).toHaveTextContent('nnd');
        expect(tableData[7]).toHaveTextContent('investigation');
        expect(tableData[8]).toHaveTextContent('Active');
    });

    it('should allow selection of a condition', () => {});
});

describe('when a search is performed', () => {
    it('should clear the selected rows when new data is provided', async () => {
        const mockOnSelectionChange = jest.fn();

        const { findAllByRole, rerender } = render(
            <WithinTableProvider>
                <ConditionTable
                    isLoading={false}
                    conditions={conditions}
                    onSelectionChange={mockOnSelectionChange}
                    onSort={jest.fn()}
                />
            </WithinTableProvider>
        );
        expect(mockOnSelectionChange).toHaveBeenLastCalledWith([]);

        const row1Checkbox = (await findAllByRole('cell'))[0].children[0].children[1];
        // select the first condition
        const user = userEvent.setup();
        await user.click(row1Checkbox);
        expect(mockOnSelectionChange).toHaveBeenLastCalledWith(['1']);

        const newConditions = [
            {
                id: '2',
                coinfectionGroup: 'coinfectionGrpCd',
                name: 'condition name',
                conditionFamily: 'family cd',
                page: 'investigation',
                nndInd: 'nnd',
                programArea: 'prog area',
                status: 'A'
            }
        ];
        rerender(
            <WithinTableProvider>
                <ConditionTable
                    isLoading={false}
                    conditions={newConditions}
                    onSelectionChange={mockOnSelectionChange}
                    onSort={jest.fn()}
                />
            </WithinTableProvider>
        );

        expect(mockOnSelectionChange).toHaveBeenLastCalledWith([]);
    });
});

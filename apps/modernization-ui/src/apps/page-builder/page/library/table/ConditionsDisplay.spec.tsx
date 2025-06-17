import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ConditionSummary } from 'apps/page-builder/generated';
import { ConditionsDisplay } from './ConditionsDisplay';

const conditions: ConditionSummary[] = [
    {
        name: 'condition1',
        id: '1'
    },
    {
        name: 'condition2',
        id: '2'
    },
    {
        name: 'condition3',
        id: '3'
    },
    {
        name: 'condition4',
        id: '4'
    },
    {
        name: 'condition5',
        id: '5'
    }
];

const viewLess = 'view less';

describe('ConditionDisplay', () => {
    it('should display No Data if no conditions are provided', () => {
        const { queryByRole, getByText } = render(<ConditionsDisplay conditions={[]} />);
        const entries = queryByRole('listitem');
        expect(entries).toBeNull();
        expect(getByText('---')).toBeInTheDocument();
    });

    it('should display condition and id', () => {
        const { getAllByRole, queryByText } = render(<ConditionsDisplay conditions={conditions} />);
        const entries = getAllByRole('listitem');

        expect(entries).toHaveLength(5);
        expect(entries[0]).toHaveTextContent('condition1 (1)');
        expect(entries[1]).toHaveTextContent('condition2 (2)');
        expect(entries[2]).toHaveTextContent('condition3 (3)');
        expect(entries[3]).toHaveTextContent('condition4 (4)');
        expect(entries[4]).toHaveTextContent('condition5 (5)');

        expect(queryByText(viewLess)).not.toBeInTheDocument();
    });

    it('should display n more if more than 5 conditions are provided', () => {
        let moreConditions = [...conditions, { name: 'condition6', id: '6' }];
        expect(moreConditions).toHaveLength(6);

        const { getAllByRole, queryByText } = render(<ConditionsDisplay conditions={moreConditions} />);

        expect(getAllByRole('listitem')).toHaveLength(5);
        expect(queryByText('1 more...')).toBeInTheDocument();
        expect(queryByText(viewLess)).not.toBeInTheDocument();
    });

    it('should show all conditions on clicking n more', async () => {
        const user = userEvent.setup();

        let moreConditions = [...conditions, { name: 'condition6', id: '6' }];
        expect(moreConditions).toHaveLength(6);

        const { getAllByRole, getByText, queryByText } = render(<ConditionsDisplay conditions={moreConditions} />);
        let entries = getAllByRole('listitem');
        expect(entries).toHaveLength(5);
        const more = getByText('1 more...');
        expect(queryByText(viewLess)).not.toBeInTheDocument();

        await user.click(more);

        expect(queryByText(viewLess)).toBeInTheDocument();

        entries = getAllByRole('listitem');
        expect(entries).toHaveLength(6);
        expect(entries[5]).toHaveTextContent('condition6 (6)');
    });

    it('should hide conditions on clicking view less', async () => {
        const user = userEvent.setup();

        let moreConditions = [...conditions, { name: 'condition6', id: '6' }];
        expect(moreConditions).toHaveLength(6);

        const { getAllByRole, getByText, queryByText } = render(<ConditionsDisplay conditions={moreConditions} />);
        // Initial state shows 5 items and a `1 more...` button
        expect(getAllByRole('listitem')).toHaveLength(5);
        const more = getByText('1 more...');
        expect(queryByText(viewLess)).not.toBeInTheDocument();

        // Clicking 1 more, shows all 6 entries
        await user.click(more);
        expect(queryByText(viewLess)).toBeInTheDocument();
        expect(getAllByRole('listitem')).toHaveLength(6);

        // clicking view less, reverts to original state
        await user.click(getByText(viewLess));
        expect(getAllByRole('listitem')).toHaveLength(5);
        expect(getByText('1 more...')).toBeInTheDocument();
        expect(queryByText(viewLess)).not.toBeInTheDocument();
    });
});

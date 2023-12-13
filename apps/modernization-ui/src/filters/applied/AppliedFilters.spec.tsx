import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { DateFilter, DateRangeFilter, Filter, MultiValueFilter, SingleValueFilter } from 'filters';
import { AppliedFilters } from './AppliedFilters';

describe('when there are applied filters', () => {
    it('should display a single value filter when applied', () => {
        const { getByText } = render(<AppliedFilters label={'Testing'} filters={[]} onRemove={jest.fn} />);

        const title = getByText('All Testing');

        expect(title).toHaveClass('title');
    });

    it('should remove the filter when X clicked', async () => {
        const filters: Filter[] = [
            {
                id: 'single-value-identifier',
                property: { value: 'single-value', name: 'Single Value', type: 'value' },
                operator: { name: 'Starts with', value: 'STARTS_WITH' },
                value: 'prefix-value'
            },
            {
                id: 'multi-value-identifier',
                property: { value: 'multi-value', name: 'Multi Value', type: 'value' },
                operator: { name: 'equals', value: 'EQUALS' },
                values: ['value-one', 'value-two']
            }
        ];

        const onRemove = jest.fn();

        const { getAllByRole } = render(<AppliedFilters label={'Testing'} filters={filters} onRemove={onRemove} />);

        const close = getAllByRole('button', {
            name: /close/i
        });

        expect(close).toHaveLength(2);

        await userEvent.click(close[0]);

        expect(onRemove).toHaveBeenCalledWith('single-value-identifier');
    });

    it('should display a single value filter when applied', () => {
        const filter: SingleValueFilter = {
            id: 'single-value-identifier',
            property: { value: 'single-value', name: 'Single Value', type: 'value' },
            operator: { name: 'Starts with', value: 'STARTS_WITH' },
            value: 'prefix-value'
        };

        const { getByText } = render(<AppliedFilters label={'Testing'} filters={[filter]} onRemove={jest.fn} />);

        expect(getByText('Single Value Starts with prefix-value')).toBeInTheDocument();
    });

    it('should display a multi value filter when applied', () => {
        const filter: MultiValueFilter = {
            id: 'multi-value-identifier',
            property: { value: 'multi-value', name: 'Multi Value', type: 'value' },
            operator: { name: 'equals', value: 'EQUALS' },
            values: ['value-one', 'value-two']
        };

        const { getByText } = render(<AppliedFilters label={'Testing'} filters={[filter]} onRemove={jest.fn} />);

        expect(getByText('Multi Value equals value-one OR value-two')).toBeInTheDocument();
    });

    it('should display a date filter when applied', () => {
        const filter: DateFilter = {
            id: 'date-identifier',
            property: { value: 'date-period', name: 'Date Period', type: 'date' },
            operator: { name: 'today', value: 'TODAY' }
        };

        const { getByText } = render(<AppliedFilters label={'Testing'} filters={[filter]} onRemove={jest.fn} />);

        expect(getByText('Date Period today')).toBeInTheDocument();
    });

    it('should display a date range filter when applied', () => {
        const filter: DateRangeFilter = {
            id: 'date-range-identifier',
            property: { value: 'date-range', name: 'Date Range', type: 'date' },
            operator: { name: 'between', value: 'BETWEEN' },
            after: '12/01/2023',
            before: '12/29/2023'
        };

        const { getByText } = render(<AppliedFilters label={'Testing'} filters={[filter]} onRemove={jest.fn} />);

        expect(getByText('Date Range between 12/01/2023 and 12/29/2023'));
    });
});

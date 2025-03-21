import { render } from '@testing-library/react';
import { ConditionSearch } from './ConditionSearch';
import { MemoryRouter } from 'react-router';
import { Condition } from 'apps/page-builder/generated';

const mockCondition: Condition = {
    coinfectionGroup: 'coinfection',
    conditionFamily: 'conditionFamily',
    id: 'id',
    name: 'conditionName',
    nndInd: 'T',
    page: undefined,
    programArea: 'program area',
    status: 'A'
};

const search = jest.fn();
const reset = jest.fn();

const mockUsConditionSearch = {
    search: search,
    response: { content: [mockCondition] },
    error: undefined,
    isLoading: false,
    reset
};

jest.mock('./useConditionSearch', () => ({
    ...jest.requireActual('./useConditionSearch'),
    useConditionSearch: () => mockUsConditionSearch
}));

describe('ConditionSearch', () => {
    const onConditionSelect = jest.fn();
    const onCancel = jest.fn();
    const onCreateNew = jest.fn();

    it('should have a modal heading', () => {
        const { getByText } = render(
            <MemoryRouter>
                <ConditionSearch onConditionSelect={onConditionSelect} onCancel={onCancel} onCreateNew={onCreateNew} />
            </MemoryRouter>
        );
        expect(getByText('Search and add condition(s)')).toBeInTheDocument();
    });

    it('should have a title', () => {
        const { getByText } = render(
            <MemoryRouter>
                <ConditionSearch onConditionSelect={onConditionSelect} onCancel={onCancel} onCreateNew={onCreateNew} />
            </MemoryRouter>
        );
        expect(getByText('You can search for existing condition(s) or create a new one.')).toBeInTheDocument();
    });

    it('should have expected buttons', () => {
        const { getByText } = render(
            <MemoryRouter>
                <ConditionSearch onConditionSelect={onConditionSelect} onCancel={onCancel} onCreateNew={onCreateNew} />
            </MemoryRouter>
        );
        const createBtn = getByText('Create new condition');
        expect(createBtn).toBeInTheDocument();
        expect(createBtn.nodeName).toBe('BUTTON');
        expect(createBtn).not.toHaveAttribute('disabled');

        const cancelBtn = getByText('Cancel');
        expect(cancelBtn).toBeInTheDocument();
        expect(cancelBtn.nodeName).toBe('BUTTON');
        expect(cancelBtn).not.toHaveAttribute('disabled');

        const addBtn = getByText('Add conditions');
        expect(addBtn).toBeInTheDocument();
        expect(addBtn.nodeName).toBe('BUTTON');
        expect(addBtn).toHaveAttribute('disabled');
    });

    it('should pass data to the table', () => {
        const { getAllByRole } = render(
            <MemoryRouter>
                <ConditionSearch onConditionSelect={onConditionSelect} onCancel={onCancel} onCreateNew={onCreateNew} />
            </MemoryRouter>
        );

        const cells = getAllByRole('cell');
        expect(cells[1]).toHaveTextContent('conditionName');
        expect(cells[2]).toHaveTextContent('id');
        expect(cells[3]).toHaveTextContent('program area');
        expect(cells[4]).toHaveTextContent('conditionFamily');
        expect(cells[5]).toHaveTextContent('coinfection');
        expect(cells[6]).toHaveTextContent('T');
        expect(cells[7]).toHaveTextContent('No Data');
        expect(cells[8]).toHaveTextContent('Active');
    });

    it('should search with appropriate page size', () => {
        render(
            <MemoryRouter>
                <ConditionSearch onConditionSelect={onConditionSelect} onCancel={onCancel} onCreateNew={onCreateNew} />
            </MemoryRouter>
        );

        expect(search).toBeCalledWith({ page: 0, pageSize: 10, sort: undefined });
    });

    it('should have aria labels', () => {
        const { getByText } = render(
            <MemoryRouter>
                <ConditionSearch onConditionSelect={onConditionSelect} onCancel={onCancel} onCreateNew={onCreateNew} />
            </MemoryRouter>
        );

        expect(getByText('Create new condition')).toHaveAttribute('aria-label', 'Create new condition');
        expect(getByText('Add conditions')).toHaveAttribute('aria-label', 'Add conditions');
        expect(getByText('Cancel')).toHaveAttribute('aria-label', 'Cancel');
    });
});

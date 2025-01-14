import { ReactNode } from 'react';
import { act, renderHook } from '@testing-library/react-hooks';
import { SearchResultSettings, useSearchResults } from './useSearchResults';
import { Page } from 'page';
import { SearchResultDisplayProvider } from './useSearchResultDisplay';

let mockCriteria: Criteria | undefined = undefined;
const mockClear = jest.fn();
const mockChange = jest.fn();

jest.mock('./useSearchCriteria', () => ({
    useSearchCriteria: () => ({
        criteria: mockCriteria,
        clear: mockClear,
        change: mockChange
    })
}));

const { Status } = jest.requireActual('page');

const mockPage: Page = {
    status: Status.Ready,
    pageSize: 5,
    total: 7,
    current: 11
};

const mockFirstPage = jest.fn();
const mockReload = jest.fn();
const mockRequest = jest.fn();
const mockReady = jest.fn();
const mockResize = jest.fn();
const mockPageReset = jest.fn();

jest.mock('page', () => ({
    usePage: () => ({
        page: mockPage,
        firstPage: mockFirstPage,
        reload: mockReload,
        request: mockRequest,
        ready: mockReady,
        resize: mockResize,
        reset: mockPageReset
    })
}));

const { Direction } = jest.requireActual('sorting');

let mockSortProperty: string | undefined = undefined;
let mockSortDirection: any | undefined = undefined;
const mockSortReset = jest.fn();
const mockSortBy = jest.fn();
const mockToggle = jest.fn();

jest.mock('sorting', () => ({
    useSorting: () => ({
        property: mockSortProperty,
        direction: mockSortDirection,
        reset: mockSortReset,
        sortBy: mockSortBy,
        toggle: mockToggle
    })
}));

type Criteria = { name: string };
type APIParameters = { search: string };
type Result = { label: string; value: string };

const wrapper = ({ children }: { children: ReactNode }) => (
    <SearchResultDisplayProvider>{children}</SearchResultDisplayProvider>
);

const setup = (props?: Partial<SearchResultSettings<Criteria, APIParameters, Result>>) => {
    const defaultTransformer = (criteria: Criteria) => ({ search: criteria.name });
    const defaultResultResolver = () => Promise.resolve({ total: 0, content: [], page: 0, size: 7 });
    const defaultTermResolver = () => [
        { source: 'default-source', title: 'title-value', name: 'name-value', value: 'value-value', partial: false }
    ];
    //
    const transformer = props?.transformer ?? defaultTransformer;
    const resultResolver = props?.resultResolver ?? defaultResultResolver;
    const termResolver = props?.termResolver ?? defaultTermResolver;

    return renderHook(
        () => useSearchResults<Criteria, APIParameters, Result>({ transformer, resultResolver, termResolver }),
        {
            wrapper
        }
    );
};

describe('when searching using useSearchResults', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('should default to waiting without any results', () => {
        const { result } = setup();

        expect(result.current.results).toEqual(expect.objectContaining({ total: 0 }));
        expect(result.current.status).toEqual('waiting');
    });

    it('should change to status to loading after invoking a search', async () => {
        const { result } = setup();

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        expect(result.current.status).toEqual('loading');
    });

    it('should change to status to no-input when terms cannot be resolved', async () => {
        const { result } = setup({ termResolver: () => [] });

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        expect(result.current.status).toEqual('no-input');
    });

    it('should change to status to waiting when reset before searching', async () => {
        const { result } = setup();

        act(() => {
            result.current.reset();
        });

        expect(result.current.status).toEqual('waiting');
    });

    it('should change to status to waiting when reset after completed', async () => {
        const { result } = setup();

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        act(() => {
            result.current.reset();
        });

        expect(result.current.status).toEqual('waiting');
    });

    it('should change to status to error when search produces an error', async () => {
        const { result } = setup({
            resultResolver: () => Promise.reject(new Error('there has been an error'))
        });

        mockCriteria = { name: 'name-value' };

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        expect(result.current.status).toEqual('error');
    });

    it('should change to status to waiting when reset after error', async () => {
        const { result } = setup({ resultResolver: () => Promise.reject(new Error()) });

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        act(() => {
            result.current.reset();
        });

        expect(result.current.status).toEqual('waiting');
    });

    it('should change the criteria when searching', async () => {
        const transformer = jest.fn(() => ({ search: 'name-value' }));

        const terms = [
            { source: 'mock-source', title: 'Mocked Title', name: 'Mocked Name', value: 'mock', partial: false }
        ];

        const termResolver = jest.fn(() => terms);

        const { result } = setup({ transformer, termResolver });

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        expect(transformer).toHaveBeenCalledWith({ name: 'name-value' });

        expect(termResolver).toHaveBeenCalledWith({ name: 'name-value' });

        expect(mockChange).toHaveBeenCalledWith(expect.objectContaining({ name: 'name-value' }));
    });

    it('should use the request first page when criteria changes', async () => {
        mockCriteria = { name: 'mocked' };

        const resultResolver = jest.fn();
        resultResolver.mockResolvedValue({ total: 2, content: [], page: 3, size: 5 });

        mockPage.current = 227;
        mockPage.pageSize = 307;

        const { result, rerender } = setup({ resultResolver });

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        rerender();

        expect(resultResolver).toBeCalledWith(
            expect.objectContaining({ page: expect.objectContaining({ number: 1, size: 307 }) })
        );
    });

    it('should use the request first page when sort property changes', async () => {
        const resultResolver = jest.fn();
        resultResolver.mockResolvedValue({ total: 2, content: [], page: 3, size: 5 });

        mockPage.current = 227;
        mockPage.pageSize = 307;

        mockSortProperty = 'property-value';

        const { result, rerender } = setup({ resultResolver });

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        rerender();

        expect(resultResolver).toBeCalledWith(
            expect.objectContaining({ page: expect.objectContaining({ number: 1, size: 307 }) })
        );
    });

    it('should use the request first page when sort direction changes', async () => {
        const resultResolver = jest.fn();
        resultResolver.mockResolvedValue({ total: 2, content: [], page: 3, size: 5 });

        mockPage.current = 227;
        mockPage.pageSize = 307;

        mockSortDirection = Direction.Ascending;

        const { result, rerender } = setup({ resultResolver });

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        rerender();

        expect(resultResolver).toBeCalledWith(
            expect.objectContaining({ page: expect.objectContaining({ number: 1, size: 307 }) })
        );
    });
});

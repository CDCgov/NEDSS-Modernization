import { vi } from 'vitest';
import { ReactNode, act } from 'react';
import { renderHook, waitFor } from '@testing-library/react';
import { SearchResultSettings, useSearchResults } from './useSearchResults';
import { Page } from 'pagination';
import { SearchResultDisplayProvider } from './useSearchResultDisplay';
import { Filter } from 'design-system/filter';

let mockCriteria: Criteria | undefined = undefined;
const mockClearCriteria = jest.fn();
const mockChangeCriteria = jest.fn();

vi.mock('./useSearchCriteria', () => ({
    useSearchCriteria: () => ({
        criteria: mockCriteria,
        clear: mockClearCriteria,
        change: mockChangeCriteria
    })
}));

const { Status: PageStatus } = (await vi.importActual('pagination')) as any;

const mockPage: Page = {
    status: PageStatus.Ready,
    pageSize: 5,
    total: 7,
    current: 11
};

const mockFirstPage = vi.fn();
const mockReload = vi.fn();
const mockRequest = vi.fn();
const mockReady = vi.fn();
const mockResize = vi.fn();
const mockPageReset = vi.fn();

vi.mock('pagination', () => ({
    usePagination: () => ({
        page: mockPage,
        firstPage: mockFirstPage,
        reload: mockReload,
        request: mockRequest,
        ready: mockReady,
        resize: mockResize,
        reset: mockPageReset
    })
}));

const { Direction } = (await vi.importActual('libs/sorting')) as any;

let mockSortProperty: string | undefined = undefined;
let mockSortDirection: any | undefined = undefined;
const mockSortReset = vi.fn();
const mockSortBy = vi.fn();
const mockToggle = vi.fn();

vi.mock('libs/sorting', () => ({
    useSorting: () => ({
        property: mockSortProperty,
        direction: mockSortDirection,
        reset: mockSortReset,
        sortBy: mockSortBy,
        toggle: mockToggle
    })
}));

let mockFilterActive: boolean = false;
let mockFilterObject: Filter | undefined = undefined;

jest.mock('design-system/filter', () => ({
    maybeUseFilter: () => ({
        active: mockFilterActive,
        filter: mockFilterObject,
        show: jest.fn(),
        hide: jest.fn(),
        toggle: jest.fn(),
        valueOf: jest.fn(),
        apply: jest.fn(),
        clear: jest.fn(),
        clearAll: jest.fn(),
        reset: jest.fn(),
        add: jest.fn(),
        pendingFilter: undefined
    })
}));

type Criteria = { name: string; filteredName?: string };
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
        mockPage.status = PageStatus.Ready;
        mockPage.pageSize = 10;
        mockPage.total = 1;
        mockPage.current = 1;
        mockSortProperty = undefined;
        mockSortDirection = undefined;
        mockFilterActive = false;
        mockFilterObject = undefined;
        mockCriteria = undefined;
    });

    it('should default to waiting without any results', () => {
        const { result } = setup();

        expect(result.current.results).toEqual(expect.objectContaining({ total: 0 }));
        expect(result.current.status).toEqual('waiting');
    });

    it('should change to status to loading after invoking a search', () => {
        const { result } = setup();

        act(() => {
            result.current.search({ name: 'name-value' });
        });

        expect(result.current.status).toEqual('loading');
    });

    it('should change to status to no-input when terms cannot be resolved', () => {
        const { result } = setup({ termResolver: () => [] });

        act(() => {
            result.current.search({ name: 'name-value' });
        });

        expect(result.current.status).toEqual('no-input');
    });

    it('should change to status to waiting when reset before searching', () => {
        const { result } = setup();

        act(() => {
            result.current.reset();
        });

        expect(result.current.status).toEqual('waiting');
    });

    it('should change to status to waiting when reset after completed', () => {
        const { result } = setup();

        act(() => {
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

        act(() => {
            result.current.search({ name: 'name-value' });
        });

        await waitFor(() => expect(result.current.status).toEqual('error'));
    });

    it('should change to status to waiting when reset after error', async () => {
        const { result } = setup({ resultResolver: () => Promise.reject(new Error()) });

        act(() => {
            result.current.search({ name: 'name-value' });
        });

        act(() => {
            result.current.reset();
        });

        await waitFor(() => expect(result.current.status).toEqual('waiting'));
    });

    it('should change the criteria when searching', async () => {
        const transformer = jest.fn(() => ({ search: 'name-value' }));

        const terms = [
            { source: 'mock-source', title: 'Mocked Title', name: 'Mocked Name', value: 'mock', partial: false }
        ];

        const termResolver = jest.fn(() => terms);

        const { result } = setup({ transformer, termResolver });

        act(() => {
            result.current.search({ name: 'name-value' });
        });

        await waitFor(() => {
            expect(transformer).toHaveBeenCalledWith({ name: 'name-value' });

            expect(termResolver).toHaveBeenCalledWith({ name: 'name-value' });

            expect(mockChangeCriteria).toHaveBeenCalledWith(expect.objectContaining({ name: 'name-value' }));
        });
    });

    it('should use the request first page when criteria changes', async () => {
        mockCriteria = { name: 'mocked' };

        const resultResolver = jest.fn();
        resultResolver.mockResolvedValue({ total: 2, content: [], page: 3, size: 5 });

        mockPage.current = 227;
        mockPage.pageSize = 307;

        const { result } = setup({ resultResolver });

        act(() => {
            result.current.search({ name: 'name-value' });
        });

        await waitFor(() =>
            expect(resultResolver).toBeCalledWith(
                expect.objectContaining({ page: expect.objectContaining({ number: 1, size: 307 }) })
            )
        );
    });

    it('should use the request first page when sort property changes', async () => {
        const resultResolver = jest.fn();
        resultResolver.mockResolvedValue({ total: 2, content: [], page: 3, size: 5 });
        mockCriteria = { name: 'name-value' };
        mockPage.current = 227;
        mockPage.pageSize = 307;

        mockSortProperty = 'property-value';

        const { result } = setup({ resultResolver });

        act(() => {
            result.current.search({ name: 'name-value' });
        });

        await waitFor(() =>
            expect(resultResolver).toBeCalledWith(
                expect.objectContaining({ page: expect.objectContaining({ number: 1, size: 307 }) })
            )
        );
    });

    it('should use the request first page when sort direction changes', async () => {
        const resultResolver = jest.fn();
        resultResolver.mockResolvedValue({ total: 2, content: [], page: 3, size: 5 });
        mockCriteria = { name: 'name-value' };
        mockPage.current = 227;
        mockPage.pageSize = 307;

        mockSortDirection = Direction.Ascending;

        const { result } = setup({ resultResolver });

        act(() => {
            result.current.search({ name: 'name-value' });
        });

        await waitFor(() =>
            expect(resultResolver).toBeCalledWith(
                expect.objectContaining({ page: expect.objectContaining({ number: 1, size: 307 }) })
            )
        );
    });

    it('should return a results object with separate total and filteredTotal properties when filter applied', async () => {
        const resultResolver = jest.fn();
        resultResolver.mockResolvedValue({ total: 13, content: [], page: 0, size: 10 });

        const { result } = setup({ resultResolver });

        act(() => {
            // search reverts to requesting state
            result.current.search({ name: 'name-value' });
            // mock updating criteria to force a completion
            mockCriteria = { name: 'name-value' };
        });

        await waitFor(() => {
            expect(result.current.status).toBe('completed');
        });

        await waitFor(() => {
            expect(result.current.results).toEqual(
                expect.objectContaining({
                    total: 13,
                    filteredTotal: undefined,
                    content: [],
                    page: 1,
                    size: 10
                })
            );
        });

        // Mocking the filter interaction to simulate a filter being applied
        mockFilterActive = true;
        mockFilterObject = { someValue: '123' };
        resultResolver.mockResolvedValue({ total: 9, content: [], page: 0, size: 10 });

        act(() => {
            result.current.search({ name: 'name-value', filteredName: 'filtered-name-value' });
            mockCriteria = { name: 'name-value', filteredName: 'filtered-name-value' };
        });

        await waitFor(() => {
            expect(result.current.status).toBe('completed');
        });

        await waitFor(() => {
            expect(result.current.results).toEqual(
                expect.objectContaining({
                    total: 13,
                    filteredTotal: 9,
                    content: [],
                    page: 1,
                    size: 10
                })
            );
        });
    });
});

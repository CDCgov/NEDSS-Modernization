import { ReactNode } from 'react';
import { MemoryRouter } from 'react-router-dom';
import { act, renderHook } from '@testing-library/react-hooks';
import { SearchResultSettings, useSearchResults } from './useSearchResults';
import { SearchPageProvider } from './SearchPage';

const mockClear = jest.fn();
const mockChange = jest.fn();

jest.mock('./useSearchCriteria', () => ({
    useSearchCritiera: () => ({
        clear: mockClear,
        change: mockChange
    })
}));

type Criteria = { name: string };
type APIParameters = { search: string };
type Result = { label: string; value: string };

const wrapper = ({ children }: { children: ReactNode }) => (
    <MemoryRouter>
        <SearchPageProvider>{children}</SearchPageProvider>
    </MemoryRouter>
);

const setup = (props?: Partial<SearchResultSettings<Criteria, APIParameters, Result>>) => {
    const defaultTransformer = (criteria: Criteria) => ({ search: criteria.name });
    const defaultResultResolver = () => Promise.resolve({ total: 0, content: [], page: 0 });
    const defaultTermResolver = () => [];
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
    it('should default to waiting without any results', () => {
        const { result } = setup();

        expect(result.current.results).toEqual(expect.objectContaining({ total: 0 }));
        expect(result.current.status).toEqual('waiting');
    });

    it('should change to status to completed when search results have been resolved', async () => {
        const { result } = setup();

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
        const { result } = setup({ resultResolver: () => Promise.reject(new Error('there has been an error')) });

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        expect(result.current.status).toEqual('no-input');
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

    it('should use change the criteria when searching', async () => {
        const transformer = jest.fn(() => ({ search: 'name-value' }));

        const terms = [{ source: 'mock-source', title: 'Mocked Title', name: 'Mocked Name', value: 'mock' }];

        const termResolver = jest.fn(() => terms);

        const { result } = setup({ transformer, termResolver });

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        expect(transformer).toHaveBeenCalledWith({ name: 'name-value' });

        expect(termResolver).toHaveBeenCalledWith({ name: 'name-value' });

        expect(mockChange).toHaveBeenCalledWith(expect.objectContaining({ name: 'name-value' }));
    });
});

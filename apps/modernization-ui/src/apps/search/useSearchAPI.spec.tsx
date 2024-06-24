import { ReactNode } from 'react';
import { MemoryRouter } from 'react-router-dom';
import { act, renderHook } from '@testing-library/react-hooks';
import { Settings, useSearchAPI } from './useSearchAPI';
import { SearchProvider } from './useSearch';

type Criteria = { name: string };
type APIParameters = { search: string };
type Result = { label: string; value: string };

const wrapper = ({ children }: { children: ReactNode }) => (
    <MemoryRouter>
        <SearchProvider>{children}</SearchProvider>
    </MemoryRouter>
);

const setup = (props?: Partial<Settings<Criteria, APIParameters, Result>>) => {
    const defaultTransformer = (criteria: Criteria) => ({ search: criteria.name });
    const defaultResultResolver = () => Promise.resolve({ total: 0, content: [], page: 0 });
    const defaultTermResolver = () => [];

    const transformer = props?.transformer ?? defaultTransformer;
    const resultResolver = props?.resultResolver ?? defaultResultResolver;
    const termResolver = props?.termResolver ?? defaultTermResolver;

    return renderHook(
        () => useSearchAPI<Criteria, APIParameters, Result>({ transformer, resultResolver, termResolver }),
        {
            wrapper
        }
    );
};

describe('when searching using useSearchAPI', () => {
    it('should default to waiting without any results', () => {
        const { result } = setup();

        expect(result.current.results).toBeUndefined();
        expect(result.current.status).toEqual('waiting');
    });

    it('should change to status to completed when search results have been resolved', async () => {
        const { result } = setup();

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        expect(result.current.status).toEqual('completed');
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

    it('should use the resolvers for searching', async () => {
        const transformer = jest.fn(() => ({ search: 'name-value' }));

        const terms = [{ source: 'mock-source', name: 'Mocked', value: 'mock' }];

        const termResolver = jest.fn(() => terms);

        const resovled = { total: 0, content: [], page: 0 };
        const resultResolver = jest.fn(() => Promise.resolve(resovled));

        const { result } = setup({ transformer, resultResolver, termResolver });

        await act(async () => {
            result.current.search({ name: 'name-value' });
        });

        expect(transformer).toHaveBeenCalledWith({ name: 'name-value' });

        expect(termResolver).toHaveBeenCalledWith({ name: 'name-value' });

        expect(resultResolver).toHaveBeenCalledWith(
            expect.objectContaining({ search: 'name-value' }),
            expect.anything(),
            undefined
        );

        expect(result.current.results?.terms).toEqual(
            expect.arrayContaining([{ source: 'mock-source', name: 'Mocked', value: 'mock' }])
        );
    });
});

import { Direction } from 'sorting';
import { createContext, Dispatch, SetStateAction } from 'react';
import { ValueSet } from '../generated';

export interface ContextData {
    filter: any;
    setFilter: (filter: any) => void;
    searchQuery: string;
    setSearchQuery: (query: string) => void;
    currentPage: number;
    setCurrentPage: (page: number) => void;
    sortBy: string;
    setSortBy: (name: string) => void;
    sortDirection: string;
    setSortDirection: (direction: string) => void;
    pageSize: number;
    setPageSize: Dispatch<SetStateAction<number>>;
    isLoading: boolean;
    setIsLoading: (status: boolean) => void;
    handleSort?: (name: string, direction: Direction) => void;
    searchValueSet?: ValueSet;
    setSearchValueSet?: (valueSet: ValueSet) => void;
}

const noop: ContextData & { type: 'noop' } = {
    type: 'noop',
    filter: '',
    setFilter: () => {},
    searchQuery: '',
    setSearchQuery: () => {},
    currentPage: 1,
    setCurrentPage: () => {},
    sortBy: 'name',
    setSortBy: () => {},
    sortDirection: 'asc',
    setSortDirection: () => {},
    pageSize: 10,
    setPageSize: () => {},
    isLoading: false,
    setIsLoading: () => {}
};

export const NoopContext = createContext<ContextData>(noop);

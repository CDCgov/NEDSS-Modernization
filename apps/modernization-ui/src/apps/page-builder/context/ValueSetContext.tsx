/* eslint-disable camelcase */
import { createContext, useState, Dispatch, SetStateAction } from 'react';

interface ValueSetsContextData {
    filter: any;
    setFilter: (filter: any) => void;
    searchQuery: string;
    setSearchQuery: (query: string) => void;
    currentPage: number;
    setCurrentPage?: (page: number) => void;
    sortBy: string;
    setSortBy: (name: string) => void;
    sortDirection: string;
    setSortDirection: (direction: string) => void;
    pageSize: number;
    setPageSize: Dispatch<SetStateAction<number>>;
    isLoading: boolean;
    setIsLoading: (status: boolean) => void;
}

const valueSetsDefaultValue: ValueSetsContextData = {
    filter: {},
    setFilter: () => {},
    searchQuery: '',
    setSearchQuery: () => {},
    currentPage: 1,
    setCurrentPage: () => {},
    sortBy: '',
    setSortBy: () => {},
    sortDirection: '',
    setSortDirection: () => {},
    pageSize: 10,
    setPageSize: () => {},
    isLoading: false,
    setIsLoading: () => {}
};

export const ValueSetsContext = createContext<ValueSetsContextData>(valueSetsDefaultValue);

export const ValueSetsProvider = ({ children }: any) => {
    const [searchQuery, setSearchQuery] = useState(valueSetsDefaultValue.searchQuery);
    const [sortDirection, setSortDirection] = useState(valueSetsDefaultValue.sortDirection);
    const [currentPage, setCurrentPage] = useState(valueSetsDefaultValue.currentPage);
    const [sortBy, setSortBy] = useState(valueSetsDefaultValue.sortBy);
    const [pageSize, setPageSize] = useState(valueSetsDefaultValue.pageSize);
    const [isLoading, setIsLoading] = useState(false);
    const [filter, setFilter] = useState(valueSetsDefaultValue.filter);

    return (
        <ValueSetsContext.Provider
            value={{
                filter,
                setFilter,
                currentPage,
                sortBy,
                setSortBy,
                sortDirection,
                setSortDirection,
                searchQuery,
                setSearchQuery,
                setCurrentPage,
                pageSize,
                setPageSize,
                isLoading,
                setIsLoading
            }}>
            {children}
        </ValueSetsContext.Provider>
    );
};

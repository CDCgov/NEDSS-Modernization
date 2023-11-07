/* eslint-disable camelcase */
import { createContext, useState } from 'react';
import { ContextData } from './contextData';

const valueSetsDefaultValue: ContextData = {
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

export const ValueSetsContext = createContext<ContextData>(valueSetsDefaultValue);

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

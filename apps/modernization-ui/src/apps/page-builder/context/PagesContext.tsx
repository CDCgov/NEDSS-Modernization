/* eslint-disable camelcase */
import { createContext, useState } from 'react';
import { ContextData } from './contextData';

const pagesContextDefaultValue: ContextData = {
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

export const PagesContext = createContext<ContextData>(pagesContextDefaultValue);

export const PagesProvider = ({ children }: any) => {
    const [filter, setFilter] = useState(pagesContextDefaultValue.filter);
    const [searchQuery, setSearchQuery] = useState(pagesContextDefaultValue.searchQuery);
    const [currentPage, setCurrentPage] = useState(pagesContextDefaultValue.currentPage);
    const [sortBy, setSortBy] = useState(pagesContextDefaultValue.sortBy);
    const [sortDirection, setSortDirection] = useState(pagesContextDefaultValue.sortDirection);
    const [pageSize, setPageSize] = useState(pagesContextDefaultValue.pageSize);
    const [isLoading, setIsLoading] = useState(false);

    return (
        <PagesContext.Provider
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
        </PagesContext.Provider>
    );
};

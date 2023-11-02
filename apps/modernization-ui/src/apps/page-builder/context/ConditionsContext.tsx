/* eslint-disable camelcase */
import { createContext, useState } from 'react';
import { ContextData } from './contextData';

const conditionContextDefaultValue: ContextData = {
    filter: {},
    setFilter: () => {},
    searchQuery: '',
    setSearchQuery: () => {},
    currentPage: 1,
    setCurrentPage: () => {},
    sortBy: 'conditionShortNm',
    setSortBy: () => {},
    sortDirection: 'asc',
    setSortDirection: () => {},
    pageSize: 10,
    setPageSize: () => {},
    isLoading: false,
    setIsLoading: () => {}
};

export const ConditionsContext = createContext<ContextData>(conditionContextDefaultValue);

export const ConditionProvider = ({ children }: any) => {
    const [searchQuery, setSearchQuery] = useState(conditionContextDefaultValue.searchQuery);
    const [sortDirection, setSortDirection] = useState(conditionContextDefaultValue.sortDirection);
    const [currentPage, setCurrentPage] = useState(conditionContextDefaultValue.currentPage);
    const [sortBy, setSortBy] = useState(conditionContextDefaultValue.sortBy);
    const [pageSize, setPageSize] = useState(conditionContextDefaultValue.pageSize);
    const [isLoading, setIsLoading] = useState(false);
    const [filter, setFilter] = useState(conditionContextDefaultValue.filter);

    return (
        <ConditionsContext.Provider
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
        </ConditionsContext.Provider>
    );
};

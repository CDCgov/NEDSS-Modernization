/* eslint-disable camelcase */
import { createContext, useState, Dispatch, SetStateAction } from 'react';

interface ConditionContextData {
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

const conditionContextDefaultValue: ConditionContextData = {
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

export const ConditionsContext = createContext<ConditionContextData>(conditionContextDefaultValue);

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

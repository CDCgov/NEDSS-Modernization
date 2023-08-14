/* eslint-disable camelcase */
import { createContext, useState, Dispatch, SetStateAction } from 'react';

interface PagesContextData {
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

const pagesContextDefaultValue: PagesContextData = {
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

export const PagesContext = createContext<PagesContextData>(pagesContextDefaultValue);

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
                currentPage,
                filter,
                setFilter,
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

/* eslint-disable camelcase */
import { createContext, useState, Dispatch, SetStateAction } from 'react';

interface BusinessRuleContextData {
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

const businessRuleContextDefaultValue: BusinessRuleContextData = {
    filter: {},
    setFilter: () => {},
    searchQuery: '',
    setSearchQuery: () => {},
    currentPage: 1,
    setCurrentPage: () => {},
    sortBy: 'ruleDescText',
    setSortBy: () => {},
    sortDirection: '',
    setSortDirection: () => {},
    pageSize: 10,
    setPageSize: () => {},
    isLoading: false,
    setIsLoading: () => {}
};

export const BusinessRuleContext = createContext<BusinessRuleContextData>(businessRuleContextDefaultValue);

export const BusinessRuleProvider = ({ children }: any) => {
    const [searchQuery, setSearchQuery] = useState(businessRuleContextDefaultValue.searchQuery);
    const [sortDirection, setSortDirection] = useState(businessRuleContextDefaultValue.sortDirection);
    const [currentPage, setCurrentPage] = useState(businessRuleContextDefaultValue.currentPage);
    const [sortBy, setSortBy] = useState(businessRuleContextDefaultValue.sortBy);
    const [pageSize, setPageSize] = useState(businessRuleContextDefaultValue.pageSize);
    const [isLoading, setIsLoading] = useState(false);
    const [filter, setFilter] = useState(businessRuleContextDefaultValue.filter);
    return (
        <BusinessRuleContext.Provider
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
        </BusinessRuleContext.Provider>
    );
};

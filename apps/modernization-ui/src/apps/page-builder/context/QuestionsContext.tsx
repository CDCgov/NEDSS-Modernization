/* eslint-disable camelcase */
import { createContext, useState, Dispatch, SetStateAction } from 'react';

interface QuestionContextData {
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

const questionContextDefaultValue: QuestionContextData = {
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

export const QuestionsContext = createContext<QuestionContextData>(questionContextDefaultValue);

export const QuestionProvider = ({ children }: any) => {
    const [searchQuery, setSearchQuery] = useState(questionContextDefaultValue.searchQuery);
    const [sortDirection, setSortDirection] = useState(questionContextDefaultValue.sortDirection);
    const [currentPage, setCurrentPage] = useState(questionContextDefaultValue.currentPage);
    const [sortBy, setSortBy] = useState(questionContextDefaultValue.sortBy);
    const [pageSize, setPageSize] = useState(questionContextDefaultValue.pageSize);
    const [isLoading, setIsLoading] = useState(false);
    const [filter, setFilter] = useState(questionContextDefaultValue.filter);

    return (
        <QuestionsContext.Provider
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
        </QuestionsContext.Provider>
    );
};

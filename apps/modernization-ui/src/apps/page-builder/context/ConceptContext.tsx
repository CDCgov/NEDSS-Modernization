/* eslint-disable camelcase */
import { createContext, useState, Dispatch, SetStateAction } from 'react';

interface ConceptsContextData {
    selectedConcept: any;
    setSelectedConcept: (concept: any) => void;
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

const conceptsDefaultValue: ConceptsContextData = {
    selectedConcept: {},
    setSelectedConcept: () => {},
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

export const ConceptsContext = createContext<ConceptsContextData>(conceptsDefaultValue);

export const ConceptsProvider = ({ children }: any) => {
    const [searchQuery, setSearchQuery] = useState(conceptsDefaultValue.searchQuery);
    const [sortDirection, setSortDirection] = useState(conceptsDefaultValue.sortDirection);
    const [currentPage, setCurrentPage] = useState(conceptsDefaultValue.currentPage);
    const [sortBy, setSortBy] = useState(conceptsDefaultValue.sortBy);
    const [pageSize, setPageSize] = useState(conceptsDefaultValue.pageSize);
    const [isLoading, setIsLoading] = useState(false);
    const [selectedConcept, setSelectedConcept] = useState(conceptsDefaultValue.selectedConcept);

    return (
        <ConceptsContext.Provider
            value={{
                selectedConcept,
                setSelectedConcept,
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
        </ConceptsContext.Provider>
    );
};

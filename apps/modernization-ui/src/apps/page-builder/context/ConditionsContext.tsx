/* eslint-disable camelcase */
import { createContext, useState } from 'react';
import { ContextData } from './contextData';
import { Direction } from 'sorting';
import { Column } from 'apps/page-builder/constant/conditionLibrary';

const conditionContextDefaultValue: ContextData = {
    filter: {},
    setFilter: () => {},
    searchQuery: '',
    setSearchQuery: () => {},
    currentPage: 1,
    setCurrentPage: () => {},
    sortBy: 'conditionShortNm',
    setSortBy: () => {},
    sortDirection: Direction.Ascending,
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

    /*
     * Converts header and Direction to API compatible sort string such as "name,asc"
     */
    const toSortString = (name: string) => {
        if (name) {
            switch (name) {
                case Column.Condition:
                    setSortBy(`conditionShortNm`);
                    break;
                case Column.Code:
                    setSortBy(`id`);
                    break;
                case Column.ProgramArea:
                    setSortBy(`progAreaCd`);
                    break;
                case Column.ConditionFamily:
                    setSortBy(`familyCd`);
                    break;
                case Column.NND:
                    setSortBy(`nndInd`);
                    break;
                case Column.InvestigationPage:
                    setSortBy(`investigationFormCd`);
                    break;
                case Column.Status:
                    setSortBy(`statusCd`);
                    break;
                default:
                    setSortBy('conditionShortNm');
                    break;
            }
        }
    };

    const handleSort = (name: string, direction: Direction): void => {
        if (currentPage > 1 && setCurrentPage) {
            setCurrentPage(1);
        }

        if (direction === Direction.None) {
            toSortString(Column.Condition);
            setSortDirection(Direction.Ascending);
        } else {
            toSortString(name);
            setSortDirection(direction);
        }
    };

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
                setIsLoading,
                handleSort
            }}>
            {children}
        </ConditionsContext.Provider>
    );
};

import { SearchInteraction } from 'apps/search';
import { createContext, useContext, ReactNode, useState, useEffect } from 'react';
import { UseFormReturn } from 'react-hook-form';
import { isEmpty } from 'utils';

type FilterContextType = {
    activeFilter: boolean;
    appliedFilter: boolean;
    toggleFilter: () => void;
    form?: UseFormReturn<any>;
    applyFilter: () => void;
    resetFilter: () => void;
};

const FilterableContext = createContext<FilterContextType | undefined>(undefined);

export const FilterProvider = ({
    children,
    value: form,
    interaction
}: {
    children: ReactNode;
    value?: UseFormReturn<any>;
    interaction?: SearchInteraction<any>;
}) => {
    const [activeFilter, setActiveFilter] = useState(false);
    const [appliedFilter, setAppliedFilter] = useState(false);
    const filterValue = form?.watch('filter');

    const toggleFilter = () => setActiveFilter((prev) => !prev);

    const applyFilter = () => {
        interaction?.search();
        setAppliedFilter(true);
    };

    const resetFilter = () => {
        form?.setValue('filter', undefined);
        interaction?.search();
        setAppliedFilter(false);
    };

    useEffect(() => {
        !isEmpty(filterValue) && setActiveFilter(true);
    }, [filterValue]);

    return (
        <FilterableContext.Provider
            value={{ activeFilter, appliedFilter, toggleFilter, applyFilter, form, resetFilter }}>
            {children}
        </FilterableContext.Provider>
    );
};

export const useFilter = () => {
    const context = useContext(FilterableContext);
    if (!context) {
        throw new Error('useFilterContext must be used within a FilterProvider');
    }
    return context;
};

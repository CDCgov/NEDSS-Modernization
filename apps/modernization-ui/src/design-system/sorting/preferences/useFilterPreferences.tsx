import { SearchInteraction } from 'apps/search';
import { createContext, useContext, ReactNode, useState, useEffect } from 'react';
import { UseFormReturn } from 'react-hook-form';
import { isEmpty } from 'utils';

type FilterPreferencesContextType = {
    activeFilter: boolean;
    toggleFilter: () => void;
    form?: UseFormReturn<any>;
    applyFilter: () => void;
    resetFilter: () => void;
};

const FilterableContext = createContext<FilterPreferencesContextType | undefined>(undefined);

export const FilterPreferencesProvider = ({
    children,
    value: form,
    interaction
}: {
    children: ReactNode;
    value?: UseFormReturn<any>;
    interaction?: SearchInteraction<any>;
}) => {
    const [activeFilter, setActiveFilter] = useState(false);
    const filterValue = form?.watch('filter');

    const toggleFilter = () => setActiveFilter((prev) => !prev);

    const applyFilter = () => {
        interaction?.search();
    };

    const resetFilter = () => {
        form?.setValue('filter', undefined);
        interaction?.search();
    };

    useEffect(() => {
        !isEmpty(filterValue) && setActiveFilter(true);
    }, [filterValue]);

    return (
        <FilterableContext.Provider value={{ activeFilter, toggleFilter, applyFilter, form, resetFilter }}>
            {children}
        </FilterableContext.Provider>
    );
};

export const useFilterPreferences = () => {
    const context = useContext(FilterableContext);
    if (!context) {
        throw new Error('useFilterPreferencesContext must be used within a FilterPreferencesProvider');
    }
    return context;
};

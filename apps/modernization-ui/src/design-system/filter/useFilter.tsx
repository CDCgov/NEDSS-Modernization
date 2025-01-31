import { createContext, useContext, ReactNode, useState } from 'react';
import { Filter } from './FilterEntry';

type FilterContextType = {
    activeFilter: boolean;
    filterEntry: Filter | undefined;
    toggleFilter: () => void;
    onApply: (value: Filter) => void;
    onReset: () => void;
    setActiveFilter: (value: boolean) => void;
};

const FilterableContext = createContext<FilterContextType | undefined>(undefined);

export const FilterProvider = ({ children }: { children: ReactNode }) => {
    const [activeFilter, setActiveFilter] = useState(false);
    const [filterEntry, setFilterEntry] = useState<Filter>();

    const toggleFilter = () => setActiveFilter((prev) => !prev);

    const onApply = (value: Filter) => {
        setFilterEntry(value);
    };

    const onReset = () => {
        setFilterEntry(undefined);
    };

    return (
        <FilterableContext.Provider
            value={{ activeFilter, filterEntry, toggleFilter, onApply, onReset, setActiveFilter }}>
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

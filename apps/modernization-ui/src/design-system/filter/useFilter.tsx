import { createContext, useContext, ReactNode, useState, useCallback } from 'react';
import { Filter } from './filter';
import { exists } from 'utils';

type FilterInteraction = {
    active: boolean;
    filter: Filter | undefined;
    show: () => void;
    hide: () => void;
    toggle: () => void;
    valueOf: (id: string) => string | undefined;
    apply: (id: string, value: string) => void;
    clear: (id: string) => void;
    clearAll: () => void;
    reset: () => void;
};

const FilterableContext = createContext<FilterInteraction | undefined>(undefined);

type FilterProviderProps = { children: ReactNode };

const FilterProvider = ({ children }: FilterProviderProps) => {
    const [active, setActive] = useState(false);
    const [filter, setFilter] = useState<Filter>();

    const valueOf = useCallback((id: string) => (filter ? filter[id] : undefined), [filter]);
    const show = useCallback(() => setActive(true), [setActive]);
    const hide = useCallback(() => setActive(false), [setActive]);
    const toggle = useCallback(() => setActive((prev) => !prev), [setActive]);

    const apply = useCallback((id: string, value: string) => setFilter(withProperty(id, value)), [setFilter]);

    const clear = useCallback((id: string) => setFilter(withoutProperty(id)), [setFilter]);

    const clearAll = useCallback(() => setFilter(undefined), [setFilter]);
    const reset = useCallback(() => {
        setActive(false);
        setFilter(undefined);
    }, [setActive, setFilter]);

    return (
        <FilterableContext.Provider
            value={{ active, filter, show, hide, toggle, valueOf, apply, clear, clearAll, reset }}>
            {children}
        </FilterableContext.Provider>
    );
};

const withProperty =
    <T extends object>(id: keyof T, value: string) =>
    (current?: T) => ({ ...current, [id]: value });

const withoutProperty =
    <T,>(id: keyof T) =>
    (current?: T) => {
        if (current) {
            const next = { ...current };
            delete next[id];

            // if the last property is removed, return undefined
            return exists(next) ? next : undefined;
        }
    };

const useFilter = () => {
    const context = useContext(FilterableContext);
    if (!context) {
        throw new Error('useFilterContext must be used within a FilterProvider');
    }
    return context;
};

const maybeUseFilter = () => useContext(FilterableContext);

export { useFilter, maybeUseFilter, FilterProvider };
export type { FilterInteraction };

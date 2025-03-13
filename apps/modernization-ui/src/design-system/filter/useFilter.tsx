import { createContext, useContext, ReactNode, useState, useCallback } from 'react';
import { Filter } from './filter';
import { exists } from 'utils';

type FilterInteraction = {
    active: boolean;
    filter: Filter | undefined;
    pendingFilter: Filter | undefined;
    show: () => void;
    hide: () => void;
    toggle: () => void;
    valueOf: (id: string) => string | undefined;
    apply: () => void;
    /* adds a value to the pending filter */
    add: (id: string, value?: string) => void;
    /* removes the value of the given id from the filter */
    clear: (id: string) => void;
    /* removes all values from the filter */
    clearAll: () => void;
    /* removes all values from the filter and deactivates filtering */
    reset: () => void;
};

const FilterableContext = createContext<FilterInteraction | undefined>(undefined);

type FilterProviderProps = { children: ReactNode };

const FilterProvider = ({ children }: FilterProviderProps) => {
    const [active, setActive] = useState(false);
    const [filter, setFilter] = useState<Filter>();
    const [pendingFilter, setPendingFilter] = useState<Filter>();

    const valueOf = useCallback((id: string) => (pendingFilter ? pendingFilter[id] : undefined), [pendingFilter]);
    const show = useCallback(() => setActive(true), [setActive]);
    const hide = useCallback(() => setActive(false), [setActive]);
    const toggle = useCallback(() => setActive((prev) => !prev), [setActive]);

    const updateFilter = useCallback(
        (value: Filter | undefined) => {
            setFilter(value);
            setPendingFilter(value);
        },
        [setFilter, setPendingFilter]
    );

    const add = useCallback(
        (id: string, value?: string) => setPendingFilter(withProperty(id, value)),
        [setPendingFilter]
    );

    const apply = () => setFilter(pendingFilter);

    const clear = (id: string) => updateFilter(withoutProperty(id)(filter) as Filter | undefined);

    const clearAll = () => updateFilter(undefined);
    const reset = useCallback(() => {
        setActive(false);
        updateFilter(undefined);
    }, [setActive, updateFilter]);

    return (
        <FilterableContext.Provider
            value={{
                active,
                filter,
                show,
                hide,
                toggle,
                valueOf,
                apply,
                clear,
                clearAll,
                reset,
                add,
                pendingFilter
            }}>
            {children}
        </FilterableContext.Provider>
    );
};

const withProperty =
    <T extends object>(id: keyof T, value?: string) =>
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

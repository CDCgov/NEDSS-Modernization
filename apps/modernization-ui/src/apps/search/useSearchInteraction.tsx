import { ReactNode, createContext, useContext } from 'react';
import { Term } from './terms';

type SearchInteractionStatus =
    | 'waiting'
    | 'completed'
    | 'no-input'
    | 'error'
    | 'resetting'
    | 'initializing'
    | 'loading'
    | 'reloading';

type SearchResults<R> = {
    total: number;
    filteredTotal?: number;
    page: number;
    size: number;
    content: R[];
    terms: Term[];
};

type SearchInteraction<R> = {
    status: SearchInteractionStatus;
    results: SearchResults<R>;
    enabled: boolean;
    search: () => void;
    without: (term: Term) => void;
    clear: () => void;
};

const SearchInteractionContext = createContext<SearchInteraction<any> | undefined>(undefined);

type Props = {
    interaction: SearchInteraction<any>;
    children: ReactNode;
};

const SearchInteractionProvider = ({ interaction, children }: Props) => {
    return <SearchInteractionContext.Provider value={interaction}>{children}</SearchInteractionContext.Provider>;
};

const useSearchInteraction = <R,>(): SearchInteraction<R> => {
    const context = useContext(SearchInteractionContext);

    if (context === undefined) {
        throw new Error('useSearchInteraction must be used within a SearchInteractionProvider');
    }

    return context as SearchInteraction<R>;
};

export { useSearchInteraction, SearchInteractionProvider };
export type { SearchInteraction, SearchResults, SearchInteractionStatus };

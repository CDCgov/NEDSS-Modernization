import { createContext, ReactNode, useCallback, useContext } from 'react';
import { useNavigate } from 'react-router';

type SearchFromAddPatientContextType = {
    toSearch: (criteria: string | null) => void;
};

const SearchFromAddPatientContext = createContext<SearchFromAddPatientContextType | undefined>(undefined);

type SearchFromAddPatientProviderProps = {
    children: ReactNode;
};

function SearchFromAddPatientProvider({ children }: SearchFromAddPatientProviderProps) {
    const navigate = useNavigate();

    const toSearch = useCallback(
        (criteria: string | null) => {
            const search = criteria ? `?q=${criteria}` : undefined;

            navigate({ pathname: 'search/patients/', search });
        },
        [navigate]
    );

    const value = {
        toSearch
    };

    return <SearchFromAddPatientContext.Provider value={value}>{children}</SearchFromAddPatientContext.Provider>;
}

function useSearchFromAddPatient(): SearchFromAddPatientContextType {
    const context = useContext(SearchFromAddPatientContext);
    if (!context) {
        throw new Error('useSearchFromAddPatient must be used within a SearchFromAddPatientProvider');
    }
    return context;
}

export { SearchFromAddPatientProvider, useSearchFromAddPatient };

import { useSearchCriteriaEncrypted } from 'apps/search/useSearchCriteriaEncrypted';
import { createContext, ReactNode, useContext } from 'react';
import { useNavigate } from 'react-router-dom';

type SearchFromAddPatientContextType = {
    toSearch: () => void;
};

const SearchFromAddPatientContext = createContext<SearchFromAddPatientContextType | undefined>(undefined);

type SearchFromAddPatientProviderProps = {
    children: ReactNode;
};

function SearchFromAddPatientProvider({ children }: SearchFromAddPatientProviderProps) {
    const navigate = useNavigate();
    const { criteria } = useSearchCriteriaEncrypted();

    const toSearch = () => {
        console.log(criteria);
        if (criteria) {
            navigate(`search/patients/?q=${criteria}`);
        } else {
            navigate('/search/patient');
        }
    };

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

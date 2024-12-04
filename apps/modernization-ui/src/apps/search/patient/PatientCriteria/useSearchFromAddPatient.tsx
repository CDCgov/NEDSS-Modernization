import { createContext, ReactNode, useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { PatientCriteriaEntry } from '../criteria';

type SearchFromAddPatientContextType = {
    toSearch: () => void;
    toAddPatient: (criteria: PatientCriteriaEntry) => void;
};

const SearchFromAddPatientContext = createContext<SearchFromAddPatientContextType | undefined>(undefined);

type SearchFromAddPatientProviderProps = {
    children: ReactNode;
};

function SearchFromAddPatientProvider({ children }: SearchFromAddPatientProviderProps) {
    const navigate = useNavigate();
    const [criteria, setCriteria] = useState<PatientCriteriaEntry | undefined>(undefined);

    const toAddPatient = (criteria: PatientCriteriaEntry) => {
        setCriteria(criteria);
    };

    const toSearch = () => {
        navigate('/search/patient', { state: { defaults: criteria } });
    };

    const value = {
        toAddPatient,
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

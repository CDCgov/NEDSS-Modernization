import { createContext, useContext, useState, ReactNode } from 'react';
import { NewPatientEntry } from '../NewPatientEntry';
import { usePatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';
import { useConceptOptions } from 'options/concepts';
import { ExtendedNewPatientEntry } from '../extended';
import { asExtendedNewPatientEntry } from '../extended/asExtendedNewPatientEntry';
import { useLocation, useNavigate } from 'react-router-dom';

type BasicExtendedTransitionContextType = {
    transitionData: NewPatientEntry | null;
    setTransitionData: (data: NewPatientEntry) => void;
    toExtended: (initial: NewPatientEntry) => void;
    toBasic: () => void;
};

const BasicExtendedTransitionContext = createContext<BasicExtendedTransitionContextType | undefined>(undefined);

type BasicExtendedTransitionProviderProps = {
    children: ReactNode;
};

function BasicExtendedTransitionProvider({ children }: BasicExtendedTransitionProviderProps) {
    const [transitionData, setTransitionData] = useState<NewPatientEntry | null>(null);
    const navigate = useNavigate();
    const nameCodes = usePatientNameCodedValues();
    const raceCategories = useConceptOptions('P_RACE_CAT', { lazy: false }).options;
    const location = useLocation();

    const toExtended = (initial: NewPatientEntry) => {
        setTransitionData(initial);
        const defaults: ExtendedNewPatientEntry = asExtendedNewPatientEntry(initial, nameCodes, raceCategories);
        navigate('/patient/add/extended', { state: { defaults: defaults, criteria: location.state.criteria } });
    };

    const toBasic = () => {
        navigate('/add-patient', { state: { defaults: transitionData, criteria: location.state.criteria } });
    };

    const value: BasicExtendedTransitionContextType = {
        transitionData,
        setTransitionData,
        toExtended,
        toBasic
    };

    return <BasicExtendedTransitionContext.Provider value={value}>{children}</BasicExtendedTransitionContext.Provider>;
}

function useBasicExtendedTransition() {
    const context = useContext(BasicExtendedTransitionContext);
    if (!context) {
        throw new Error('useBasicExtendedTransition must be used within a BasicExtendedTransitionProvider');
    }
    return context;
}

export { BasicExtendedTransitionProvider, useBasicExtendedTransition };

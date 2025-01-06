import { createContext, useContext, useState, ReactNode } from 'react';
import { NewPatientEntry } from '../NewPatientEntry';
import { usePatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';
import { useConceptOptions } from 'options/concepts';
import { ExtendedNewPatientEntry } from '../extended';
import { asExtendedNewPatientEntry } from '../extended/asExtendedNewPatientEntry';
import { useNavigate } from 'react-router-dom';
import { BasicNewPatientEntry } from '../basic/entry';
import { asNewExtendedPatientEntry } from '../basic/asNewExtendedPatientEntry';

type BasicExtendedTransitionContextType = {
    transitionData: NewPatientEntry | BasicNewPatientEntry | null;
    setTransitionData: (data: NewPatientEntry) => void;
    toExtended: (initial: NewPatientEntry) => void;
    toExtendedNew: (initial: BasicNewPatientEntry) => void;
    toBasic: () => void;
};

const BasicExtendedTransitionContext = createContext<BasicExtendedTransitionContextType | undefined>(undefined);

type BasicExtendedTransitionProviderProps = {
    children: ReactNode;
};

function BasicExtendedTransitionProvider({ children }: BasicExtendedTransitionProviderProps) {
    const [transitionData, setTransitionData] = useState<NewPatientEntry | BasicNewPatientEntry | null>(null);
    const navigate = useNavigate();
    const nameCodes = usePatientNameCodedValues();
    const raceCategories = useConceptOptions('P_RACE_CAT', { lazy: false }).options;

    const toExtended = (initial: NewPatientEntry) => {
        setTransitionData(initial);
        const defaults: ExtendedNewPatientEntry = asExtendedNewPatientEntry(initial, nameCodes, raceCategories);
        navigate('/patient/add/extended', { state: { defaults: defaults } });
    };

    const toExtendedNew = (initial: BasicNewPatientEntry) => {
        setTransitionData(initial);
        const defaults: ExtendedNewPatientEntry = asNewExtendedPatientEntry(initial);
        navigate('/patient/add/extended', { state: { defaults: defaults } });
    };

    const toBasic = () => {
        navigate('/add-patient', { state: { defaults: transitionData } });
    };

    const value: BasicExtendedTransitionContextType = {
        transitionData,
        setTransitionData,
        toExtended,
        toExtendedNew,
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

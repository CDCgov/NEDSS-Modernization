import { createContext, useContext, useState, ReactNode } from 'react';
import { NewPatientEntry } from '../NewPatientEntry';
import { useConceptOptions } from 'options/concepts';
import { ExtendedNewPatientEntry } from '../extended';
import { asExtendedNewPatientEntry } from '../extended/asExtendedNewPatientEntry';
import { useNavigate } from 'react-router';
import { BasicNewPatientEntry } from '../basic/entry';
import { asNewExtendedPatientEntry } from '../basic/asNewExtendedPatientEntry';
import { useConfiguration } from 'configuration';
import { useNameCodedValues } from 'apps/patient/data/name/useNameCodedValues';

type BasicExtendedTransitionContextType = {
    transitionData: NewPatientEntry | null;
    setTransitionData: (data: NewPatientEntry) => void;
    newTransitionData: BasicNewPatientEntry | null;
    setNewTransitionData: (data: BasicNewPatientEntry) => void;
    toExtended: (initial: NewPatientEntry) => void;
    toExtendedNew: (initial: BasicNewPatientEntry, criteria?: object) => void;
    toBasic: () => void;
};

const BasicExtendedTransitionContext = createContext<BasicExtendedTransitionContextType | undefined>(undefined);

type BasicExtendedTransitionProviderProps = {
    children: ReactNode;
};

function BasicExtendedTransitionProvider({ children }: BasicExtendedTransitionProviderProps) {
    const [transitionData, setTransitionData] = useState<NewPatientEntry | null>(null);
    const [newTransitionData, setNewTransitionData] = useState<BasicNewPatientEntry | null>(null);
    const { features } = useConfiguration();
    const navigate = useNavigate();
    const nameCodes = useNameCodedValues();
    const raceCategories = useConceptOptions('P_RACE_CAT', { lazy: false }).options;
    const [criteria, setCriteria] = useState<object | undefined>(undefined);

    const toExtended = (initial: NewPatientEntry) => {
        setTransitionData(initial);
        const defaults: ExtendedNewPatientEntry = asExtendedNewPatientEntry(initial, nameCodes, raceCategories);
        navigate('/patient/add/extended', { state: { defaults: defaults } });
    };

    const toExtendedNew = (initial: BasicNewPatientEntry, criteria?: object) => {
        setNewTransitionData(initial);
        setCriteria(criteria);
        const defaults: ExtendedNewPatientEntry = asNewExtendedPatientEntry(initial);
        navigate('/patient/add/extended', { state: { defaults: defaults } });
    };

    const toBasic = () => {
        if (features.patient.add.enabled) {
            navigate('/patient/add', { state: { defaults: newTransitionData, criteria: criteria } });
        } else {
            navigate('/add-patient', { state: { defaults: transitionData, criteria: criteria } });
        }
    };

    const value: BasicExtendedTransitionContextType = {
        transitionData,
        setTransitionData,
        newTransitionData,
        setNewTransitionData,
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

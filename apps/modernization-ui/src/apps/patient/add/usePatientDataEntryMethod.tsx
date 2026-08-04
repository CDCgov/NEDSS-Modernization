import { createContext, ReactNode, useCallback, useContext, useState } from 'react';

import { useNavigate } from 'react-router';

import { asNewExtendedPatientEntry } from './basic/asNewExtendedPatientEntry';
import { BasicNewPatientEntry } from './basic/entry';
import { ExtendedNewPatientEntry } from './extended';

type PatientDataEntryMethodInteraction = {
    toExtended: (initial: BasicNewPatientEntry, criteria?: object) => void;
    toBasic: () => void;
};

const PatientDataEntryMethodContext = createContext<PatientDataEntryMethodInteraction | undefined>(undefined);

type Props = {
    children: ReactNode;
};

const PatientDataEntryMethodProvider = ({ children }: Props) => {
    const [basic, setBasic] = useState<BasicNewPatientEntry | undefined>(undefined);
    const [criteria, setCriteria] = useState<object | undefined>(undefined);

    const navigate = useNavigate();

    const toExtended = useCallback(
        (initial: BasicNewPatientEntry, criteria?: object) => {
            setBasic(initial);
            setCriteria(criteria);
            const extended: ExtendedNewPatientEntry = asNewExtendedPatientEntry(initial);
            navigate('/patient/add/extended', { state: { extended } });
        },
        [navigate, setBasic, setCriteria]
    );

    const toBasic = useCallback(() => {
        navigate('/patient/add', { state: { basic, criteria } });
    }, [navigate, basic, criteria]);

    const value: PatientDataEntryMethodInteraction = {
        toExtended,
        toBasic,
    };

    return <PatientDataEntryMethodContext.Provider value={value}>{children}</PatientDataEntryMethodContext.Provider>;
};

const usePatientDataEntryMethod = () => {
    const context = useContext(PatientDataEntryMethodContext);
    if (!context) {
        throw new Error('usePatientDataEntryMethod must be used within a PatientDataEntryMethodProvider');
    }
    return context;
};

export { PatientDataEntryMethodProvider, usePatientDataEntryMethod };

import { createContext, ReactNode, useCallback, useContext, useState } from 'react';

import { MemoizedSupplier } from 'libs/supplying/';

import { PatientDemographicsData } from './demographics';
import { description } from './description';
import { PatientFileEventData } from './events';
import { Patient } from './patient';
import { PatientFileSummaryData } from './summary';

type PatientFileData = {
    id: number;
    patient: Patient;
    summary: MemoizedSupplier<PatientFileSummaryData>;
    events: MemoizedSupplier<PatientFileEventData>;
    demographics: MemoizedSupplier<PatientDemographicsData>;
};

type PatientFileInteraction = PatientFileData & { refresh: () => void };

const PatientContext = createContext<PatientFileInteraction | undefined>(undefined);

type PatientProviderProps = {
    data: PatientFileData;
    children: ReactNode | ReactNode[];
};

const PatientFileProvider = ({ data, children }: PatientProviderProps) => {
    const [value, setValue] = useState<PatientFileData>(data);

    const refresh = useCallback(() => {
        data.demographics.reset();
        description(value.patient.patientId).then((patient) => setValue((current) => ({ ...current, patient })));
    }, [value]);

    const interaction = {
        ...value,
        refresh,
    };

    return <PatientContext.Provider value={interaction}>{children}</PatientContext.Provider>;
};

const usePatientFileData = () => {
    const context = useContext(PatientContext);
    if (context === undefined) {
        throw new Error('usePatientFile must be used within a PatientProvider');
    }
    return context;
};

export { PatientFileProvider, usePatientFileData };
export type { PatientFileData };

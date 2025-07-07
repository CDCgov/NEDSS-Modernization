import { createContext, ReactNode, useContext } from 'react';
import { MemoizedSupplier } from 'libs/supplying/';
import { Patient } from './patient';
import { PatientDemographics } from './demographics';

type PatientFileData = {
    id: number;
    patient: Patient;
    demographics: MemoizedSupplier<PatientDemographics>;
};

const PatientContext = createContext<PatientFileData | undefined>(undefined);

type PatientProviderProps = {
    data: PatientFileData;
    children: ReactNode | ReactNode[];
};

const PatientFileProvider = ({ data, children }: PatientProviderProps) => {
    return <PatientContext.Provider value={data}>{children}</PatientContext.Provider>;
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

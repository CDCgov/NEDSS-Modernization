import { createContext, ReactNode, useContext } from 'react';
import { Patient } from './patient';

const PatientContext = createContext<Patient | undefined>(undefined);

type PatientProviderProps = {
    patient: Patient;
    children: ReactNode | ReactNode[];
};

const PatientProvider = ({ patient, children }: PatientProviderProps) => (
    <PatientContext.Provider value={patient}>{children}</PatientContext.Provider>
);

const usePatient = () => {
    const context = useContext(PatientContext);
    if (context === undefined) {
        throw new Error('usePatient must be used within a PatientProvider');
    }
    return context;
};

export { PatientProvider, usePatient };

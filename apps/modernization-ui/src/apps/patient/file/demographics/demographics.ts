import { MemoizedSupplier } from 'libs/supplying/';
import { PatientFileSexBirthDemographic, patientSexBirth } from './sex-birth';

type PatientDemographics = {
    sexBirth: MemoizedSupplier<Promise<PatientFileSexBirthDemographic>>;
};

export type { PatientDemographics };

const demographics = (patient: number): PatientDemographics => {
    return {
        sexBirth: new MemoizedSupplier(() => patientSexBirth(patient))
    };
};

export { demographics };

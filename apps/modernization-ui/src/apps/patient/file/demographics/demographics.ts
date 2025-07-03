import { MemoizedSupplier } from 'libs/supplying/';
import { PatientFileSexBirthDemographic, patientSexBirth } from './sex-birth';
import { GeneralInformationDemographic } from 'libs/patient/demographics/general';
import { patientGeneral } from './general/patientGeneral';

type PatientDemographics = {
    sexBirth: MemoizedSupplier<Promise<PatientFileSexBirthDemographic>>;
    general: MemoizedSupplier<Promise<GeneralInformationDemographic>>;
};

export type { PatientDemographics };

const demographics = (patient: number): PatientDemographics => {
    return {
        sexBirth: new MemoizedSupplier(() => patientSexBirth(patient)),
        general: new MemoizedSupplier(() => patientGeneral(patient))
    };
};

export { demographics };

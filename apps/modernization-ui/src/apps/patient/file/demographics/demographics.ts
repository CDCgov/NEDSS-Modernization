import { MemoizedSupplier } from 'libs/supplying/';
import { PatientFileSexBirthDemographic, patientSexBirth } from './sex-birth';
import { GeneralInformationDemographic } from 'libs/patient/demographics/general';
import { patientGeneral } from './general/patientGeneral';
import { PhoneEmailDemographic } from 'libs/patient/demographics/phoneEmail/phoneEmails';
import { patientPhoneEmail } from './phoneEmail/patientPhoneEmail';

type PatientDemographics = {
    sexBirth: MemoizedSupplier<Promise<PatientFileSexBirthDemographic>>;
    general: MemoizedSupplier<Promise<GeneralInformationDemographic>>;
    phoneEmail: MemoizedSupplier<Promise<Array<PhoneEmailDemographic>>>;
};

export type { PatientDemographics };

const demographics = (patient: number): PatientDemographics => {
    return {
        sexBirth: new MemoizedSupplier(() => patientSexBirth(patient)),
        general: new MemoizedSupplier(() => patientGeneral(patient)),
        phoneEmail: new MemoizedSupplier(() => patientPhoneEmail(patient))
    };
};

export { demographics };

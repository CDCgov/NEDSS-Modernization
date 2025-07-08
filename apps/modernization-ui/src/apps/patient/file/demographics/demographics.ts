import { MemoizedSupplier } from 'libs/supplying/';
import { PatientFileSexBirthDemographic, patientSexBirth } from './sex-birth';
import { GeneralInformationDemographic } from 'libs/patient/demographics/general';
import { patientGeneral } from './general/patientGeneral';
import { PhoneEmailDemographic } from 'libs/patient/demographics/phoneEmail/phoneEmails';
import { patientPhoneEmail } from './phoneEmail/patientPhoneEmail';
import { MortalityDemographic } from 'libs/patient/demographics/mortality';
import { patientMortality } from './mortality';

type PatientDemographics = {
    phoneEmail: MemoizedSupplier<Promise<Array<PhoneEmailDemographic>>>;
    sexBirth: MemoizedSupplier<Promise<PatientFileSexBirthDemographic>>;
    mortality: MemoizedSupplier<Promise<MortalityDemographic>>;
    general: MemoizedSupplier<Promise<GeneralInformationDemographic>>;
};

export type { PatientDemographics };

const demographics = (patient: number): PatientDemographics => {
    return {
        phoneEmail: new MemoizedSupplier(() => patientPhoneEmail(patient)),
        sexBirth: new MemoizedSupplier(() => patientSexBirth(patient)),
        mortality: new MemoizedSupplier(() => patientMortality(patient)),
        general: new MemoizedSupplier(() => patientGeneral(patient))
    };
};

export { demographics };

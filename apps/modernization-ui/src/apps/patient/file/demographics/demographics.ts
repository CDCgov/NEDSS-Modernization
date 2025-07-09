import { MemoizedSupplier } from 'libs/supplying/';
import { demographicsSummary, PatientFileDemographicsSummary } from './summary';
import { PatientFileSexBirthDemographic, patientSexBirth } from './sex-birth';
import { GeneralInformationDemographic } from 'libs/patient/demographics/general';
import { patientGeneral } from './general/patientGeneral';
import { PhoneEmailDemographic } from 'libs/patient/demographics/phoneEmail/phoneEmails';
import { patientPhoneEmail } from './phoneEmail/patientPhoneEmail';
import { MortalityDemographic } from 'libs/patient/demographics/mortality';
import { patientMortality } from './mortality';
import { RaceDemographic } from 'libs/patient/demographics/race/race';
import { patientRace } from './race/patientRace';

type PatientDemographics = {
    summary: MemoizedSupplier<Promise<PatientFileDemographicsSummary>>;
    phoneEmail: MemoizedSupplier<Promise<Array<PhoneEmailDemographic>>>;
    sexBirth: MemoizedSupplier<Promise<PatientFileSexBirthDemographic>>;
    mortality: MemoizedSupplier<Promise<MortalityDemographic>>;
    general: MemoizedSupplier<Promise<GeneralInformationDemographic>>;
    race: MemoizedSupplier<Promise<Array<RaceDemographic>>>;
};

export type { PatientDemographics };

const demographics = (patient: number): PatientDemographics => ({
    summary: new MemoizedSupplier(() => demographicsSummary(patient)),
    phoneEmail: new MemoizedSupplier(() => patientPhoneEmail(patient)),
    race: new MemoizedSupplier(() => patientRace(patient)),
    sexBirth: new MemoizedSupplier(() => patientSexBirth(patient)),
    mortality: new MemoizedSupplier(() => patientMortality(patient)),
    general: new MemoizedSupplier(() => patientGeneral(patient))
});

export { demographics };

import {
    AddressDemographic,
    AdministrativeInformation,
    EthnicityDemographic,
    GeneralInformationDemographic,
    IdentificationDemographic,
    MortalityDemographic,
    NameDemographic,
    PhoneEmailDemographic,
    RaceDemographic,
} from 'libs/patient/demographics';
import { MemoizedSupplier } from 'libs/supplying/';

import { patientAddress } from './address';
import { patientAdministrative } from './administrative';
import { patientEthnicity } from './ethnicity';
import { patientGeneral } from './general';
import { patientIdentifications } from './identification';
import { patientMortality } from './mortality';
import { patientNames } from './name';
import { patientPhoneEmail } from './phoneEmail';
import { patientRace } from './race';
import { PatientFileSexBirthDemographic, patientSexBirth } from './sex-birth';
import { demographicsSummary, PatientFileDemographicsSummary } from './summary';

type PatientDemographicsData = {
    summary: MemoizedSupplier<Promise<PatientFileDemographicsSummary>>;
    administrative: MemoizedSupplier<Promise<AdministrativeInformation>>;
    names: MemoizedSupplier<Promise<NameDemographic[]>>;
    addresses: MemoizedSupplier<Promise<AddressDemographic[]>>;
    phoneEmail: MemoizedSupplier<Promise<PhoneEmailDemographic[]>>;
    identifications: MemoizedSupplier<Promise<IdentificationDemographic[]>>;
    race: MemoizedSupplier<Promise<RaceDemographic[]>>;
    ethnicity: MemoizedSupplier<Promise<EthnicityDemographic>>;
    sexBirth: MemoizedSupplier<Promise<PatientFileSexBirthDemographic>>;
    mortality: MemoizedSupplier<Promise<MortalityDemographic>>;
    general: MemoizedSupplier<Promise<GeneralInformationDemographic>>;
};

export type { PatientDemographicsData };

const demographics = (patient: number): PatientDemographicsData => ({
    summary: new MemoizedSupplier(() => demographicsSummary(patient)),
    administrative: new MemoizedSupplier(() => patientAdministrative(patient)),
    names: new MemoizedSupplier(() => patientNames(patient)),
    addresses: new MemoizedSupplier(() => patientAddress(patient)),
    phoneEmail: new MemoizedSupplier(() => patientPhoneEmail(patient)),
    identifications: new MemoizedSupplier(() => patientIdentifications(patient)),
    race: new MemoizedSupplier(() => patientRace(patient)),
    ethnicity: new MemoizedSupplier(() => patientEthnicity(patient)),
    sexBirth: new MemoizedSupplier(() => patientSexBirth(patient)),
    mortality: new MemoizedSupplier(() => patientMortality(patient)),
    general: new MemoizedSupplier(() => patientGeneral(patient)),
});

export { demographics };

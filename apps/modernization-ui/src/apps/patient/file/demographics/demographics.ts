import { MemoizedSupplier } from 'libs/supplying/';
import { demographicsSummary, PatientFileDemographicsSummary } from './summary';
import {
    AdministrativeInformation,
    GeneralInformationDemographic,
    MortalityDemographic,
    RaceDemographic,
    PhoneEmailDemographic,
    AddressDemographic,
    NameDemographic,
    EthnicityDemographic,
    IdentificationDemographic
} from 'libs/patient/demographics';

import { PatientFileSexBirthDemographic, patientSexBirth } from './sex-birth';
import { patientAdministrative } from './administrative';
import { patientGeneral } from './general';
import { patientPhoneEmail } from './phoneEmail';
import { patientMortality } from './mortality';
import { patientRace } from './race';
import { patientAddress } from './address';
import { patientNames } from './name';
import { patientEthnicity } from './ethnicity';
import { patientIdentifications } from './identification';

type PatientDemographics = {
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

export type { PatientDemographics };

const demographics = (patient: number): PatientDemographics => ({
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
    general: new MemoizedSupplier(() => patientGeneral(patient))
});

export { demographics };

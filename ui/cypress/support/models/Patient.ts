import { PatientIdentificationType } from './enums/PatientIdentificationType';
import { MaritalStatus } from './enums/MaritalStatus';
import { Race } from './enums/Race';
import { Suffix } from './enums/Suffix';
import { Ethnicity } from './enums/Ethnicity';
import { Deceased } from './enums/Deceased';
import { Sex } from './enums/Sex';
import { State } from './enums/State';

export default interface Patient {
    firstName: string;
    middleName: string;
    lastName: string;
    suffix: Suffix;
    maritalStatus: MaritalStatus;
    dateOfBirth: Date;
    currentSex: Sex;
    birthSex: Sex;
    deceased: Deceased;
    streetAddress: string;
    city: string;
    state: State;
    zip: string;
    county: string;
    country: string;
    censusTract: string;
    patientIds: string[];
    homePhone: string;
    workPhone: string;
    workPhoneExtension: string;
    cellPhone: string;
    email: string;
    ethnicitiy: Ethnicity;
    races: [Race];
    identifications: Identification[];
}

export interface Identification {
    identificationType: PatientIdentificationType;
    idNumber: string;
    assigningAuthority: string;
}

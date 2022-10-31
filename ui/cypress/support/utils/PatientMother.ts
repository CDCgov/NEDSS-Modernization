import { Deceased } from '../models/enums/Deceased';
import { Ethnicity } from '../models/enums/Ethnicity';
import { MaritalStatus } from '../models/enums/MaritalStatus';
import { PatientIdentificationType } from '../models/enums/PatientIdentificationType';
import { Race } from '../models/enums/Race';
import { Sex } from '../models/enums/Sex';
import { State } from '../models/enums/State';
import { Suffix } from '../models/enums/Suffix';
import Patient from '../models/Patient';

export default class PatientMother {
    public static patient(overrides?: Partial<Patient>): Patient {
        return {
            dateOfBirth: new Date('1970/01/01'),
            firstName: 'John',
            lastName: 'Doe',
            middleName: 'Bob',
            suffix: Suffix.NONE,
            maritalStatus: MaritalStatus.SINGLE_NEVER_MARRIED,
            deceased: Deceased.NO,
            county: 'Dekalb County',
            censusTract: '1234.22',
            workPhone: '111-222-3333',
            workPhoneExtension: '59',
            cellPhone: '123-456-7890',
            country: 'United States',
            currentSex: Sex.MALE,
            birthSex: Sex.MALE,
            streetAddress: '1600 Clifton Road',
            city: 'Atlanta',
            state: State.GEORGIA,
            zip: '30329',
            patientIds: [],
            homePhone: '123-456-7890',
            email: 'JohnDoe@email.com',
            ethnicitiy: Ethnicity.NOT_HISPANIC_OR_LATINO,
            races: [Race.WHITE],
            identifications: [
                {
                    identificationType: PatientIdentificationType.DRIVERS_LICENSE_NUMBER,
                    assigningAuthority: 'GA',
                    idNumber: 'ID:123456'
                }
            ],
            ...overrides
        };
    }

    public static duplicatedPatient(): Patient {
        return PatientMother.patient({ firstName: 'Johnathan', streetAddress: '1234 Another Road' });
    }
}

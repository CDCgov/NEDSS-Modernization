import { asSelectable } from 'options';
import { NewPatientEntry } from '../NewPatientEntry';
import { ExtendedNewPatientEntry } from './entry';
import { AddressEntry, NameEntry, PhoneEmailEntry } from 'apps/patient/data/entry';

// const useBasicToExtended = (initial: NewPatientEntry): ExtendedNewPatientEntry => {
//     const extendedFormValues: ExtendedNewPatientEntry = {
//         administrative: { asOf: initial.asOf, comment: initial.comments ?? undefined },
//         names: [
//             {
//                 asOf: initial.asOf,
//                 type: asSelectable('L', 'Legal'),
//                 first: initial.firstName ?? undefined,
//                 middle: initial.middleName ?? undefined,
//                 last: initial.lastName ?? undefined,
//                 suffix: asSelectable(initial.suffix ?? '')
//             }
//         ],
//         addresses: [
//             {
//                 asOf: initial.asOf,
//                 type: asSelectable('H', 'House'),
//                 use: asSelectable('H', 'Home'),
//                 address1: initial.streetAddress1 ?? undefined,
//                 address2: initial.streetAddress2 ?? undefined,
//                 city: initial.city ?? undefined,
//                 state: asSelectable(initial.state ?? ''),
//                 zipcode: initial.zip ?? undefined,
//                 county: asSelectable(initial.county ?? ''),
//                 country: asSelectable(initial.country ?? ''),
//                 censusTract: initial.censusTract ?? undefined
//             }
//         ]
//     };

//     return extendedFormValues;
// };

const nameExtended = (initial: NewPatientEntry): NameEntry => {
    const name: NameEntry = {
        asOf: initial.asOf,
        type: asSelectable('L', 'Legal'),
        first: initial.firstName ?? undefined,
        last: initial.lastName ?? undefined,
        middle: initial.middleName ?? undefined,
        suffix: asSelectable(initial.suffix ?? '')
    };
    return name;
};

const addressExtended = (initial: NewPatientEntry): AddressEntry => {
    const addresses = {
        asOf: initial.asOf,
        type: asSelectable('H', 'House'),
        use: asSelectable('H', 'Home'),
        address1: initial.streetAddress1 ?? undefined,
        address2: initial.streetAddress2 ?? undefined,
        city: initial.city ?? undefined,
        state: asSelectable(initial.state ?? ''),
        zipcode: initial.zip ?? undefined,
        county: asSelectable(initial.county ?? ''),
        country: asSelectable(initial.country ?? ''),
        censusTract: initial.censusTract ?? undefined
    };
    return addresses;
};

const phoneEmailsExtended = (initial: NewPatientEntry): PhoneEmailEntry[] => {
  const phoneEmails: PhoneEmailEntry[] = [];

  if(initial.homePhone) {
    phoneEmails.push({
      asOf: initial.asOf,
      type: asSelectable('PH', 'Phone'),
      use: asSelectable('H', 'Home'),
      phoneNumber: initial.homePhone
    });
  }

  if(initial.cellPhone) {
    phoneEmails.push({
      asOf: initial.asOf,
      type: asSelectable('CP', 'Cellular phone'),
      use: asSelectable('MC', 'Mobile contact'),
      phoneNumber: initial.cellPhone
    });
  }

  return phoneEmails;
}

export { nameExtended, addressExtended };

export interface AdministrativeComments {
    asOf: string;
    comments: string;
    selected: boolean;
}

export interface NameEntry {
    asOf: string;
    type: string;
    prefix: string;
    last: string;
    first: string;
    middle: string;
    suffix: string;
    degree: string;
    selected: boolean;
}

export interface AddressEntry {
    asOf: string;
    type: string;
    address: string;
    city: string;
    state: string;
    zip: string;
    selected: boolean;
}

export interface PhoneEmailEntry {
    asOf: string;
    type: string;
    phoneNumber: string;
    emailAddress: string;
    comments: string;
    selected: boolean;
}

export interface IdentificationEntry {
    asOf: string;
    type: string;
    assigningAuthority: string;
    idValue: string;
    selected: boolean;
}

export interface RaceEntry {
    asOf: string;
    race: string;
    detailedRace: string;
    selected: boolean;
}

export interface EthnicityInfo {
    asOf: string;
    ethnicity: string;
    spanishOrigin: string;
    reasonUnknown: string;
    selected: boolean;
}

export interface SexAndBirthInfo {
    asOf: string;
    dateOfBirth: string;
    currentAge: number;
    currentSex: string;
    unknownReason: string;
    transgenderInformation: string;
    additionalGender: string;
    birthSex: string;
    multipleBirth: string;
    birthOrder: number | null;
    birthCity: string;
    birthState: string;
    birthCounty: string;
    birthCountry: string;
}

export interface MortalityInfo {
    asOf: string;
    isDeceased: boolean;
    dateOfDeath: string | null;
    cityOfDeath: string | null;
    stateOfDeath: string | null;
    countyOfDeath: string | null;
    countryOfDeath: string | null;
}

export interface GeneralPatientInfo {
    asOf: string;
    maritalStatus: string;
    mothersMaidenName: string;
    numberOfAdultsInResidence: number;
    numberOfChildrenInResidence: number;
    primaryOccupation: string;
    highestLevelOfEducation: string;
    primaryLanguage: string;
    speaksEnglish: boolean;
    stateHivCaseId: string;
}

export interface PatientPreviewData {
    patientId: string;
    firstName: string;
    lastName: string;
    dob: string;
    comments: AdministrativeComments[];
    name: NameEntry[];
    address: AddressEntry[];
    phoneAndEmail: PhoneEmailEntry[];
    identification: IdentificationEntry[];
    race: RaceEntry[];
    ethnicity: EthnicityInfo;
    sexAndBirthCandidates: SexAndBirthInfo[];
    selectedSexAndBirth: Partial<SexAndBirthInfo>;
    mortalityCandidates: MortalityInfo[];
    selectedMortality: Partial<MortalityInfo>;
    generalPatientInformationCandidates: GeneralPatientInfo[];
    selectedGeneralPatientInformation: Partial<GeneralPatientInfo>;
}

// Dummy Data
export const dummyPatientData: PatientPreviewData = {
    patientId: "12345",
    firstName: "John",
    lastName: "Doe",
    dob: "1985-04-12",

    comments: [
        {
            asOf: "2025-02-05",
            comments: "Verified Address through DL",
            selected: true
        },
        {
            asOf: "2025-05-09",
            comments: "No discrepancies found in core identifiers",
            selected: false
        }
    ],

    name: [
        {
            asOf: "2023-01-01",
            type: "Legal",
            prefix: "Mr.",
            last: "Doe",
            first: "John",
            middle: "Allen",
            suffix: "Jr.",
            degree: "MD",
            selected: true,
        },
        {
            asOf: "2024-01-01",
            type: "Alias",
            prefix: "",
            last: "Smith",
            first: "Johnny",
            middle: "",
            suffix: "",
            degree: "",
            selected: false,
        },
    ],

    address: [
        {
            asOf: "2023-05-01",
            type: "Home",
            address: "123 Main St",
            city: "Boston",
            state: "MA",
            zip: "02118",
            selected: true,
        },
        {
            asOf: "2024-02-15",
            type: "Work",
            address: "456 Corporate Blvd",
            city: "Cambridge",
            state: "MA",
            zip: "02139",
            selected: false,
        },
    ],

    phoneAndEmail: [
        {
            asOf: "2023-06-01",
            type: "Mobile",
            phoneNumber: "555-123-4567",
            emailAddress: "john.doe@example.com",
            comments: "Primary contact number",
            selected: true,
        },
        {
            asOf: "2024-03-10",
            type: "Work",
            phoneNumber: "555-987-6543",
            emailAddress: "j.doe@company.com",
            comments: "Work contact",
            selected: false,
        },
    ],

    identification: [
        {
            asOf: "2022-09-01",
            type: "SSN",
            assigningAuthority: "SSA",
            idValue: "123-45-6789",
            selected: true,
        },
        {
            asOf: "2023-04-15",
            type: "Driver's License",
            assigningAuthority: "MA DMV",
            idValue: "D1234567",
            selected: false,
        },
    ],

    race: [
        {
            asOf: "2023-07-01",
            race: "Asian",
            detailedRace: "Chinese",
            selected: true,
        },
        {
            asOf: "2024-01-20",
            race: "White",
            detailedRace: "Middle Eastern",
            selected: false,
        },
    ],

    ethnicity: {
        asOf: "2023-08-15",
        ethnicity: "Not Hispanic or Latino",
        spanishOrigin: "None",
        reasonUnknown: "Not applicable",
        selected: true
    },

    sexAndBirthCandidates: [
        {
            asOf: "2023-09-01",
            dateOfBirth: "1985-04-12",
            currentAge: 40,
            currentSex: "Male",
            unknownReason: "",
            transgenderInformation: "None",
            additionalGender: "None",
            birthSex: "Male",
            multipleBirth: "No",
            birthOrder: null,
            birthCity: "Springfield",
            birthState: "IL",
            birthCounty: "King",
            birthCountry: "USA",
        },
        {
            asOf: "2024-03-01",
            dateOfBirth: "1985-05-01",
            currentAge: 39,
            currentSex: "Male",
            unknownReason: "",
            transgenderInformation: "N/A",
            additionalGender: "",
            birthSex: "Male",
            multipleBirth: "Yes",
            birthOrder: 2,
            birthCity: "Chicago",
            birthState: "IL",
            birthCounty: "Mushroom",
            birthCountry: "USA",
        },
        {
            asOf: "2022-12-01",
            dateOfBirth: "1985-04-12",
            currentAge: 40,
            currentSex: "Unknown",
            unknownReason: "Medical condition",
            transgenderInformation: "Female to Male",
            additionalGender: "Non-binary",
            birthSex: "Female",
            multipleBirth: "No",
            birthOrder: null,
            birthCity: "Naperville",
            birthState: "IL",
            birthCounty: "Sea",
            birthCountry: "USA",
        },
    ],

    selectedSexAndBirth: {
        asOf: "2023-09-01",
        dateOfBirth: "1985-04-12",
        currentAge: 40,
        currentSex: "Male",
        unknownReason: "N/A",
        transgenderInformation: "Female to Male",
        additionalGender: "Non-binary",
        birthSex: "Female",
        multipleBirth: "No",
        birthOrder: null,
        birthCity: "Naperville",
        birthState: "IL",
        birthCounty: "Mushroom",
        birthCountry: "USA",
    },

    mortalityCandidates: [
        {
            asOf: "2024-11-01",
            isDeceased: false,
            dateOfDeath: null,
            cityOfDeath: null,
            stateOfDeath: null,
            countyOfDeath: null,
            countryOfDeath: null,
        },
        {
            asOf: "2024-05-01",
            isDeceased: true,
            dateOfDeath: "2024-04-15",
            cityOfDeath: "Boston",
            stateOfDeath: "MA",
            countyOfDeath: "Suffolk",
            countryOfDeath: "USA",
        },
        {
            asOf: "2023-11-20",
            isDeceased: false,
            dateOfDeath: null,
            cityOfDeath: null,
            stateOfDeath: null,
            countyOfDeath: null,
            countryOfDeath: null,
        },
    ],

    selectedMortality: {
        asOf: "2024-05-01",
        isDeceased: true,
        dateOfDeath: "2024-04-15",
        cityOfDeath: "Boston",
        stateOfDeath: "MA",
        countyOfDeath: "Suffolk",
        countryOfDeath: "USA",
    },

    generalPatientInformationCandidates: [
        {
            asOf: "2023-10-01",
            maritalStatus: "Married",
            mothersMaidenName: "Smith",
            numberOfAdultsInResidence: 2,
            numberOfChildrenInResidence: 1,
            primaryOccupation: "Software Engineer",
            highestLevelOfEducation: "Master's Degree",
            primaryLanguage: "English",
            speaksEnglish: true,
            stateHivCaseId: "MA-HIV-56789",
        },
        {
            asOf: "2024-01-10",
            maritalStatus: "Single",
            mothersMaidenName: "Johnson",
            numberOfAdultsInResidence: 1,
            numberOfChildrenInResidence: 0,
            primaryOccupation: "Analyst",
            highestLevelOfEducation: "Bachelor's Degree",
            primaryLanguage: "Spanish",
            speaksEnglish: false,
            stateHivCaseId: "MA-HIV-12345",
        },
        {
            asOf: "2022-12-05",
            maritalStatus: "Divorced",
            mothersMaidenName: "Brown",
            numberOfAdultsInResidence: 3,
            numberOfChildrenInResidence: 2,
            primaryOccupation: "Teacher",
            highestLevelOfEducation: "Doctorate",
            primaryLanguage: "French",
            speaksEnglish: true,
            stateHivCaseId: "MA-HIV-99999",
        },
    ],

    selectedGeneralPatientInformation: {
        asOf: "2022-12-05",
        maritalStatus: "Divorced",
        mothersMaidenName: "Brown",
        numberOfAdultsInResidence: 3,
        numberOfChildrenInResidence: 2,
        primaryOccupation: "Teacher",
        highestLevelOfEducation: "Doctorate",
        primaryLanguage: "French",
        speaksEnglish: true,
        stateHivCaseId: "MA-HIV-99999",
    }
};

export const dummySelectedCounts = {
    nameCount: dummyPatientData.name.filter(entry => entry.selected).length,
    addressCount: dummyPatientData.address.filter(entry => entry.selected).length,
    phoneAndEmailCount: dummyPatientData.phoneAndEmail.filter(entry => entry.selected).length,
    identificationCount: dummyPatientData.identification.filter(entry => entry.selected).length,
    raceCount: dummyPatientData.race.filter(entry => entry.selected).length,
};

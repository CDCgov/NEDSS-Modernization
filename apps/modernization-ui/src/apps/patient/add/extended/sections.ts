export type Section = {
    id: string;
    label: string;
};

export const sections: Section[] = [
    { id: 'administrative', label: 'Administrative' },
    { id: 'name', label: 'Name' },
    { id: 'address', label: 'Address' },
    { id: 'phoneAndEmail', label: 'Phone & email' },
    { id: 'identification', label: 'Identification' },
    { id: 'race', label: 'Race' },
    { id: 'ethnicity', label: 'Ethnicity' },
    { id: 'sexAndBirth', label: 'Sex & birth' },
    { id: 'mortality', label: 'Mortality' },
    { id: 'general', label: 'General patient information' }
];

export type Section = {
    id: string;
    label: string;
};

export const sections: Section[] = [
    { id: 'section-Administrative', label: 'Administrative' },
    { id: 'section-Name', label: 'Name' },
    { id: 'section-Address', label: 'Address' },
    { id: 'section-PhoneAndEmail', label: 'Phone & email' },
    { id: 'section-Identification', label: 'Identification' },
    { id: 'section-Race', label: 'Race' },
    { id: 'section-Ethnicity', label: 'Ethnicity' },
    { id: 'section-SexAndBirth', label: 'Sex & birth' },
    { id: 'section-Mortality', label: 'Mortality' },
    { id: 'section-General', label: 'General patient information' }
];

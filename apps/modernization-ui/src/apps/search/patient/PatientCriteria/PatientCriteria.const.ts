import { Selectable } from 'options';

export const deceased: Selectable[] = [
    { name: 'Living', label: 'Living', value: 'living' },
    { name: 'Deceased', label: 'Deceased', value: 'deceased' }
];

export const gender: Selectable[] = [
    { name: 'Male', label: 'Male', value: 'M' },
    { name: 'Female', label: 'Female', value: 'F' },
    { name: 'Other', label: 'Other', value: 'U' }
];

export const country: Selectable[] = [
    { name: 'United State of America', label: 'United State of America', value: 'USA' }
];

export const ethnicity: Selectable[] = [
    { name: 'ethnicity', label: 'White', value: 'W' },
    { name: 'ethnicity', label: 'Black', value: 'B' }
];

export const race: Selectable[] = [{ name: 'Pacific Islander', label: 'Pacific Islander', value: 'PI' }];

export const recordStatus: Selectable[] = [
    { name: 'recordStatus', label: 'Active', value: 'active' },
    { name: 'recordStatus', label: 'Archived', value: 'archived' }
];

export const state: Selectable[] = [
    { name: 'state', label: 'Alabama', value: '01' },
    { name: 'state', label: 'Alaska', value: '02' },
    { name: 'state', label: 'Arizona', value: '03' }
];

export const assigningAuthority: Selectable[] = [];

export const identificationType: Selectable[] = [];

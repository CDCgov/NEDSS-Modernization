import { DataElement } from '../types';

export const dataElements: DataElement[] = [
    {
        name: 'lastName',
        label: 'Last name',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5, // 1
        logOdds: Math.log(0.5 / 0.5) // 0
    },
    {
        name: 'secondLastName',
        label: 'Second last name',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'firstName',
        label: 'First name',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'middleName',
        label: 'Middle name',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'secondMiddleName',
        label: 'Second middle name',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'suffix',
        label: 'Suffix',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'currentSex',
        label: 'Current sex',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'dateOfBirth',
        label: 'Date of birth',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'ssn',
        label: 'Social security number',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'idType',
        label: 'ID type',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'idAssigningAuthority',
        label: 'ID assigning authority',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'idValue',
        label: 'ID value',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'streetAddress1',
        label: 'Street address 1',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'streetAddress2',
        label: 'Street address 2',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'city',
        label: 'City',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'state',
        label: 'State',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'zip',
        label: 'Zip code',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'telephone',
        label: 'Telephone',
        active: true,
        m: 0.5,
        u: 0.1, // Different 'u' value
        threshold: 0.5,
        oddsRatio: 0.5 / 0.1, // Calculated dynamically
        logOdds: Math.log(0.5 / 0.1) // Calculated dynamically
    }
];

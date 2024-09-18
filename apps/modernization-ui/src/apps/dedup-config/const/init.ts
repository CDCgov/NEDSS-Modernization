import { DataElement } from '../types';

export const dataElements: DataElement[] = [
    {
        name: 'lastName',
        label: 'Last name',
        category: 'Name',
        active: true,
        m: 0.5,
        u: 0.5,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.5,
        logOdds: Math.log(0.5 / 0.5)
    },
    {
        name: 'secondLastName',
        label: 'Second last name',
        category: 'Name',
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
        category: 'Name',
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
        category: 'Name',
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
        category: 'Name',
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
        category: 'Name',
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
        category: 'Sex and birth',
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
        category: 'Sex and birth',
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
        category: 'Identification',
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
        category: 'Identification',
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
        category: 'Identification',
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
        category: 'Identification',
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
        category: 'Address',
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
        category: 'Address',
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
        category: 'Address',
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
        category: 'Address',
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
        category: 'Address',
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
        category: 'Address',
        active: true,
        m: 0.5,
        u: 0.1,
        threshold: 0.5,
        oddsRatio: 0.5 / 0.1,
        logOdds: Math.log(0.5 / 0.1)
    }
];

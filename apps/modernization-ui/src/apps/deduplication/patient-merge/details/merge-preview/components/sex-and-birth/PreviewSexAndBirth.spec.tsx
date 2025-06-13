import React from 'react';
import { render } from '@testing-library/react';
import { PreviewSexAndBirth } from './PreviewSexAndBirth';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';
import { MemoryRouter } from 'react-router';

const Fixture = () => {
    return (
        <MemoryRouter>
            <PreviewSexAndBirth mergeFormData={mergeFormData} mergeCandidates={mockMergeCandidates} />
        </MemoryRouter>
    );
};
const mockMergeCandidates: MergeCandidate[] = [
    {
        personUid: '1',
        personLocalId: 'ABC123',
        addTime: '2023-01-01T00:00:00Z',
        adminComments: { date: '2023-01-01', comment: 'Test comments' },
        names: [],
        addresses: [],
        phoneEmails: [],
        identifications: [],
        races: [],
        ethnicity: {},
        sexAndBirth: {
            asOf: "2025-06-12",
            dateOfBirth: "1987-11-23",
            currentSex: "Female",
            sexUnknown: "No",
            transgender: "Not Transgender",
            additionalGender: "None",
            birthGender: "Female",
            multipleBirth: "Yes",
            birthOrder: "2",
            birthCity: "Cincinnati",
            birthState: "Ohio",
            birthCounty: "Hamilton",
            birthCountry: "United States"
        },
        mortality: {
            asOf: '2023-01-02',
            deceased: '',
            dateOfDeath: '2025-01-02',
            deathCity: '',
            deathState: '',
            deathCounty: '',
            deathCountry: ''
        },
        general: {},
        investigations: [],
    },
    {
        personUid: '2',
        personLocalId: 'ABC123',
        addTime: '2023-01-01T00:00:00Z',
        adminComments: { date: '2023-01-01', comment: 'Test comments' },
        names: [],
        addresses: [],
        phoneEmails: [],
        identifications: [],
        races: [],
        ethnicity: {},
        sexAndBirth: {
            asOf: "2025-06-12",
            dateOfBirth: "1987-11-23",
            currentSex: "Unknown",
            sexUnknown: "No",
            transgender: "Not Transgender",
            additionalGender: "None",
            birthGender: "Female",
            multipleBirth: "Yes",
            birthOrder: "2",
            birthCity: "Cincinnati",
            birthState: "Ohio",
            birthCounty: "Hamilton",
            birthCountry: "United States"
        },
        mortality: {},
        general: {
            asOf: '2023-01-02',
            maritalStatus: 'Single',
            mothersMaidenName: '',
            numberOfAdultsInResidence: '',
            numberOfChildrenInResidence: '',
            primaryOccupation: '',
            educationLevel: '',
            primaryLanguage: 'Latin',
            speaksEnglish: '',
            stateHivCaseId: '1234-HIV'
        },
        investigations: [],
    }
];

const mergeFormData: PatientMergeForm = {
    survivingRecord: '123',
    adminComments: '123',
    ethnicity: '1',
    mortality: {},
    generalInfo: {
        stateHivCaseId: '1',
        primaryLanguage: '2'
    },
    names: [
        {
            personUid: '123',
            sequence: '1',
            asOf: '',
            type: '',
            first: 'John',
            middle: 'Q',
            last: 'Public',
            suffix: 'Jr.',
        },
    ],
    sexAndBirth: {
        currentSex: '1',
        dateOfBirth: '2',
        birthCity: '2'
    },
} as any;

describe('PreviewSexAndBirth Component', () => {
    it('renders the sex & birth table', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('Cincinnati')).toBeInTheDocument();
        expect(getByText('Female')).toBeInTheDocument();
    });
});
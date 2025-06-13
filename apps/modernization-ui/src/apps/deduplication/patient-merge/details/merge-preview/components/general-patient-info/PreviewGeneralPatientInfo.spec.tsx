import React from 'react';
import { render } from '@testing-library/react';
import { PreviewGeneralPatientInfo } from './PreviewGeneralPatientInfo';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';
import { MemoryRouter } from 'react-router';

const Fixture = () => {
    return (
        <MemoryRouter>
            <PreviewGeneralPatientInfo mergeFormData={mergeFormData} mergeCandidates={mockMergeCandidates} />
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
        sexAndBirth: {},
        mortality: {},
        general: {
            asOf: '2023-01-02',
            maritalStatus: 'Single',
            mothersMaidenName: '',
            numberOfAdultsInResidence: '',
            numberOfChildrenInResidence: '',
            primaryOccupation: '',
            educationLevel: '',
            primaryLanguage: '',
            speaksEnglish: '',
            stateHivCaseId: '1234-HIV'
        },
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
        sexAndBirth: {},
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
        currentSex: '123',
        dateOfBirth: '123',
    },
} as any;

describe('PreviewGeneralPatientInfo Component', () => {
    it('renders the general info table', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('1234-HIV')).toBeInTheDocument();
        expect(getByText('Latin')).toBeInTheDocument();
    });
});
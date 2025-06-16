import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { PatientSummary } from './PatientSummary';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';

describe('PatientSummary', () => {
    it('renders the most recent legal name and other fields correctly', () => {
        const mergeCandidates: MergeCandidate[] = [
            {
                personUid: '123',
                personLocalId: '98882',
                addTime: '2023-05-31T00:00:00Z',
                general: {},
                investigations: [],
                adminComments: { date: '', comment: '' },
                names: [
                    {
                        personUid: '123',
                        sequence: '1',
                        asOf: '2020-01-01T00:00:00Z',
                        type: 'Legal',
                        first: 'John',
                        middle: 'Q',
                        last: 'Public',
                        suffix: 'Jr.'
                    },
                    {
                        personUid: '123',
                        sequence: '2',
                        asOf: '2023-05-01T00:00:00Z',
                        type: 'Legal',
                        first: 'Johnny',
                        middle: 'R',
                        last: 'Citizen',
                        suffix: ''
                    }
                ],
                addresses: [],
                phoneEmails: [],
                identifications: [],
                races: [],
                ethnicity: {},
                sexAndBirth: {
                    currentSex: 'Male',
                    dateOfBirth: '2003-11-10T12:00:00Z'
                },
                mortality: {}
            }
        ];

        const mergeFormData: PatientMergeForm = {
            survivingRecord: '123',
            names: [
                { personUid: '123', sequence: '1' },
                { personUid: '123', sequence: '2' }
            ],
            addresses: [],
            phoneEmails: [],
            identifications: [],
            races: [],
            ethnicity: '',
            sexAndBirth: {
                currentSex: '123',
                dateOfBirth: '123'
            },
            mortality: {},
            generalInfo: {
                asOf: '',
                maritalStatus: '',
                mothersMaidenName: '',
                numberOfAdultsInResidence: '',
                numberOfChildrenInResidence: '',
                primaryOccupation: '',
                educationLevel: '',
                primaryLanguage: '',
                speaksEnglish: '',
                stateHivCaseId: ''
            },
            adminComments: ''
        };

        render(<PatientSummary mergeCandidates={mergeCandidates} mergeFormData={mergeFormData} />);

        // Most recent legal name: Citizen, Johnny R
        expect(
            screen.getByRole('heading', {
                level: 2,
                name: (_content, element) => element?.textContent === 'Citizen, Johnny R'
            })
        ).toBeInTheDocument();

        expect(screen.getByText('Male')).toBeInTheDocument();
        expect(screen.getByText((text) => text.startsWith('11/09/2003'))).toBeInTheDocument();
    });
});

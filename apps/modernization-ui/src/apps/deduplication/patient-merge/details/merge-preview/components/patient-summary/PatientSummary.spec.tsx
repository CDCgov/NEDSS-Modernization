import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { PatientSummary } from './PatientSummary';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';

describe('PatientSummary', () => {
    it('renders the patient summary correctly', () => {
        const mergeCandidates: MergeCandidate[] = [
            {
                personUid: '123',
                personLocalId: '98882',
                addTime: '2023-05-31T00:00:00Z',
                general:{},
                investigations: [],
                adminComments: { date: '', comment: '' },
                names: [{
                    personUid: '123',
                    sequence: '1',
                    asOf: '',
                    type: '',
                    first: 'John',
                    middle: 'Q',
                    last: 'Public',
                    suffix: 'Jr.'
                }],
                addresses: [],
                phoneEmails: [],
                identifications: [],
                races: [],
                ethnicity: {},
                sexAndBirth: {
                    currentSex: 'Male',
                    dateOfBirth: '2003-11-10T00:00:00Z'
                },
                mortality: {}
            }
        ];

        const mergeFormData: PatientMergeForm = {
            survivingRecord: '123',
            names: [{
                personUid: '123',
                sequence: '1',
                asOf: '',
                type: '',
                first: 'John',
                middle: 'Q',
                last: 'Public',
                suffix: 'Jr.'
            }],
            sexAndBirth: {
                currentSex: '123',
                dateOfBirth: '123'
            }
        } as any;

        render(<PatientSummary mergeCandidates={mergeCandidates} mergeFormData={mergeFormData} />);

        expect(screen.getByRole('heading', { level: 2, name: (_content, element) =>
                element?.textContent === 'Public, John Q, Jr.'
        })).toBeInTheDocument();

        expect(screen.getByText('Male')).toBeInTheDocument();
    });
});

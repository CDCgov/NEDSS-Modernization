import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { AdministrativeComments } from './AdministrativeComments';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';

describe('AdministrativeComments', () => {
    it('renders title, date, and comment', () => {
        const mergeCandidates: MergeCandidate[] = [
            {
                personUid: '123',
                personLocalId: '98882',
                addTime: '2023-05-31T00:00:00Z',
                general:{},
                investigations: [],
                adminComments: {
                    date: '2023-05-31T00:00:00Z', // use ISO format date
                    comment: 'This is a test comment',
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
                addresses: [],
                phoneEmails: [],
                identifications: [],
                races: [],
                ethnicity: {},
                sexAndBirth: {
                    currentSex: 'Male',
                    dateOfBirth: '2003-11-10T00:00:00Z',
                },
                mortality: {},
            },
        ];

        const mergeFormData: PatientMergeForm = {
            survivingRecord: '123',
            adminComments: '123',
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

        render(
            <AdministrativeComments
                mergeCandidates={mergeCandidates}
                mergeFormData={mergeFormData}
            />
        );

        expect(screen.getByText(/Administrative comments/i)).toBeInTheDocument();
        expect(screen.getByText('05/30/2023')).toBeInTheDocument();
        expect(screen.getByText('This is a test comment')).toBeInTheDocument();
    });
});

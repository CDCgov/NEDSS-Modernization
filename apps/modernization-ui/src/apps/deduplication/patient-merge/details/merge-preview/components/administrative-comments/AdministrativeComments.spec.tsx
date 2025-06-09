import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { AdministrativeComments } from './AdministrativeComments';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';

describe('AdministrativeComments', () => {
    it('renders title, date, and comment', () => {
        const mergeCandidates: MergeCandidate[] = [
            {
                personUid: '123',
                adminComments: {
                    date: '2023-06-01T00:00:00Z',
                    comment: 'This is a test comment',
                },
                names: [],
                addresses: [],
                phoneEmails: [],
                identifications: [],
                races: [],
                ethnicity: {},
                sexAndBirth: {},
                mortality: {},
            },
        ];

        const mergeFormData = { adminComments: '123' } as any;

        render(
            <AdministrativeComments
                mergeCandidates={mergeCandidates}
                mergeFormData={mergeFormData}
            />
        );

        expect(screen.getByText(/Administrative comments/i)).toBeInTheDocument();
        expect(screen.getByText('05/31/2023')).toBeInTheDocument();
        expect(screen.getByText('This is a test comment')).toBeInTheDocument();
    });
});

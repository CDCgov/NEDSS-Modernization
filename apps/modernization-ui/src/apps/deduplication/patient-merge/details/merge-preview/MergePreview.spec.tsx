import { render, screen, fireEvent } from '@testing-library/react';
import { MergePreview } from './MergePreview';
import { MergeCandidate } from '../../../api/model/MergeCandidate';
import { PatientMergeForm } from '../merge-review/model/PatientMergeForm';

const mockMergeCandidates: MergeCandidate[] = [
    {
        personUid: '123',
        adminComments: { date: '2024-01-01', comment: 'No issues' },
        personLocalId: '98882',
        addTime: '2023-05-31T00:00:00Z',
        general:{},
        investigations: [],
        names: [
            {
                personUid: '123',
                sequence: '1',
                asOf: '',
                type: '',
                first: 'John',
                middle: 'Q',
                last: 'Public',
                suffix: 'Jr.'
            }
        ],
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

const mockMergeFormData: PatientMergeForm = {
    survivingRecord: '123',
    names: [
        {
            personUid: '123',
            sequence: '1',
            asOf: '',
            type: '',
            first: 'John',
            middle: 'Q',
            last: 'Public',
            suffix: 'Jr.'
        }
    ],
    sexAndBirth: {
        currentSex: '123',
        dateOfBirth: '123'
    }
} as any;

describe('MergePreview', () => {
    it('renders the "Merge preview" heading', () => {
        render(
            <MergePreview
                onBack={jest.fn()}
                mergeCandidates={mockMergeCandidates}
                mergeFormData={mockMergeFormData}
            />
        );

        const heading = screen.getByRole('heading', { name: /merge preview/i });
        expect(heading).toBeInTheDocument();
    });

    it('calls onBack when Back button is clicked', () => {
        const onBackMock = jest.fn();
        render(
            <MergePreview
                onBack={onBackMock}
                mergeCandidates={mockMergeCandidates}
                mergeFormData={mockMergeFormData}
            />
        );

        const backButton = screen.getByRole('button', { name: /back/i });
        fireEvent.click(backButton);
        expect(onBackMock).toHaveBeenCalledTimes(1);
    });

    it('renders the "Merge record" button', () => {
        render(
            <MergePreview
                onBack={jest.fn()}
                mergeCandidates={mockMergeCandidates}
                mergeFormData={mockMergeFormData}
            />
        );

        const mergeButton = screen.getByRole('button', {
            name: /confirm and merge patient records/i
        });

        expect(mergeButton).toBeInTheDocument();
    });

    it('renders patient summary with name', () => {
        render(
            <MergePreview
                onBack={jest.fn()}
                mergeCandidates={mockMergeCandidates}
                mergeFormData={mockMergeFormData}
            />
        );

        expect(
            screen.getByRole('heading', {
                level: 2,
                name: (_, element) =>
                    element?.textContent === 'Public, John Q, Jr.'
            })
        ).toBeInTheDocument();
    });
});

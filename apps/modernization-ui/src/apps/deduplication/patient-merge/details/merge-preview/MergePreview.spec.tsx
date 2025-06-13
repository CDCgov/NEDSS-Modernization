import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { MergePreview } from './MergePreview';
import { PatientMergeForm } from '../merge-review/model/PatientMergeForm';
import { MergeCandidate } from '../../../api/model/MergeCandidate';
import {MemoryRouter} from "react-router";
import {PatientSummary} from "./components/patient-summary/PatientSummary";
import {AdministrativeComments} from "./components/administrative-comments/AdministrativeComments";
import {PreviewName} from "./components/name/PreviewName";
import {PreviewAddress} from "./components/address/PreviewAddress";
import {PreviewPhoneAndEmail} from "./components/phone-and-email/PreviewPhoneAndEmail";
import {PreviewIdentification} from "./components/identification/PreviewIdentification";
import {PreviewRace} from "./components/race/PreviewRace";
import {PreviewEthnicity} from "./components/ethnicity/PreviewEthnicity";

describe('MergePreview', () => {
    const mockOnBack = jest.fn();

    const mockMergeFormData: PatientMergeForm = {
        survivingRecord: '1',
        adminComments: '1',
        names: [{ personUid: '1', sequence: '1' }],
        addresses: [{ locatorId: '101' }],
        phoneEmails: [{ locatorId: '201' }],
        identifications: [{ personUid: '1', sequence: '1' }],
        races: [{ personUid: '1', raceCode: '2106-3' }],
        ethnicity: '1',
        sexAndBirth: {},
        mortality: {},
        generalInfo: {
            asOf: '2023-01-01',
            maritalStatus: '',
            mothersMaidenName: '',
            numberOfAdultsInResidence: '',
            numberOfChildrenInResidence: '',
            primaryOccupation: '',
            educationLevel: '',
            primaryLanguage: '',
            speaksEnglish: '',
            stateHivCaseId: ''
        }
    };

    const mockMergeCandidates: MergeCandidate[] = [
        {
            personUid: '1',
            personLocalId: 'L123',
            addTime: '2024-01-01',
            adminComments: { date: '2024-01-01', comment: 'Note' },
            names: [{ personUid: '123', sequence: '1', asOf: '2023-01-01', type: 'Legal', first: 'Johnathan', last: 'Doe' }],
            addresses: [{ id: 'a1', asOf: '2023-01-01', type: 'Home', use: 'Primary', city: 'Boston', state: 'MA' }],
            phoneEmails: [{ id: 'p1', asOf: '2023-01-01', type: 'Phone', use: 'Mobile', phoneNumber: '1234567890' }],
            identifications: [{ personUid: '123', sequence: '1', asOf: '2023-01-01', type: 'SSN', value: '123-45-6789' }],
            races: [{ personUid: '123', raceCode: '2106-3', asOf: '2023-01-01', race: 'White' }],
            ethnicity: { ethnicity: 'Not Hispanic' },
            sexAndBirth: { dateOfBirth: '2000-01-01', currentSex: 'M' },
            mortality: { deceased: 'N' },
            general: {
                asOf: '2023-01-01',
                maritalStatus: 'Single',
                mothersMaidenName: 'Doe',
                numberOfAdultsInResidence: '2',
                numberOfChildrenInResidence: '0',
                primaryOccupation: 'Engineer',
                educationLevel: 'Bachelor',
                primaryLanguage: 'English',
                speaksEnglish: 'Yes',
                stateHivCaseId: 'HIV123'
            },
            investigations: []
        }
    ];

    const Fixture = (props: { onBack?: () => void }) => (
        <MemoryRouter>
            <div>
                <MergePreview
                    mergeFormData={mockMergeFormData}
                    mergeCandidates={mockMergeCandidates}
                    onBack={props.onBack ?? jest.fn()}
                />
                <PatientSummary mergeFormData={mockMergeFormData} mergeCandidates={mockMergeCandidates} />
                <AdministrativeComments mergeFormData={mockMergeFormData} mergeCandidates={mockMergeCandidates} />
                <PreviewName
                    mergeCandidates={mockMergeCandidates}
                    selectedNames={mockMergeFormData.names.map(({ personUid, sequence }) => ({ personUid, sequence }))}
                />
                <PreviewAddress
                    mergeCandidates={mockMergeCandidates}
                    selectedAddresses={mockMergeFormData.addresses.map(({ locatorId }) => ({ locatorId }))}
                />
                <PreviewPhoneAndEmail
                    mergeCandidates={mockMergeCandidates}
                    selectedPhoneEmails={mockMergeFormData.phoneEmails.map(({ locatorId }) => ({ locatorId }))}
                />
                <PreviewIdentification
                    mergeCandidates={mockMergeCandidates}
                    selectedIdentifications={mockMergeFormData.identifications.map(({ personUid, sequence }) => ({ personUid, sequence }))}
                />
                <PreviewRace
                    mergeCandidates={mockMergeCandidates}
                    selectedRaces={mockMergeFormData.races.map(({ personUid, raceCode }) => ({ personUid, raceCode }))}
                />
                <PreviewEthnicity mergeFormData={mockMergeFormData} mergeCandidates={mockMergeCandidates} />
            </div>
        </MemoryRouter>
    );

    it('renders title and buttons', () => {
        render(<Fixture />);

        expect(screen.getByRole('heading', { name: /merge preview/i })).toBeInTheDocument();
        expect(screen.getByRole('button', { name: /back/i })).toBeInTheDocument();
        expect(screen.getByRole('button', { name: /confirm and merge patient records/i })).toBeInTheDocument();
    });

    it('calls onBack when "Back" button is clicked', () => {
        render(<Fixture onBack={mockOnBack} />);
        fireEvent.click(screen.getByRole('button', { name: /back/i }));
        expect(mockOnBack).toHaveBeenCalled();
    });

    it('renders components correctly', () => {
        render(<Fixture />);
        const adminComments = screen.getAllByRole('heading', { name: /Administrative comments/i });
        expect(adminComments.length).toBeGreaterThan(0);
        const name = screen.getAllByRole('heading', { name: /Name/i });
        expect(name.length).toBeGreaterThan(0);
        const address = screen.getAllByRole('heading', { name: /Address/i });
        expect(address.length).toBeGreaterThan(0);
        const phoneEmail = screen.getAllByRole('heading', { name: /Phone & Email/i });
        expect(phoneEmail.length).toBeGreaterThan(0);
        const race = screen.getAllByRole('heading', { name: /Race/i });
        expect(race.length).toBeGreaterThan(0);
        const identification = screen.getAllByRole('heading', { name: /Identification/i });
        expect(identification.length).toBeGreaterThan(0);
    });
});

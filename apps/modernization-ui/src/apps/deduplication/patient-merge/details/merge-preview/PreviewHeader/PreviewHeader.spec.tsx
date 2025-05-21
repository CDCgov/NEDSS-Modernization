import React from 'react';
import { render, screen } from '@testing-library/react';
import { PreviewHeader } from './PreviewHeader';
import { dummyPatientData, getLatestLegalName } from '../dummyData';
import * as dummyDataModule from '../dummyData';

jest.mock('./PatientSummary/PatientSummaryCard', () => ({
    PatientSummaryCard: ({ firstName, lastName, dob, age, gender, patientId }: any) => (
        <div data-testid="summary-card">
            <span data-testid="first-name">{firstName}</span>
            <span data-testid="last-name">{lastName}</span>
            <span data-testid="dob">{dob}</span>
            <span data-testid="age">{age}</span>
            <span data-testid="gender">{gender}</span>
            <span data-testid="patient-id">{patientId}</span>
        </div>
    )
}));

describe('PreviewHeader', () => {
    it('renders PatientSummaryCard with correct data from dummyPatientData', () => {
        render(<PreviewHeader/>);

        const latestLegalName = getLatestLegalName(dummyPatientData.name);

        expect(screen.getByTestId('first-name').textContent).toBe(latestLegalName?.first || '---');
        expect(screen.getByTestId('last-name').textContent).toBe(latestLegalName?.last || '---');
        expect(screen.getByTestId('dob').textContent).toBe(dummyPatientData.selectedSexAndBirth.dateOfBirth || '---');
        expect(screen.getByTestId('age').textContent).toBe(
            dummyPatientData.selectedSexAndBirth.currentAge?.toString() ?? '---'
        );
        expect(screen.getByTestId('gender').textContent).toBe(dummyPatientData.selectedSexAndBirth.birthSex);
        expect(screen.getByTestId('patient-id').textContent).toBe(dummyPatientData.patientId);
    });

    it('renders fallback values when getLatestLegalName returns undefined', () => {
        jest.spyOn(dummyDataModule, 'getLatestLegalName').mockReturnValue(undefined);

        render(<PreviewHeader />);

        expect(screen.getAllByText('---').length).toBeGreaterThanOrEqual(2);
    });
});

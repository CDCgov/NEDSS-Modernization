import { render } from '@testing-library/react';
import { PatientDataProvider, usePatientData } from './usePatientData';
import userEvent from '@testing-library/user-event';

function TestComponent() {
    const { addPatientFormData, setAddPatientFormData } = usePatientData();

    return (
        <div>
            <button
                onClick={() =>
                    setAddPatientFormData({
                        firstName: 'John',
                        lastName: 'Doe',
                        dateOfBirth: '01-01-1993',
                        asOf: '01-01-2024',
                        identification: [],
                        phoneNumbers: [],
                        emailAddresses: []
                    })
                }>
                Add Patient
            </button>
            <div data-testid="patient-name">{`${addPatientFormData?.firstName} ${addPatientFormData?.lastName}`}</div>
            <div data-testid="patient-age">{addPatientFormData?.dateOfBirth}</div>
        </div>
    );
}

describe('usePatientData', () => {
    it('should update addPatientEntry when setAddPatientEntry is called', () => {
        const { getByTestId, getByText } = render(
            <PatientDataProvider>
                <TestComponent />
            </PatientDataProvider>
        );

        userEvent.click(getByText('Add Patient'));

        expect(getByTestId('patient-name').textContent).toBe('John Doe');
        expect(getByTestId('patient-age').textContent).toBe('01-01-1993');
    });

    it('should throw an error if usePatientData is used outside of AddPatientDataProvider', () => {
        const consoleError = jest.spyOn(console, 'error').mockImplementation(() => {});

        expect(() => render(<TestComponent />)).toThrow('usePatientData must be used within a PatientDataProvider');

        consoleError.mockRestore();
    });
});

import { render } from '@testing-library/react';
import { BasicExtendedTransitionProvider, useBasicExtendedTransition } from './useBasicExtendedTransition';
import userEvent from '@testing-library/user-event';
import { MemoryRouter } from 'react-router-dom';

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: jest.fn()
}));

const mockPatientNameCodedValues = {
    types: [{ name: 'Adopted name', value: 'AN' }],
    prefixes: [{ name: 'Miss', value: 'MS' }],
    suffixes: [{ name: 'Sr.', value: 'SR' }],
    degrees: [{ name: 'BA', value: 'BA' }]
};

jest.mock('apps/patient/profile/names/usePatientNameCodedValues', () => ({
    usePatientNameCodedValues: () => mockPatientNameCodedValues
}));

function TestComponent() {
    const { transitionData, setTransitionData } = useBasicExtendedTransition();

    return (
        <div>
            <button
                onClick={() =>
                    setTransitionData({
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
            {/* <div data-testid="patient-name">{`${transitionData?.firstName} ${transitionData?.lastName}`}</div>
            <div data-testid="patient-age">{transitionData?.dateOfBirth}</div> */}
        </div>
    );
}

describe('useBasicExtendedTransition', () => {
    it('should update transitionData when setTransitionData is called', () => {
        const { getByTestId, getByText } = render(
            <MemoryRouter>
                <BasicExtendedTransitionProvider>
                    <TestComponent />
                </BasicExtendedTransitionProvider>
            </MemoryRouter>
        );

        userEvent.click(getByText('Add Patient'));

        expect(getByTestId('patient-name').textContent).toBe('John Doe');
        expect(getByTestId('patient-age').textContent).toBe('01-01-1993');
    });

    it('should throw an error if usePatientData is used outside of AddPatientDataProvider', () => {
        const consoleError = jest.spyOn(console, 'error').mockImplementation(() => {});

        expect(() => render(<TestComponent />)).toThrow(
            'useBasicExtendedTransition must be used within a BasicExtendedTransitionProvider'
        );

        consoleError.mockRestore();
    });
});

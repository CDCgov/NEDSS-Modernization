import { render } from '@testing-library/react';
import { BasicExtendedTransitionProvider, useBasicExtendedTransition } from './useBasicExtendedTransition';
import userEvent from '@testing-library/user-event';
import { MemoryRouter } from 'react-router-dom';
import { NewPatientEntry } from '../NewPatientEntry';
import { BasicNewPatientEntry } from '../basic/entry';

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

    const input: NewPatientEntry = {
        firstName: 'John',
        lastName: 'Doe',
        dateOfBirth: '01-01-1993',
        asOf: '01-01-2024',
        identification: [],
        phoneNumbers: [],
        emailAddresses: []
    };

    return (
        <div>
            <button onClick={() => setTransitionData(input)}>Add Patient</button>
            <div data-testid="patient-name">{`${transitionData?.firstName} ${transitionData?.lastName}`}</div>
            <div data-testid="patient-age">{transitionData?.dateOfBirth}</div>
        </div>
    );
}

function TestComponentNewBasic() {
    const { newTransitionData, setNewTransitionData } = useBasicExtendedTransition();

    const input: BasicNewPatientEntry = {
        administrative: { asOf: '01-01-2024' },
        name: { first: 'John', last: 'Doe' },
        personalDetails: { bornOn: '01-01-1993' }
    };

    return (
        <div>
            <button onClick={() => setNewTransitionData(input)}>Add Patient</button>
            <div data-testid="patient-name">{`${newTransitionData?.name?.first} ${newTransitionData?.name?.last}`}</div>
            <div data-testid="patient-age">{newTransitionData?.personalDetails?.bornOn}</div>
        </div>
    );
}

describe('useBasicExtendedTransition', () => {
    it('should update transitionData when setTransitionData is called for old basic form', () => {
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

    it('should update transitionData when setTransitionData is called for new basic form', () => {
        const { getByTestId, getByText } = render(
            <MemoryRouter>
                <BasicExtendedTransitionProvider>
                    <TestComponentNewBasic />
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
    it('should throw an error if usePatientData is used outside of AddPatientDataProvider', () => {
        const consoleError = jest.spyOn(console, 'error').mockImplementation(() => {});

        expect(() => render(<TestComponentNewBasic />)).toThrow(
            'useBasicExtendedTransition must be used within a BasicExtendedTransitionProvider'
        );

        consoleError.mockRestore();
    });
});

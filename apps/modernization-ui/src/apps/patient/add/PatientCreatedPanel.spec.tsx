import { vi } from 'vitest';
import { render } from '@testing-library/react';
import { CreatedPatient } from './api';
import { PatientCreatedPanel } from './PatientCreatedPanel';

let mockPermissions: string[] = [];

vi.mock('user', () => ({
    useUser: () => ({ state: { user: { permissions: mockPermissions } } })
}));

describe('PatientCreatedPanel', () => {
    beforeEach(() => {
        mockPermissions = [];
    });

    const createdPatient: CreatedPatient = {
        id: 123,
        shortId: 123,
        name: { first: 'John', last: 'Doe' }
    };

    it('renders success message with patient name and ID', () => {
        const { getByText } = render(<PatientCreatedPanel created={createdPatient} />);
        expect(getByText(/You have successfully added a new patient/i)).toBeInTheDocument();
        expect(getByText(/John Doe \(Patient ID: 123\)/i)).toBeInTheDocument();
    });

    it('renders Add lab report button when permission is granted', () => {
        mockPermissions = ['ADD-OBSERVATIONLABREPORT'];
        const { getByText } = render(<PatientCreatedPanel created={createdPatient} />);
        expect(getByText(/Add lab report/i)).toBeInTheDocument();
    });

    it('renders Add investigation button when permission is granted', () => {
        mockPermissions = ['ADD-INVESTIGATION'];
        const { getByText } = render(<PatientCreatedPanel created={createdPatient} />);
        expect(getByText(/Add investigation/i)).toBeInTheDocument();
    });

    it('renders Add morbidity button when permission is granted', () => {
        mockPermissions = ['ADD-OBSERVATIONMORBIDITYREPORT'];
        const { getByText } = render(<PatientCreatedPanel created={createdPatient} />);
        expect(getByText(/Add morbidity/i)).toBeInTheDocument();
    });
});

import { BrowserRouter } from 'react-router';
import { render, screen } from '@testing-library/react';
import { defaultConfiguration } from 'configuration/defaults';
import { Features } from 'configuration';
import { PatientFileLink } from './PatientFileLink';

let mockPermissions: string[] = [];

vi.mock('user', () => ({
    useUser: () => ({ state: { user: { permissions: mockPermissions } } })
}));

let mockFeatures: Features = {
    ...defaultConfiguration.features
};

const withModernizedPatientFile = (enabled: boolean) => ({
    ...defaultConfiguration.features,
    patient: {
        ...defaultConfiguration.features.patient,
        file: {
            enabled: enabled
        }
    }
});

vi.mock('configuration', () => ({
    useConfiguration: () => ({ ready: false, loading: false, load: vi.fn(), features: mockFeatures })
}));

describe('PatientFileLink', () => {
    beforeEach(() => {
        mockPermissions = [];
        mockFeatures = { ...defaultConfiguration.features };
    });

    describe('when the use cannot view a patient file', () => {
        beforeEach(() => {
            mockPermissions = ['VIEW-PATIENT'];
        });

        it('should display the provided value ', () => {
            render(
                <PatientFileLink identifier={719} patientId={84001}>
                    John Doe
                </PatientFileLink>
            );
            expect(screen.getByText('John Doe')).toBeInTheDocument();

            expect(screen.queryByRole('link', { name: 'John Doe' })).not.toBeInTheDocument();
        });

        it('should display the Patient ID', () => {
            render(<PatientFileLink identifier={719} patientId={84001} />);

            expect(screen.getByText('84001')).toBeInTheDocument();

            expect(screen.queryByRole('link', { name: '84001' })).not.toBeInTheDocument();
        });
    });

    describe('when the use can view a patient file ', () => {
        beforeEach(() => {
            mockPermissions = ['VIEWWORKUP-PATIENT'];
        });

        it('should link to the modernized patient file using the provided display value when the modernized Patient file is enabled', () => {
            mockFeatures = withModernizedPatientFile(true);

            render(
                <BrowserRouter>
                    <PatientFileLink identifier={719} patientId={84001}>
                        John Doe
                    </PatientFileLink>
                </BrowserRouter>
            );

            const link = screen.getByRole('link', { name: 'John Doe' });
            expect(link).toHaveAttribute('href', '/patient/84001');
        });

        it('should link to the modernized patient file using the Patient ID when the modernized Patient file is enabled', () => {
            mockFeatures = withModernizedPatientFile(true);

            render(
                <BrowserRouter>
                    <PatientFileLink identifier={719} patientId={84001} />
                </BrowserRouter>
            );
            const link = screen.getByRole('link', { name: '84001' });
            expect(link).toHaveAttribute('href', '/patient/84001');
        });

        it('should link to the NBS6 patient file using the provided display value when the modernized Patient file is disabled', () => {
            mockFeatures = withModernizedPatientFile(false);

            render(
                <BrowserRouter>
                    <PatientFileLink identifier={719} patientId={84001}>
                        John Doe
                    </PatientFileLink>
                </BrowserRouter>
            );

            const link = screen.getByRole('link', { name: 'John Doe' });
            expect(link).toHaveAttribute('href', '/nbs/api/patient/719/file/redirect');
        });

        it('should link to the NBS6 patient file using the Patient ID when the modernized Patient file is disabled', () => {
            mockFeatures = withModernizedPatientFile(false);

            render(
                <BrowserRouter>
                    <PatientFileLink identifier={719} patientId={84001} />
                </BrowserRouter>
            );

            const link = screen.getByRole('link', { name: '84001' });
            expect(link).toHaveAttribute('href', '/nbs/api/patient/719/file/redirect');
        });
    });
});

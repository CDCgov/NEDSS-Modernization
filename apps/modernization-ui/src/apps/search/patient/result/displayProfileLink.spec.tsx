import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router';
import { displayProfileLink } from './displayProfileLink';
import { defaultConfiguration } from 'configuration/defaults';
import { Features } from 'configuration';

let mockFeatures: Features = {
    ...defaultConfiguration.features
};

const withModernizedPatientProfile = (enabled: boolean) => ({
    ...defaultConfiguration.features,
    patient: {
        ...defaultConfiguration.features.patient,
        profile: {
            enabled: enabled
        }
    }
});

jest.mock('configuration', () => ({
    useConfiguration: () => ({ ready: false, loading: false, load: jest.fn(), features: mockFeatures })
}));

describe('when navigating to the patient profile', () => {
    it('should link to the modernized patient profile when the feature is enabled', () => {
        mockFeatures = withModernizedPatientProfile(true);

        const { getByText } = render(<BrowserRouter>{displayProfileLink(719, 84001, 'John Doe')}</BrowserRouter>);
        const link = getByText('John Doe');
        expect(link).toHaveAttribute('href', '/patient-profile/84001/summary');
    });

    it('should link to the NBS6 patient profile when the feature is disabled', () => {
        mockFeatures = withModernizedPatientProfile(false);

        const { getByText } = render(<BrowserRouter>{displayProfileLink(719, 84001, 'John Doe')}</BrowserRouter>);
        const link = getByText('John Doe');
        expect(link).toHaveAttribute('href', '/nbs/api/patient/719/file/redirect');
    });
});
